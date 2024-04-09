package erp.pcpartsbackend.controllers;

import erp.pcpartsbackend.models.ProductOrder;
import erp.pcpartsbackend.services.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class ProductOrderController {
    
    @Autowired
    private ProductOrderService productOrderService;

    @GetMapping("productOrders")
    public ResponseEntity<?> getAllProductOrders() {
        List<ProductOrder> productOrders = productOrderService.getAllProductOrders();
        if (productOrders.isEmpty()) {
            return new ResponseEntity<>(
                    "Product orders not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(productOrders, HttpStatus.OK);
    }

    @GetMapping("productOrders/{id}")
    public ResponseEntity<?> getproductOrderById(@PathVariable("id") UUID productOrderId) {
        ProductOrder productOrder = productOrderService.getProductOrderById(productOrderId);
        if (productOrder == null) {
            return new ResponseEntity<>(
                    "Product order not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(productOrder, HttpStatus.OK);
    }

    @PostMapping("productOrders")
    public ResponseEntity<?> addproductOrder(@RequestBody ProductOrder productOrder) {
        if(productOrderService.existById(productOrder.getProductOrderId())){
            return new ResponseEntity<>(
                    "Product order with that id already exists",
                    HttpStatus.CONFLICT);
        }
        ProductOrder savedproductOrder = productOrderService.addProductOrder(productOrder);
        return ResponseEntity.status(HttpStatus.OK).body(savedproductOrder);
    }

    @PutMapping("productOrders/{id}")
    public ResponseEntity<?> updateproductOrder(@RequestBody ProductOrder productOrder, @PathVariable("id") UUID productOrderId) {
        productOrder.setProductOrderId(productOrderId);
        if(!productOrderService.existById(productOrder.getProductOrderId())){
            return new ResponseEntity<>(
                    "Product order with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        ProductOrder savedproductOrder = productOrderService.addProductOrder(productOrder);
        return ResponseEntity.status(HttpStatus.OK).body(savedproductOrder);
    }

    @DeleteMapping("productOrders/{id}")
    public ResponseEntity<String> deleteproductOrder(@PathVariable("id") UUID productOrderId) {
        if(!productOrderService.existById(productOrderId)){
            return new ResponseEntity<>(
                    "Product order with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        productOrderService.deleteProductOrder(productOrderService.getProductOrderById(productOrderId));
        return ResponseEntity.status(HttpStatus.OK).body("Product order with that id has been deleted");
    }
}