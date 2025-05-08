package com.company.consumerapi.service.impl;

import com.company.consumerapi.service.PersonService;
import com.open.person.model.PersonDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PersonImpl implements PersonService {

    private final WebClient webClient;

    public PersonImpl(WebClient.Builder webClientBuilder,
                      @Value("${app.person.url}") String personApiUrl) {
        this.webClient = webClientBuilder
                .baseUrl(personApiUrl)
                .build();
    }

    @Override
    public Mono<PersonDTO> getPerson(Integer id) {
        return webClient.get()
                .uri("/person/{id}", id)
                .retrieve()
                .bodyToMono(PersonDTO.class)
                .onErrorResume(e-> {
                    // Handle error and return an empty Mono
                    return Mono.empty();
                });
    }

    @Override
    public Mono<PersonDTO> createPerson(PersonDTO person) {
        return webClient.post()
                .uri("/person")
                .bodyValue(person)
                .retrieve()
                .bodyToMono(PersonDTO.class)
                .onErrorResume(e -> {
                    // Handle error and return an empty Mono
                    return Mono.empty();
                });
    }
}
