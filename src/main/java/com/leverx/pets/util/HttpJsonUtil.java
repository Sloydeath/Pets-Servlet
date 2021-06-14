package com.leverx.pets.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class HttpJsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sendJsonResponse(String objectJson, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        out.print(objectJson);
        out.flush();
    }

    public static void sendJsonResponse(List<String> objectsJson, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();
        objectsJson.forEach(out::print);
        out.flush();
    }

    public static String jsonWrapper(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
