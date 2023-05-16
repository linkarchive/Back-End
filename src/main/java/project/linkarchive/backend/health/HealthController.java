package project.linkarchive.backend.health;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated() == false")
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }

}