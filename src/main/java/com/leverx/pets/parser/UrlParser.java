package com.leverx.pets.parser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.leverx.pets.util.StringConstantsUtil.EMPTY;
import static com.leverx.pets.util.StringConstantsUtil.URL_DELIMITER;
import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class UrlParser {

    public static String getPathInfo(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (isBlank(pathInfo) || URL_DELIMITER.equals(pathInfo)) {
            return EMPTY;
        }

        List<String> endpoint = asList(pathInfo.split(URL_DELIMITER));
        if (endpoint.size() == 2 && isNumeric(endpoint.get(1))) {
            return endpoint.get(1);
        }

        throw new IllegalArgumentException("Incorrect URL");
    }

    public static void endpointWithIdIsValid(String endpoint) {
        if (EMPTY.equals(endpoint)) {
            throw new IllegalArgumentException("Incorrect URL");
        }
    }

    public static void endpointEmptyIsValid(String endpoint) {
        if (!EMPTY.equals(endpoint)) {
            throw new IllegalArgumentException("Incorrect URL");
        }
    }
}
