package project.Licenta.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.Licenta.models.EnumRole;
import project.Licenta.models.Role;
import project.Licenta.models.User;
import project.Licenta.payload.response.UserResponse;
import project.Licenta.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        Map<String, Set<Role>> user_roles = users.stream()
                .collect(Collectors.toMap(User::getUsername, User::getRoles));

        List<UserResponse> userResponses = new ArrayList<>();
        for(Map.Entry<String, Set<Role>> i : user_roles.entrySet()) {
            if (i.getValue().stream().anyMatch(j -> j.getName() == EnumRole.ROLE_PROVIDER)){
                //List<String> userEmailByUsername = users.stream().map(j -> j.getEmailByUsername(i.getKey())).toList();
                //List<User> userEmailByUsername = users.stream().map(j -> userRepository.findEmailByUsername(i.getKey())).toList();
                userResponses.add(new UserResponse(userRepository.findIdByUsername(i.getKey()).getId(), userRepository.findEmailByUsername(i.getKey()).getEmail(), i.getKey()));
            }
        }
        return userResponses;
    }

}
