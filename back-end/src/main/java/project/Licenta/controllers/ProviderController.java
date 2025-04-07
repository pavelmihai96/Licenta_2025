package project.Licenta.controllers;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.Licenta.models.Subscription;
import project.Licenta.models.User;
import project.Licenta.payload.response.UserResponse;
import project.Licenta.services.UserDetailsServiceImpl;

import java.util.List;

@NoArgsConstructor
@RestController
@RequestMapping("/licenta/provs")
@CrossOrigin(origins = "http://localhost:8081")
public class ProviderController {
    @Autowired
    private UserDetailsServiceImpl service;

    public ProviderController(UserDetailsServiceImpl service) { this.service = service; }

    @GetMapping("/{user_id}")
    @PreAuthorize("hasRole('USER')")
    public List<UserResponse> getAll(@PathVariable Long user_id) {
        return service.getAll(user_id);
    }
}
