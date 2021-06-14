package com.leverx.pets.controller.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.dto.PersonDto;
import com.leverx.pets.exception.EntityNotFoundException;
import com.leverx.pets.model.Person;
import com.leverx.pets.parser.UrlParser;
import com.leverx.pets.service.PersonService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static com.leverx.pets.util.HttpJsonUtil.jsonWrapper;
import static com.leverx.pets.util.HttpJsonUtil.sendJsonResponse;
import static com.leverx.pets.parser.UrlParser.getPathInfo;
import static com.leverx.pets.util.StringConstantsUtil.URL_DELIMITER;
import static java.lang.Long.parseLong;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(value = "/people/*", name = "PersonServlet")
public class PersonServlet extends HttpServlet {

    private PersonService personService;
    private ObjectMapper objectMapper;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        personService = (PersonService) context.getAttribute(PersonService.class.getName());
        objectMapper = (ObjectMapper) context.getAttribute(ObjectMapper.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String endpoint = getPathInfo(request);

            if (URL_DELIMITER.equals(endpoint)) {
                doGetAllPeople(response, endpoint);
            }
            else {
                doGetPersonById(response, endpoint);
            }

        } catch (IllegalArgumentException ex) {
            response.sendError(SC_NOT_FOUND, ex.getMessage());
        }
    }

    private void doGetAllPeople(HttpServletResponse response, String endpoint) throws IOException {

        try {
            UrlParser.endpointEmptyIsValid(endpoint);

            List<Person> people = personService.getAllPeople();

            String peopleJson = jsonWrapper(people);
            sendJsonResponse(peopleJson, response);

        } catch (EntityNotFoundException entityNotFoundException) {
            response.sendError(SC_NOT_FOUND, entityNotFoundException.getMessage());

        } catch (IllegalArgumentException illegalArgumentException) {
            response.sendError(SC_BAD_REQUEST, illegalArgumentException.getMessage());
        }

    }

    private void doGetPersonById(HttpServletResponse response, String endpoint) throws IOException {

        try {
            UrlParser.endpointWithIdIsValid(endpoint);

            Person person = personService.getPersonById(parseLong(endpoint));

            String personJson = jsonWrapper(person);
            sendJsonResponse(personJson, response);

        } catch (EntityNotFoundException entityNotFoundException) {
            response.sendError(SC_NOT_FOUND, entityNotFoundException.getMessage());

        } catch (IllegalArgumentException illegalArgumentException) {
            response.sendError(SC_BAD_REQUEST, illegalArgumentException.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String endpoint = getPathInfo(request);
            UrlParser.endpointEmptyIsValid(endpoint);

            BufferedReader personJsonRequest = request.getReader();
            PersonDto personDto = objectMapper.readValue(personJsonRequest, PersonDto.class);
            Person person = personService.createPerson(personDto);

            String personJson = jsonWrapper(person);
            sendJsonResponse(personJson, response);

        } catch (IllegalArgumentException ex) {
            response.sendError(SC_BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String endpoint = getPathInfo(request);
            UrlParser.endpointWithIdIsValid(endpoint);

            BufferedReader personJsonRequest = request.getReader();
            PersonDto personDto = objectMapper.readValue(personJsonRequest, PersonDto.class);
            Person person = personService.updatePerson(personDto, parseLong(endpoint));

            String personJson = jsonWrapper(person);
            sendJsonResponse(personJson, response);

        } catch (IllegalArgumentException ex) {
            response.sendError(SC_BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String endpoint = getPathInfo(request);
            UrlParser.endpointWithIdIsValid(endpoint);

            personService.deletePersonById(parseLong(endpoint));

            response.setStatus(SC_OK);

        } catch (IllegalArgumentException ex) {
            response.sendError(SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
