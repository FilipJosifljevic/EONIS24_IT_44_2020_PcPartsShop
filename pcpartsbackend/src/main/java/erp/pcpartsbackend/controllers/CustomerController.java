package erp.pcpartsbackend.controllers;

import erp.pcpartsbackend.models.Customer;
import erp.pcpartsbackend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class CustomerController {
    
    @Autowired
    private CustomerService customerService;

    @GetMapping("customers")
    public ResponseEntity<?> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        if (customers.isEmpty()) {
            return new ResponseEntity<>(
                    "Customers not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("customers/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable("id") UUID customerId) {
        Customer customer = customerService.getCustomerById(customerId);
        if (customer == null) {
            return new ResponseEntity<>(
                    "Customer not found",
                    HttpStatus.NOT_FOUND
            );
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PostMapping("customers")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        if(customerService.existById(customer.getUserId())){
            return new ResponseEntity<>(
                    "Customer with that id already exists",
                    HttpStatus.CONFLICT);
        }
        Customer savedcustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(savedcustomer);
    }

    @PutMapping("customers/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer, @PathVariable("id") UUID customerId) {
        customer.setUserId(customerId);
        if(!customerService.existById(customer.getUserId())){
            return new ResponseEntity<>(
                    "Customer with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        Customer savedcustomer = customerService.addCustomer(customer);
        return ResponseEntity.status(HttpStatus.OK).body(savedcustomer);
    }

    @DeleteMapping("customers/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") UUID customerId) {
        if(!customerService.existById(customerId)){
            return new ResponseEntity<>(
                    "Customer with that id doesn't exist",
                    HttpStatus.CONFLICT);
        }
        customerService.deleteCustomer(customerService.getCustomerById(customerId));
        return ResponseEntity.status(HttpStatus.OK).body("Customer with that id has been deleted");
    }
}
