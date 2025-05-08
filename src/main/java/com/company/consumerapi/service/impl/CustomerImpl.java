package com.company.consumerapi.service.impl;

import com.company.consumerapi.service.CustomerService;
import com.open.customer.model.CustomerResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerImpl implements CustomerService {

    private final WebClient webClient;

    public CustomerImpl(WebClient.Builder webClientBuilder,
                        @Value("${app.customer.url}") String customerApiUrl) {
        this.webClient = webClientBuilder
                .baseUrl(customerApiUrl)
                .build();
    }

    @Override
    public Mono<CustomerResponse> getCustomerById(String customerId) {
        return webClient.get()
                .uri("/customer/{id}", customerId)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .onErrorResume(e -> {
                    // Handle error and return an empty Mono
                    return Mono.empty();
                });
    }
}
