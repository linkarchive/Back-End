package project.linkarchive.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.linkarchive.backend.security.*;
import project.linkarchive.backend.util.JwtUtil;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig implements WebMvcConfigurer {

    public static final String[] EXCLUDE_PATHS = {
            "/health",
            "/auth/**",
            "/nickname",
            "/user/{nickname}",
            "/links/user/{nickname}",
            "/links/archive",
            "/mark/links/**",
            "/mark/tags/**",
            "/tags/**",
            "/follower-list/user/{userId}",
            "/following-list/user/{userId}",
            "tags/archive"
    };

    private final SecurityArgumentResolver securityArgumentResolver;

    public SecurityConfig(SecurityArgumentResolver securityArgumentResolver) {
        this.securityArgumentResolver = securityArgumentResolver;
    }

    @Bean
    public SecurityFilterChain setupSecurity(
            HttpSecurity httpSecurity,
            JwtUtil jwtUtil,
            SecurityExceptionHandler securityExceptionHandler
    ) throws Exception {
        return httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers(EXCLUDE_PATHS).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(securityExceptionHandler)
                .accessDeniedHandler(securityExceptionHandler)
                .and()
                .build();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(securityArgumentResolver);
    }

}