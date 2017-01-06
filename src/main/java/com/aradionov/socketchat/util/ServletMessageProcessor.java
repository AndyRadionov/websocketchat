package com.aradionov.socketchat.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Andrey Radionov
 */
public class ServletMessageProcessor {
    public static void writeResponseMessage(HttpServletResponse resp, int status, String message) throws IOException {
        resp.setStatus(status);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(message);
    }
}
