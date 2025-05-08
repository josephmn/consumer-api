package com.company.consumerapi.service;

import com.open.model.CustomerResponse;
import reactor.core.publisher.Mono;

public interface CustomerService {
    Mono<CustomerResponse> getCustomerById(String customerId);
}
