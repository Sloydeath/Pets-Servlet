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
import java.io.PrintWriter;
import java.util.List;

import static com.leverx.pets.model.PetType.CAT;

@WebServlet("/pets/*")
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
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")){
            List<Pet> pets = petService.getAllPets();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            String jsonPets = objectMapper.writeValueAsString(pets);
            PrintWriter out = response.getWriter();
            out.print(jsonPets);
            out.flush();
        }
        else {
            String[] endpoints = pathInfo.split("/");
            if (endpoints.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            else if (StringUtils.isNumeric(endpoints[1])) {
                Pet pet = petService.getPetById(Long.parseLong(endpoints[1]));
                if (pet != null) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    String jsonPet = objectMapper.writeValueAsString(pet);

                    PrintWriter out = response.getWriter();
                    out.print(jsonPet);
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

    // pets/person/{id}
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null) {
            String[] endpoints = pathInfo.split("/");
            if (endpoints.length != 3) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            else if (StringUtils.isNumeric(endpoints[2]) && "person".equals(endpoints[1])) {
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
                Person person = personService.getPersonById(Long.parseLong(endpoints[2]));
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
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
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
                PetDto petDto = objectMapper.readValue(json, PetDto.class);
                Pet pet = petService.getPetById(Long.parseLong(endpoints[1]));
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
        else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    // pets/{id}
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            String[] endpoints = pathInfo.split("/");
            if (endpoints.length != 2) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
            else if (StringUtils.isNumeric(endpoints[1])) {
                petService.deletePet(Long.parseLong(endpoints[1]));
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
