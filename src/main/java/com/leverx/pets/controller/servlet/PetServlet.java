package com.leverx.pets.controller.servlet;

import com.leverx.pets.service.PetService;

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
import static com.leverx.pets.parser.UrlParser.getPathInfo;
import static java.lang.Long.parseLong;
import static java.util.Objects.nonNull;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(value = "/pets/*", name = "PetServlet")
public class PetServlet extends HttpServlet {

    private PetService petService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        petService = (PetService) context.getAttribute(PetService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (!url.isEmpty() && URL_DELIMITER.equals(url.get(0)) && url.size() == 1) {
            String pets = petService.getAllPets();
            sendJsonResponse(pets, response);
        }
        else if (url.size() == 2) {
            String pet = petService.getPetById(parseLong(url.get(1)));
            if (nonNull(pet) && !EMPTY.equals(pet)) {
                sendJsonResponse(pet, response);
            }
            else {
                response.sendError(SC_NOT_FOUND);
            }
        }
        else {
            response.sendError(SC_NOT_FOUND);
        }
    }

    // pets/person/{id}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url.size() == 3) {
            BufferedReader petJsonRequest = request.getReader();
            boolean isCreate = petService.createPet(petJsonRequest, Long.parseLong(url.get(2)));
            if (isCreate) {
                response.setStatus(SC_OK);
            }
            else {
                response.setStatus(SC_BAD_REQUEST);
            }
        }
        else {
            response.sendError(SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url.size() == 2) {
            BufferedReader petJsonRequest = request.getReader();
            boolean isUpdate = petService.updatePet(petJsonRequest, parseLong(url.get(1)));
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

    // pets/{id}
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url.size() == 2) {
            boolean isDelete = petService.deletePet(parseLong(url.get(1)));
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
