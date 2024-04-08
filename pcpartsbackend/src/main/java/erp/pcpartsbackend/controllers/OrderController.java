package erp.pcpartsbackend.controllers;

import erp.pcpartsbackend.models.Order;
import erp.pcpartsbackend.repositories.OrderRepository;
import erp.pcpartsbackend.services.OrderService;
import erp.pcpartsbackend.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("orders")
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            return new ResponseEntity<>(
                    "Orders not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("orders/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") UUID orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return new ResponseEntity<>(
                    "Order not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("orders/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable("status") String status) {
        List<Order> orders = orderService.getOrdersByStatus(status);
        if (orders.isEmpty()) {
            return new ResponseEntity<>(
                    "Orders not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("orders/date/{date}")
    public ResponseEntity<?> getOrdersByDate(@PathVariable("date") String dateString) {
        try {
            List<Order> orders = orderService.getOrdersByDate(DateConverter.convertStringToDate(dateString));
            if (orders.isEmpty()) {
                return new ResponseEntity<>(
                        "Orders not found",
                        HttpStatus.NOT_FOUND
                );
            }
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Invalid date", HttpStatus.BAD_REQUEST);
        }

    }
}
