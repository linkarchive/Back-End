package project.linkarchive.backend.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import project.linkarchive.backend.advice.exception.ExceptionCodeConst;
import project.linkarchive.backend.advice.exception.custom.ForbiddenException;
import project.linkarchive.backend.advice.exception.custom.InvalidException;
import project.linkarchive.backend.advice.exception.custom.UnauthorizedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Slf4j
@Component
public class SecurityExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler {
    private final HandlerExceptionResolver handler;

    public SecurityExceptionHandler(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver handler) {
        this.handler = handler;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ExceptionCodeConst exception = (ExceptionCodeConst) request.getAttribute("exception");

        if (exception.equals(BAD_REQUEST_TOKEN)) {
            handler.resolveException(request, response, null, new InvalidException(BAD_REQUEST_TOKEN));
        }

        if (exception.equals(INVALID_TOKEN)) {
            handler.resolveException(request, response, null, new UnauthorizedException(INVALID_TOKEN));
        }

        if (exception.equals(NOT_FOUND_USER)) {
            handler.resolveException(request, response, null, new InvalidException(NOT_FOUND_USER));
        }

    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        handler.resolveException(request, response, null, new ForbiddenException(FORBIDDEN_ACCESS));
    }
}