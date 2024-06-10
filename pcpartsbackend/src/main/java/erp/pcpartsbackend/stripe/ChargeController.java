package erp.pcpartsbackend.stripe;

import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import erp.pcpartsbackend.controllers.OrderController;
import erp.pcpartsbackend.controllers.dto.OrderDto;
import erp.pcpartsbackend.models.Order;
import erp.pcpartsbackend.models.ProductOrder;
import erp.pcpartsbackend.repositories.OrderRepository;
import erp.pcpartsbackend.services.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin("*")
public class ChargeController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final OrderController orderController;

    public ChargeController(@Value("${stripe.apiKey}") String apiKey, OrderService orderService, OrderRepository orderRepository, OrderController orderController) {
        Stripe.apiKey = apiKey;
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderController = orderController;
    }

    @PostMapping(value = "/charges", consumes = "application/json")
    public ResponseEntity<?> chargeCard(@RequestBody CreateChargeForm chargeForm) throws StripeException {
        try {
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("currency", "rsd");
            chargeParams.put("description", chargeForm.getDescription());
            chargeParams.put("amount", chargeForm.getPrice()*100);
            chargeParams.put("source", chargeForm.getToken());
            //Map<String, Object> metadata = new HashMap<>();
            //metadata.put("orderId", chargeForm.getOrderId());
            //chargeParams.put("metadata", metadata);
            Charge charge = Charge.create(chargeParams);

            ChargeResponse response = new ChargeResponse();
            response.setId(charge.getId());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(e.getStripeError().getMessage());
        }
    }

    @PostMapping(value = "/webhook")
    public ResponseEntity<?> webhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event = null;

        try {
            String webhookSecret = "whsec_b2826a7aa1199758932de4df679f01f667ce0a54a0c3a558c7fbb9bc46fd1014";
            event = Webhook.constructEvent(payload,sigHeader, webhookSecret);
        } catch (SignatureVerificationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        switch (event.getType()) {
            case "charge.succeeded":
                handleChargeSucceeded(event);
                break;
            case "charge.failed":
                handleChargeFailed(event);
        }
        return ResponseEntity.ok("Success");
    }

    private void handleChargeSucceeded(Event event) {
        Charge charge = (Charge) event.getDataObjectDeserializer().getObject().orElse(null);
        if (charge != null && charge.getMetadata() != null) {
            String orderId = charge.getDescription();
            if (orderId != null) {
                Order order = orderRepository.findByOrderId(UUID.fromString(orderId));
                order.setOrderStatus("Completed");
                orderRepository.save(order);
            } else {
                log.error("Order ID not found in charge metadata.");
            }
        } else {
            log.error("Failed to deserialize charge object.");
        }
    }

    private void handleChargeFailed(Event event) {
        // Extract charge and order details from the event
        // Update the order status to "FAILED" or appropriate status
    }
}
