package com.aradionov.socketchat.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Andrey Radionov
 */
public class ServletMessageProcessor {
    public static void writeErrorMessage(HttpServletResponse resp, int status, String errorMessage) throws IOException {
        resp.setStatus(status);
        String json = String.format(Locale.ROOT, "{Error: %s}", errorMessage);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(json);
    }
}
