package com.company.consumerapi.service;

import com.open.person.model.PersonDTO;
import reactor.core.publisher.Mono;

public interface PersonService {
    Mono<PersonDTO> getPerson(Integer id);
    Mono<PersonDTO> createPerson(PersonDTO person);
}
