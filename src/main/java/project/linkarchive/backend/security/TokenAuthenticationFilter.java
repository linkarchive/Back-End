package project.linkarchive.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.linkarchive.backend.auth.service.OAuthService;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final OAuthService oAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String[] tokenData = tokenHeader.split(" ");
        if (tokenData.length != 2) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!tokenData[0].equalsIgnoreCase("bearer")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = tokenData[1];

        if (!oAuthService.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // TODO: 탈퇴한 유저인 경우,, userId 존재 여부 검증 필요
        Authentication authentication = new TokenAuthentication(token, oAuthService.getUserId(token));
        authentication.setAuthenticated(true);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);
        filterChain.doFilter(request, response);

    }

}
