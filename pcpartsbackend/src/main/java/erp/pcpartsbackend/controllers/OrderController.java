package erp.pcpartsbackend.controllers;

import erp.pcpartsbackend.controllers.dto.OrderDto;
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

    @PostMapping("orders")
    public ResponseEntity<?> addOrder(@RequestBody OrderDto orderDto) {
        if(orderService.existById(orderDto.getOrderId())){
            return new ResponseEntity<>(
                    "Order with that id already exists",
                    HttpStatus.CONFLICT);
        }
        Order savedOrder = orderService.addOrder(orderDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedOrder);
    }

    @PutMapping("orders/{id}")
    public ResponseEntity<?> updateOrder(@RequestBody OrderDto orderDto, @PathVariable("id") UUID orderId) {
        orderDto.setOrderId(orderId);
        if(!orderService.existById(orderDto.getOrderId())){
            return new ResponseEntity<>(
                    "Order with that id doesn't exists",
                    HttpStatus.CONFLICT);
        }
        if (orderDto.getPromoCode() != null && !orderDto.getPromoCode().isEmpty()) {
            int percentage = Integer.parseInt(orderDto.getPromoCode().substring(0, 2));
            orderDto.setDiscount((double) percentage / 100);
        } else {
            orderDto.setDiscount(0.0);
        }
        Order savedOrder = orderService.addOrder(orderDto);
        return ResponseEntity.status(HttpStatus.OK).body(savedOrder);
    }

    @DeleteMapping("orders/{id}")
    public  ResponseEntity<String> deleteOrder(@PathVariable("id") UUID orderId){
        if(!orderService.existById(orderId)){
            return new ResponseEntity<>(
                    "Order with that id doesn't exists",
                    HttpStatus.CONFLICT);
        }
        orderService.deleteOrder(orderService.getOrder(orderId));
        return ResponseEntity.status(HttpStatus.OK).body("Order with that id has been deleted");
    }
}
