package pe.edu.vallegrande.app.service;

import pe.edu.vallegrande.app.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    // Cloud (Atlas)
    Flux<Customer> findAllCloud();
    Mono<Customer> findByIdCloud(String id);

    // Local (Docker)
    Flux<Customer> findAllLocal();
    Mono<Customer> findByIdLocal(String id);

    // Save to both
    Mono<Customer> save(Customer customer);
    
}