package erp.pcpartsbackend.services;

import erp.pcpartsbackend.controllers.dto.OrderDto;
import erp.pcpartsbackend.models.Customer;
import erp.pcpartsbackend.models.Order;
import erp.pcpartsbackend.repositories.CustomerRepository;
import erp.pcpartsbackend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrder(UUID orderId) {
        return orderRepository.findByOrderId(orderId);
    }

    public List<Order> getOrdersByCustomerId(UUID customerId) {
        return orderRepository.findAllByCustomer_UserId(customerId);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findAllByOrderStatusIgnoreCase(status);
    }

    public List<Order> getOrdersByDate(Date date) {
        return orderRepository.findAllByOrderDate(date);
    }

    public Order addOrder(OrderDto orderDto) {
        Order order = new Order();
        order.setOrderDate(new Date());
        order.setOrderPrice(orderDto.getOrderPrice());
        order.setOrderStatus(orderDto.getOrderStatus());
        order.setDiscount(orderDto.getDiscount());
        order.setPromoCode(orderDto.getPromoCode());

        UUID customerId = orderDto.getCustomerId();
        Customer customer = customerRepository.findByUserId(customerId);
        order.setCustomer(customer);
        return orderRepository.save(order);
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    public boolean existById(UUID orderId){
        return getOrder(orderId) != null;
    }
}
