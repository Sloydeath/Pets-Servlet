package com.leverx.pets.controller.servlet;

import com.leverx.pets.service.PersonService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static com.leverx.pets.util.JsonUtil.sendJsonResponse;
import static com.leverx.pets.util.StringConstantsUtil.URL_DELIMITER;
import static com.leverx.pets.util.StringConstantsUtil.EMPTY;
import static com.leverx.pets.util.UrlParser.getPathInfo;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(value = "/people/*", name = "PersonServlet")
public class PersonServlet extends HttpServlet {

    private PersonService personService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        personService = (PersonService) context.getAttribute(PersonService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (!url.isEmpty() && URL_DELIMITER.equals(url.get(0)) && url.size() == 1) {
            String people = personService.getAllPeople();
            sendJsonResponse(people, response);
        }
        else if (url.size() == 2) {
            String person = personService.getPersonById(parseLong(url.get(1)));
            if (nonNull(person) && !EMPTY.equals(person)) {
                sendJsonResponse(person, response);
            }
            else {
                response.sendError(SC_NOT_FOUND);
            }
        }
        else {
            response.sendError(SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (!url.isEmpty() && URL_DELIMITER.equals(url.get(0)) && url.size() == 1) {
            BufferedReader personJsonRequest = request.getReader();
            boolean isCreate = personService.createPerson(personJsonRequest);
            if (isCreate) {
                response.setStatus(SC_OK);
            }
            else {
                response.setStatus(SC_BAD_REQUEST);
            }
        }
        else {
            response.sendError(SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url.size() == 2) {
            BufferedReader personJsonRequest = request.getReader();
            boolean isUpdate = personService.updatePerson(personJsonRequest, parseLong(url.get(1)));
            if (isUpdate) {
                response.setStatus(SC_OK);
            }
            else {
                response.setStatus(SC_BAD_REQUEST);
            }
        }
        else {
            response.sendError(SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url.size() == 2) {
            boolean isDelete = personService.deletePersonById(parseLong(url.get(1)));
            if (isDelete) {
                response.setStatus(SC_OK);
            }
            else {
                response.setStatus(SC_BAD_REQUEST);
            }
        }
        else {
            response.sendError(SC_BAD_REQUEST);
        }
    }
}
