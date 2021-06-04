package com.leverx.pets.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.leverx.pets.util.StringConstantsUtil.URL_DELIMITER;
import static com.leverx.pets.util.StringConstantsUtil.PERSON_URL;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class UrlParser {

    public static List<String> getPathInfo(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (isNull(pathInfo) || pathInfo.isEmpty() || URL_DELIMITER.equals(pathInfo)) {
            return new ArrayList<>(singleton(URL_DELIMITER));
        }
        List<String> endpoint = asList(pathInfo.split(URL_DELIMITER));
        switch (endpoint.size()) {
            case 2:
                if (isNumeric(endpoint.get(1))) {
                    return endpoint;
                }
                else {
                    return new ArrayList<>();
                }
            case 3:
                if (PERSON_URL.equals(endpoint.get(1)) && isNumeric(endpoint.get(2))) {
                    return endpoint;
                }
                else {
                    return new ArrayList<>();
                }
            default:
                return new ArrayList<>();
        }
    }
}
