package com.leverx.pets.controller.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.model.Person;
import com.leverx.pets.service.PersonService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static com.leverx.pets.util.JsonUtil.readJsonData;
import static com.leverx.pets.util.JsonUtil.sendJsonResponse;
import static com.leverx.pets.util.UrlParser.*;

@WebServlet(value = "/people/*", name = "PersonServlet")
public class PersonServlet extends HttpServlet {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private PersonService personService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        personService = (PersonService) context.getAttribute(PersonService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url == null) {
            List<Person> people = personService.getAllPeople();
            String jsonPeople = objectMapper.writeValueAsString(people);
            sendJsonResponse(jsonPeople, response);
        }
        else if (url.size() == 1) {
            Person person = personService.getPersonById(Long.parseLong(url.get(0)));
            if (person != null) {
                String jsonPerson = objectMapper.writeValueAsString(person);
                sendJsonResponse(jsonPerson, response);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url == null) {
            BufferedReader reader = request.getReader();
            String json = readJsonData(reader);
            Person person = objectMapper.readValue(json, Person.class);
            personService.createPerson(person);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        else if (url.size() == 1) {
            BufferedReader reader = request.getReader();
            String json = readJsonData(reader);
            Person newPerson = objectMapper.readValue(json, Person.class);
            Person person = personService.getPersonById(Long.parseLong(url.get(0)));
            if (person != null) {
                person.setName(newPerson.getName());
                personService.updatePerson(person);
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        else if (url.size() == 1) {
            personService.deletePersonById(Long.parseLong(url.get(0)));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
