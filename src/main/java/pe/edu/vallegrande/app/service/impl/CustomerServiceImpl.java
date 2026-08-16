package pe.edu.vallegrande.app.service.impl;

import pe.edu.vallegrande.app.model.Customer;
import pe.edu.vallegrande.app.repository.CustomerRepository;
import pe.edu.vallegrande.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ReactiveMongoTemplate localMongoTemplate;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               @Qualifier("localMongoTemplate") ReactiveMongoTemplate localMongoTemplate) {
        this.customerRepository = customerRepository;
        this.localMongoTemplate = localMongoTemplate;
    }

    // ========== CLOUD (Atlas) ==========

    @Override
    public Flux<Customer> findAllCloud() {
        log.info("Mostrando datos desde CLOUD");
        return customerRepository.findAll();
    }

    @Override
    public Mono<Customer> findByIdCloud(String id) {
        log.info("Mostrando datos por ID desde CLOUD");
        return customerRepository.findById(id);
    }

    // ========== LOCAL (Docker) ==========

    @Override
    public Flux<Customer> findAllLocal() {
        log.info("Mostrando datos desde LOCAL");
        return localMongoTemplate.findAll(Customer.class, "customer");
    }

    @Override
    public Mono<Customer> findByIdLocal(String id) {
        log.info("Mostrando datos por ID desde LOCAL");
        Query query = new Query(Criteria.where("_id").is(id));
        return localMongoTemplate.findOne(query, Customer.class, "customer");
    }

    // ========== SAVE (Cloud + Local) ==========

    @Override
    public Mono<Customer> save(Customer customer) {
        log.info("Registrando datos en CLOUD y LOCAL: " + customer.toString());
        customer.setState("A");
        return customerRepository.save(customer)
                .flatMap(savedCustomer ->
                        localMongoTemplate.save(savedCustomer, "customer")
                );
    }

}