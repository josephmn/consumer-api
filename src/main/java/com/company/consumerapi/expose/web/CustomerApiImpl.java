package com.company.consumerapi.expose.web;

import com.company.consumerapi.service.CustomerService;
import com.open.customer.api.CustomerApi;
import com.open.customer.model.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class CustomerApiImpl implements CustomerApi {

    private final CustomerService customerService;

    @Override
    public Mono<ResponseEntity<CustomerResponse>> getCustomerById(
            String id,
            ServerWebExchange exchange) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity.status(500).body(null));
                });
    }
}
