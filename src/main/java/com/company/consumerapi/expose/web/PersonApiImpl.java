package com.company.consumerapi.expose.web;

import com.company.consumerapi.service.PersonService;
import com.open.person.api.PersonApi;
import com.open.person.model.PersonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class PersonApiImpl implements PersonApi {

    private final PersonService personService;

    @Override
    public Mono<ResponseEntity<PersonDTO>> getPerson(
            @PathVariable("id") Integer id,
            ServerWebExchange exchange) {
        return personService.getPerson(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> {
                    return Mono.just(ResponseEntity.status(500).body(null));
                });
    }

    @Override
    public Mono<ResponseEntity<PersonDTO>> createPerson(
            @RequestBody Mono<PersonDTO> personDTO,
            ServerWebExchange exchange) {
        return personDTO
                .flatMap(this.personService::createPerson)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.badRequest().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).body(null)));
    }
}
