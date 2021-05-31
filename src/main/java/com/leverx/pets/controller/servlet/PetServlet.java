package com.leverx.pets.controller.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leverx.pets.dto.PetDto;
import com.leverx.pets.model.Cat;
import com.leverx.pets.model.Dog;
import com.leverx.pets.model.Person;
import com.leverx.pets.model.Pet;
import com.leverx.pets.service.PersonService;
import com.leverx.pets.service.PetService;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import static com.leverx.pets.model.PetType.CAT;
import static com.leverx.pets.util.JsonUtil.readJsonData;
import static com.leverx.pets.util.JsonUtil.sendJsonResponse;
import static com.leverx.pets.util.UrlParser.getPathInfo;

@WebServlet(value = "/pets/*", name = "PetServlet")
public class PetServlet extends HttpServlet {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private PetService petService;
    private PersonService personService;

    @Override
    public void init() {
        ServletContext context = getServletContext();
        petService = (PetService) context.getAttribute(PetService.class.getName());
        personService = (PersonService) context.getAttribute(PersonService.class.getName());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url == null) {
            List<Pet> pets = petService.getAllPets();
            String jsonPeople = objectMapper.writeValueAsString(pets);
            sendJsonResponse(jsonPeople, response);
        }
        else if (url.size() == 1) {
            Pet pet = petService.getPetById(Long.parseLong(url.get(0)));
            if (pet != null) {
                String jsonPet = objectMapper.writeValueAsString(pet);
                sendJsonResponse(jsonPet, response);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        }
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // pets/person/{id}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);

        if (url != null && url.size() == 2) {
            BufferedReader reader = request.getReader();
            String json = readJsonData(reader);
            PetDto petDto = objectMapper.readValue(json, PetDto.class);
            Pet pet;
            if (CAT.equals(petDto.getPetType())) {
                pet = new Cat();
            }
            else {
                pet = new Dog();
            }
            Person person = personService.getPersonById(Long.parseLong(url.get(1)));
            if (person != null) {
                pet.setName(petDto.getName());
                pet.setPerson(person);
            }
            else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
            petService.createPet(pet);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url != null && url.size() == 1) {
            BufferedReader reader = request.getReader();
            String json = readJsonData(reader);
            PetDto petDto = objectMapper.readValue(json, PetDto.class);
            Pet pet = petService.getPetById(Long.parseLong(url.get(0)));
            if (pet != null) {
                pet.setName(petDto.getName());
                petService.updatePet(pet);
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

    // pets/{id}
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<String> url = getPathInfo(request);
        if (url != null && url.size() == 1) {
            petService.deletePet(Long.parseLong(url.get(0)));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
