package com.leverx.pets.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.leverx.pets.util.StringConstantsUtil.DELIMITER;
import static com.leverx.pets.util.StringConstantsUtil.PERSON_URL;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class UrlParser {

    public static List<String> getPathInfo(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.isEmpty() || DELIMITER.equals(pathInfo)) {
            return new ArrayList<>(singleton(DELIMITER));
        }
        List<String> endpoints = asList(pathInfo.split(DELIMITER));
        switch (endpoints.size()) {
            case 2:
                if (isNumeric(endpoints.get(1))) {
                    return endpoints;
                }
                else {
                    return new ArrayList<>();
                }
            case 3:
                if (PERSON_URL.equals(endpoints.get(1)) && isNumeric(endpoints.get(2))) {
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
