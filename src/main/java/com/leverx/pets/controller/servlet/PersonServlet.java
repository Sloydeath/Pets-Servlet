package com.leverx.pets.controller.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.model.Person;
import com.leverx.pets.service.PersonService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/people/*")
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
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")){
            List<Person> people = personService.getAllPeople();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonPeople = objectMapper.writeValueAsString(people);
            PrintWriter out = response.getWriter();
            out.print(jsonPeople);
            out.flush();
        }
        else {
            String[] endpoints = pathInfo.split("/");
            if (endpoints.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            else if (StringUtils.isNumeric(endpoints[1])) {
                Person person = personService.getPersonById(Long.parseLong(endpoints[1]));
                if (person != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    String jsonPerson = objectMapper.writeValueAsString(person);

                    PrintWriter out = response.getWriter();
                    out.print(jsonPerson);
                    out.flush();
                }
                else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
            else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.equals("/")){
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
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            String[] endpoints = pathInfo.split("/");
            if (endpoints.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            else if (StringUtils.isNumeric(endpoints[1])) {
                BufferedReader reader = request.getReader();
                String json = readJsonData(reader);
                Person newPerson = objectMapper.readValue(json, Person.class);
                Person person = personService.getPersonById(Long.parseLong(endpoints[1]));
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
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            String[] endpoints = pathInfo.split("/");
            if (endpoints.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            else if (StringUtils.isNumeric(endpoints[1])) {
                personService.deletePersonById(Long.parseLong(endpoints[1]));
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private String readJsonData(BufferedReader reader) throws IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line);
        }
        return buffer.toString();
    }
}
