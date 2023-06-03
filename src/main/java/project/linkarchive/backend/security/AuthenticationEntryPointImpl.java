package project.linkarchive.backend.security;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    public static final String JSON = "application/json";
    public static final String ENCODING = "UTF-8";
    public static final String MESSAGE = "message";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ExceptionCodeConst exception = (ExceptionCodeConst) request.getAttribute("exception");

        if (exception.equals(NOT_TOKEN)) {
            exceptionHandler(response, NOT_TOKEN);
            return;
        }

        if (exception.equals(INVALID_TOKEN)) {
            exceptionHandler(response,INVALID_TOKEN);
            return;
        }

        if (exception.equals(NOT_FOUND_USER)) {
            exceptionHandler(response,NOT_FOUND_USER);
        }
    }

    public void exceptionHandler(HttpServletResponse response, ExceptionCodeConst error) throws IOException {
        response.setStatus(error.getStatus());
        response.setContentType(JSON);
        response.setCharacterEncoding(ENCODING);
        JSONObject json = new JSONObject();
        json.put(MESSAGE, error.getMessage());
        PrintWriter out = response.getWriter();
        out.print(json);
    }
}