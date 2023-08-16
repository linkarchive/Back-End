package project.linkarchive.backend.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.linkarchive.backend.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static project.linkarchive.backend.advice.data.DataConstants.*;
import static project.linkarchive.backend.advice.exception.ExceptionCodeConst.*;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public TokenAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader == null) {
            request.setAttribute("exception", NOT_TOKEN_IN_HEADER);
            filterChain.doFilter(request, response);
            return;
        }

        String[] tokenData = tokenHeader.split(" ");

        if (!tokenData[TOKEN_TYPE_INDEX].equalsIgnoreCase(BEARER)) {
            request.setAttribute("exception", IS_NOT_BEARER);
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenData[TOKEN_DATA_INDEX];

        if (!jwtUtil.isValidatedToken(token)) {
            request.setAttribute("exception", INVALID_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = new TokenAuthentication(token, jwtUtil.getUserId(token));
        authentication.setAuthenticated(true);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        filterChain.doFilter(request, response);
    }

}
