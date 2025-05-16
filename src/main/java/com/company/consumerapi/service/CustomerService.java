package com.company.consumerapi.service;

import com.company.consumerapi.model.customer.CustomerResponse;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerResponse> getCustomerById(String customerId);
}
