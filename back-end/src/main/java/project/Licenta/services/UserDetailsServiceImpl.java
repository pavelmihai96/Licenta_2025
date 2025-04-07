package project.Licenta.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.Licenta.embedded.SubscriptionId;
import project.Licenta.models.EnumRole;
import project.Licenta.models.Role;
import project.Licenta.models.Subscription;
import project.Licenta.models.User;
import project.Licenta.payload.response.UserResponse;
import project.Licenta.repositories.SubscriptionRepository;
import project.Licenta.repositories.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    SubscriptionRepository subRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public List<UserResponse> getAll(Long user_id) {
        List<User> users = userRepository.findAll();

        Map<String, Set<Role>> user_roles = new HashMap<>();

        for(User u: users){
            if (Objects.equals(u.getUserRole(), "PROVIDER")){
                user_roles.put(u.getUsername(), u.getRoles());
            }
        }

        String subscribed = "";

        List<UserResponse> userResponses = new ArrayList<>();
        for(Map.Entry<String, Set<Role>> i : user_roles.entrySet()) {
            //List<String> userEmailByUsername = users.stream().map(j -> j.getEmailByUsername(i.getKey())).toList();
            //List<User> userEmailByUsername = users.stream().map(j -> userRepository.findEmailByUsername(i.getKey())).toList();
            if (subRepository.findById(new SubscriptionId(user_id, userRepository.findIdByUsername(i.getKey()).getId())).orElse(null) != null){
                subscribed = "Subscribed";
            } else {
                subscribed = "Not Subscribed";
            }
            userResponses.add(new UserResponse(userRepository.findIdByUsername(i.getKey()).getId(), userRepository.findEmailByUsername(i.getKey()).getEmail(), i.getKey(), subscribed));

        }
        return userResponses;
    }

}
