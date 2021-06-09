package com.leverx.pets.controller.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.leverx.pets.dto.PetDto;
import com.leverx.pets.dto.UpdatePetDto;
import com.leverx.pets.exception.EntityNotFoundException;
import com.leverx.pets.model.pet.Pet;
import com.leverx.pets.parser.UrlParser;
import com.leverx.pets.service.PetService;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static com.leverx.pets.util.JsonResponseSenderUtil.sendJsonResponse;
import static com.leverx.pets.util.StringConstantsUtil.EMPTY;
import static com.leverx.pets.parser.UrlParser.getPathInfo;
import static java.lang.Long.parseLong;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet(value = "/pets/*", name = "PetServlet")
public class PetServlet extends HttpServlet {

    private PetService petService;
    private ObjectMapper objectMapper;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        petService = (PetService) context.getAttribute(PetService.class.getName());
        objectMapper = (ObjectMapper) context.getAttribute(ObjectMapper.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String endpoint = getPathInfo(request);
            if (EMPTY.equals(endpoint)) {
                doGetAllPet(response, endpoint);
            }
            else {
                doGetPetById(response, endpoint);
            }
        } catch (IllegalArgumentException ex) {
            response.sendError(SC_NOT_FOUND, ex.getMessage());
        }
    }

    private void doGetAllPet(HttpServletResponse response, String endpoint) throws IOException {

        try {
            UrlParser.endpointEmptyIsValid(endpoint);

            List<Pet> pets = petService.getAllPets();

            String petsJsonResponse = objectMapper.writeValueAsString(pets);
            sendJsonResponse(petsJsonResponse, response);

        } catch (EntityNotFoundException entityNotFoundException) {
            response.sendError(SC_NOT_FOUND, entityNotFoundException.getMessage());

        } catch (IllegalArgumentException illegalArgumentException) {
            response.sendError(SC_BAD_REQUEST, illegalArgumentException.getMessage());
        }

    }

    private void doGetPetById(HttpServletResponse response, String endpoint) throws IOException {

        try {
            UrlParser.endpointWithIdIsValid(endpoint);

            Pet pet = petService.getPetById(parseLong(endpoint));

            String petJsonResponse = objectMapper.writeValueAsString(pet);
            sendJsonResponse(petJsonResponse, response);

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

            BufferedReader petJsonRequest = request.getReader();
            PetDto petDto = objectMapper.readValue(petJsonRequest, PetDto.class);
            Pet pet = petService.createPet(petDto);

            String petJsonResponse = objectMapper.writeValueAsString(pet);
            sendJsonResponse(petJsonResponse, response);

        } catch (IllegalArgumentException illegalArgumentException) {
            response.sendError(SC_BAD_REQUEST, illegalArgumentException.getMessage());

        } catch (InvalidFormatException invalidFormatException) {
            response.sendError(SC_BAD_REQUEST, "Invalid data");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String endpoint = getPathInfo(request);
            UrlParser.endpointWithIdIsValid(endpoint);

            BufferedReader petJsonRequest = request.getReader();
            UpdatePetDto updatePetDto = objectMapper.readValue(petJsonRequest, UpdatePetDto.class);

            Pet pet = petService.updatePet(updatePetDto, parseLong(endpoint));

            String petJsonResponse = objectMapper.writeValueAsString(pet);
            sendJsonResponse(petJsonResponse, response);

        } catch (IllegalArgumentException ex) {
            response.sendError(SC_BAD_REQUEST, ex.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {
            String endpoint = getPathInfo(request);
            UrlParser.endpointWithIdIsValid(endpoint);

            petService.deletePetById(parseLong(endpoint));

            response.setStatus(SC_OK);
        } catch (IllegalArgumentException ex) {
            response.sendError(SC_BAD_REQUEST, ex.getMessage());
        }
    }
}
