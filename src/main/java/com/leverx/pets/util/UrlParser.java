package com.leverx.pets.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UrlParser {

    private static final String PERSON_URL = "person";
    private static final String DELIMITER = "/";

    public static List<String> getPathInfo(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty() || DELIMITER.equals(pathInfo)) {
            return null;
        }
        pathInfo = pathInfo.substring(1);
        List<String> endpoints = Arrays.asList(pathInfo.split(DELIMITER));
        switch (endpoints.size()) {
            case 1:
                if (StringUtils.isNumeric(endpoints.get(0))) {
                    return endpoints;
                }
                else {
                    return new ArrayList<>();
                }
            case 2:
                if (PERSON_URL.equals(endpoints.get(0)) && StringUtils.isNumeric(endpoints.get(1))) {
                    return endpoints;
                }
                else {
                    return new ArrayList<>();
                }
            default:
                return new ArrayList<>();
        }
    }
}
