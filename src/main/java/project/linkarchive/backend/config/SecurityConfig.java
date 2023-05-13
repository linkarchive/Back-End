package project.linkarchive.backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.linkarchive.backend.auth.service.OAuthService;
import project.linkarchive.backend.security.AuthenticationEntryPointImpl;
import project.linkarchive.backend.security.SecurityArgumentResolver;
import project.linkarchive.backend.security.TokenAuthenticationFilter;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final SecurityArgumentResolver securityArgumentResolver;

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
                .authenticationEntryPoint(new AuthenticationEntryPointImpl())
                .and()
                .build();
    }
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(securityArgumentResolver);
    }

}
