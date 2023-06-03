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

@Slf4j
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ExceptionCodeConst exception = (ExceptionCodeConst) request.getAttribute("exception");

        if (exception.equals(ExceptionCodeConst.NOT_TOKEN)) {
            exceptionHandler(response, ExceptionCodeConst.NOT_TOKEN);
            return;
        }

        if (exception.equals(ExceptionCodeConst.INVALID_TOKEN)) {
            exceptionHandler(response, ExceptionCodeConst.INVALID_TOKEN);
            return;
        }

        if (exception.equals(ExceptionCodeConst.NOT_FOUND_USER)) {
            exceptionHandler(response, ExceptionCodeConst.NOT_FOUND_USER);
        }
    }

    public void exceptionHandler(HttpServletResponse response, ExceptionCodeConst error) throws IOException {
        response.setStatus(error.getStatus());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject json = new JSONObject();
        json.put("message", error.getMessage());
        PrintWriter out = response.getWriter();
        out.print(json);
    }
}
