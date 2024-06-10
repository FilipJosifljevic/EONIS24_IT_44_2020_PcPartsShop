package erp.pcpartsbackend.controllers;

import erp.pcpartsbackend.controllers.dto.ProductOrderDto;
import erp.pcpartsbackend.controllers.dto.UpdateProductOrdersDto;
import erp.pcpartsbackend.models.Order;
import erp.pcpartsbackend.models.Product;
import erp.pcpartsbackend.models.ProductOrder;
import erp.pcpartsbackend.repositories.OrderRepository;
import erp.pcpartsbackend.repositories.ProductOrderRepository;
import erp.pcpartsbackend.repositories.ProductRepository;
import erp.pcpartsbackend.services.OrderService;
import erp.pcpartsbackend.services.ProductOrderService;
import erp.pcpartsbackend.services.ProductService;
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

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
    public ResponseEntity<?> getProductOrderByProductId(@PathVariable("id") UUID productOrderId) {
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
    public ResponseEntity<?> addProductOrder(@RequestBody ProductOrderDto productOrder) {
        if(productOrderService.existById(productOrder.getProductOrderId())){
            return new ResponseEntity<>(
                    "Product order with that id already exists",
                    HttpStatus.CONFLICT);
        }

        // Fetch the Product from the database
        Product product = productService.getProductById(productOrder.getProductId());
        if (product == null) {
            return new ResponseEntity<>(
                    "Product not found",
                    HttpStatus.NOT_FOUND);
        }

        Order order = orderService.getOrder(productOrder.getOrderId());
        if (order != null) {
            float totalPrice = product.getProductPrice() * productOrder.getQuantity();
            order.setOrderPrice(order.getOrderPrice() + totalPrice);
        }

        // Reduce the quantity in stock of the Product
        int newQuantityInStock = product.getQuantityInStock() - productOrder.getQuantity();
        if (newQuantityInStock < 0) {
            return new ResponseEntity<>("Insufficient quantity in stock", HttpStatus.BAD_REQUEST);
        }
        product.setQuantityInStock(newQuantityInStock);

        // Save the ProductOrder
        ProductOrder savedproductOrder = productOrderService.addProductOrder(productOrder);

        return ResponseEntity.status(HttpStatus.OK).body(savedproductOrder);
    }


    @PutMapping("productOrders/{id}")
    public ResponseEntity<?> updateProductOrder(@RequestBody ProductOrderDto productOrderDto, @PathVariable("id") UUID productOrderId) {
        productOrderDto.setProductOrderId(productOrderId);
        if (!productOrderService.existById(productOrderDto.getProductOrderId())) {
            return new ResponseEntity<>("Product order with that id doesn't exist", HttpStatus.CONFLICT);
        }

        ProductOrder existingProductOrder = productOrderService.getProductOrderById(productOrderId);
        if (existingProductOrder == null) {
            return new ResponseEntity<>("Product order not found", HttpStatus.NOT_FOUND);
        }


        int quantityDifference = productOrderDto.getQuantity() - existingProductOrder.getQuantity();

        // Fetch the associated Product and Order
        Product product = existingProductOrder.getProduct();
        Order order = existingProductOrder.getOrder();
        if (product == null || order == null) {
            return new ResponseEntity<>("Product or Order not found", HttpStatus.NOT_FOUND);
        }

        // Adjust the order price based on the difference in quantity
        float priceAdjustment = quantityDifference * product.getProductPrice();
        order.setOrderPrice(order.getOrderPrice() + priceAdjustment);

        // Reduce the quantity in stock of the Product
        int newQuantityInStock = product.getQuantityInStock() - quantityDifference;
        if (newQuantityInStock < 0) {
            return new ResponseEntity<>("Insufficient quantity in stock", HttpStatus.BAD_REQUEST);
        }
        product.setQuantityInStock(newQuantityInStock);


        existingProductOrder.setQuantity(productOrderDto.getQuantity());
        existingProductOrder.setOrder(orderRepository.findByOrderId(productOrderDto.getOrderId()));
        existingProductOrder.setProduct(productRepository.findProductByProductId(productOrderDto.getProductId()));
        ProductOrder savedProductOrder = productOrderService.updateProductOrder(existingProductOrder);

        return ResponseEntity.status(HttpStatus.OK).body(savedProductOrder);
    }

    @PutMapping("productOrders/updateOrderIds")
    public ResponseEntity<?> updateProductOrders(@RequestBody UpdateProductOrdersDto productOrders) {
        productOrderService.updateOrderIds(productOrders.getProductOrders(), productOrders.getOrderId());
        return ResponseEntity.status(HttpStatus.OK).body(productOrders);
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
