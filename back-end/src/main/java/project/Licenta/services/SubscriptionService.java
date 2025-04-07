package project.Licenta.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Licenta.embedded.SubscriptionId;
import project.Licenta.models.Subscription;
import project.Licenta.models.User;
import project.Licenta.payload.request.SubReq;
import project.Licenta.payload.response.SubscriptionResponse;
import project.Licenta.repositories.SubscriptionRepository;
import project.Licenta.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subRepository;

    @Autowired
    private UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subRepository = subscriptionRepository;
    }

    public List<SubscriptionResponse> getAll(Long user_id) {
        List<Subscription> subList = subRepository.findAllById_UserId(user_id);
        List<SubscriptionResponse> subscriptionResponseList = new ArrayList<>();
        for (Subscription s : subList) {
            subscriptionResponseList.add(new SubscriptionResponse(s.getId().getProvider_id(), userRepository.findUsernameById(s.getId().getProvider_id()).getUsername(), s.getStatus(), s.getIndex_old(), s.getIndex_read()));
        }
        return subscriptionResponseList;
    }

    public Subscription getById(Long user_id, Long provider_id) {
        return subRepository.findById(new SubscriptionId(user_id, provider_id)).orElse(null);
    }

    public String create(Subscription subscription) {
        User user = userRepository.findById(subscription.getId().getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));

        User provider = userRepository.findById(subscription.getId().getProvider_id()).orElseThrow(() -> new RuntimeException("Provider not found"));

        // Set the relationships because hibernate does not know automatically to
        // populate the User_sub and Provider_sub with data
        subscription.setUser_sub(user);
        subscription.setProvider_sub(provider);
        subRepository.save(subscription);

        return "Added successfully";
    }

    public Subscription update(Long user_id, Long provider_id, Subscription s) {
        Subscription existing = subRepository.findById(new SubscriptionId(user_id, provider_id)).orElse(null);
        if (existing != null) {
            existing.setStatus(s.getStatus());
            existing.setIndex_read(s.getIndex_read());
            existing.setIndex_old(s.getIndex_old());
            return subRepository.save(existing);
        } else {
            return null;
        }
    }

    public String updateStatus(Long user_id, Long provider_id, SubReq subreq) {
        Subscription existing = subRepository.findById(new SubscriptionId(user_id, provider_id)).orElse(null);
        assert existing != null;

        existing.setStatus(subreq.getStatus());
        subRepository.save(existing);
        return "Status updated to " + subreq.getStatus();
        //return new SubscriptionResponse(existing.getId().getProvider_id(), userRepository.findUsernameById(existing.getId().getProvider_id()).getUsername(), existing.getStatus(), existing.getIndex_old(), existing.getIndex_read());
    }

    public void delete(Long user_id, Long provider_id) {
        subRepository.deleteById(new SubscriptionId(user_id, provider_id));
    }
}
