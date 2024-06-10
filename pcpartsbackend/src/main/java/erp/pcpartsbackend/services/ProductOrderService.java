package erp.pcpartsbackend.services;

import erp.pcpartsbackend.controllers.dto.ProductOrderDto;
import erp.pcpartsbackend.models.ProductOrder;
import erp.pcpartsbackend.repositories.OrderRepository;
import erp.pcpartsbackend.repositories.ProductOrderRepository;
import erp.pcpartsbackend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<ProductOrder> getAllProductOrders() {
        return productOrderRepository.findAll();
    }

    public ProductOrder getProductOrderById(UUID productOrderId) {
        return productOrderRepository.findByProductOrderId(productOrderId);
    }

    public ProductOrder addProductOrder(ProductOrderDto productOrderDto) {
        ProductOrder productOrder = new ProductOrder();
        productOrder.setQuantity(productOrder.getQuantity());
        productOrder.setProduct(productRepository.findProductByProductId(productOrderDto.getProductId()));
        productOrder.setOrder(orderRepository.findByOrderId(productOrderDto.getOrderId()));
        return productOrderRepository.save(productOrder);
    }

    public ProductOrder updateProductOrder(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }

    public void updateOrderIds(List<ProductOrder> productOrders, UUID orderId) {
        for (ProductOrder productOrder : productOrders) {
            productOrder.setOrder(orderRepository.findByOrderId(orderId));
            productOrderRepository.save(productOrder);
        }
    }

    public void deleteProductOrder(ProductOrder productOrder){
        productOrderRepository.delete(productOrder);
    }

    public boolean existById(UUID productOrderId){
        return getProductOrderById(productOrderId) != null;
    }
}
