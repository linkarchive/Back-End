package project.linkarchive.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "https://localhost:3000",
                        "https://dev.link-archive.com",
                        "https://www.link-archive.com",
                        "https://test.link-archive.com",
                        "https://front-12t545pry-link-archive.vercel.app"
                )
                .allowedMethods("GET", "POST", "PATCH", "DELETE")
                .allowedHeaders("Access-Control-Allow-Origin",
                        "Access-Control-Allow-Methods",
                        "Access-Control-Allow-Headers",
                        "Origin",
                        "X-Requested-With",
                        "Authorization",
                        "Content-Type",
                        "Accept")
                .maxAge(3000);
    }

}