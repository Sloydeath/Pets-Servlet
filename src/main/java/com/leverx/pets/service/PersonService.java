package com.leverx.pets.service;

import java.io.BufferedReader;

public interface PersonService {
    boolean createPerson(BufferedReader person);
    String getAllPeople();
    String getPersonById(Long id);
    boolean deletePersonById(Long id);
    boolean updatePerson(BufferedReader person, Long id);
}
