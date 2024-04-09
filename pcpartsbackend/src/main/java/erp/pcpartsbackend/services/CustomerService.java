package erp.pcpartsbackend.services;

import erp.pcpartsbackend.models.Customer;
import erp.pcpartsbackend.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(UUID customerId) {
        return customerRepository.findByUserId(customerId);
    }

    public Customer addCustomer(Customer customer) {
        return  customerRepository.save(customer);
    }

    public void deleteCustomer(Customer customer) {
        customerRepository.delete(customer);
    }

    public boolean existById(UUID customerId){
        return getCustomerById(customerId) != null;
    }
}
