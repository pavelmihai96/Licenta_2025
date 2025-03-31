package project.Licenta.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/licenta/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('PROVIDER')")
    public String userAccess() {
        return "User Page.";
    }

    @GetMapping("/prov")
    @PreAuthorize("hasRole('PROVIDER')")
    public String providerAccess() {
        return "Provider Page.";
    }

}
