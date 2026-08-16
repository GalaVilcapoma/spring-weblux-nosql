package pe.edu.vallegrande.app.rest;

import pe.edu.vallegrande.app.model.Customer;
import pe.edu.vallegrande.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/api/customer")
public class CustomerRest {

    private final CustomerService customerService;

    @Autowired
    public CustomerRest(CustomerService customerService) {
        this.customerService = customerService;
    }

    // ========== CLOUD (Atlas) ==========

    @GetMapping("/cloud")
    public Flux<Customer> findAllCloud() {
        return customerService.findAllCloud();
    }

    @GetMapping("/cloud/{id}")
    public Mono<Customer> findByIdCloud(@PathVariable String id) {
        return customerService.findByIdCloud(id);
    }

    // ========== LOCAL (Docker) ==========

    @GetMapping("/local")
    public Flux<Customer> findAllLocal() {
        return customerService.findAllLocal();
    }

    @GetMapping("/local/{id}")
    public Mono<Customer> findByIdLocal(@PathVariable String id) {
        return customerService.findByIdLocal(id);
    }

    // ========== SAVE (Cloud + Local) ==========

    @PostMapping("/save")
    public Mono<Customer> save(@RequestBody Customer customer) {
        return customerService.save(customer);
    }

}
