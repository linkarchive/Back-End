package project.linkarchive.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.linkarchive.backend.auth.service.OAuthService;
import project.linkarchive.backend.security.FailureHandler;
import project.linkarchive.backend.security.TokenAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain setupSecurity(
            HttpSecurity httpSecurity,
            OAuthService oAuthService
    ) throws Exception {
        return httpSecurity.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable().headers().frameOptions().disable()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(oAuthService), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new FailureHandler())
                .and()
                .build();
    }

}

