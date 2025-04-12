package project.Licenta.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.Licenta.models.EnumRole;
import project.Licenta.models.Role;
import project.Licenta.models.User;
import project.Licenta.payload.request.LoginRequest;
import project.Licenta.payload.request.SignupRequest;
import project.Licenta.payload.response.JwtResponse;
import project.Licenta.payload.response.MessageResponse;
import project.Licenta.repositories.RoleRepository;
import project.Licenta.repositories.UserRepository;
import project.Licenta.security.jwt.JwtUtils;
import project.Licenta.services.UserDetailsImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/licenta/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generate(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        //String strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(EnumRole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {

            strRoles.forEach(role -> {
                if (role.equals("provider")) {
                    Role providerRole = roleRepository.findByName(EnumRole.ROLE_PROVIDER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(providerRole);
                } else {
                    Role userRole = roleRepository.findByName(EnumRole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });

            /*
            if (strRoles.equals("provider")) {
                Role providerRole = roleRepository.findByName(EnumRole.ROLE_PROVIDER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(providerRole);
            } else {
                Role userRole = roleRepository.findByName(EnumRole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            }
             */
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Registered successfully!"));
    }
}
