package project.Licenta.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Licenta.embedded.SubscriptionId;
import project.Licenta.models.Subscription;
import project.Licenta.models.User;
import project.Licenta.repositories.SubscriptionRepository;
import project.Licenta.repositories.UserRepository;

import java.util.List;

@Service
@NoArgsConstructor
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository thisRepository;

    @Autowired
    private UserRepository userRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.thisRepository = subscriptionRepository;
    }

    public List<Subscription> getAll() {
        return thisRepository.findAll();
    }

    public Subscription getById(Long user_id, Long provider_id) {
        return thisRepository.findById(new SubscriptionId(user_id, provider_id)).orElse(null);
    }

    public String create(Subscription subscription) {
        User user = userRepository.findById(subscription.getId().getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));

        User provider = userRepository.findById(subscription.getId().getProvider_id()).orElseThrow(() -> new RuntimeException("Provider not found"));

        // Set the relationships because hibernate does not know automatically to
        // populate the User_sub and Provider_sub with data
        subscription.setUser_sub(user);
        subscription.setProvider_sub(provider);
        thisRepository.save(subscription);

        return "Added successfully";
    }

    public Subscription update(Long user_id, Long provider_id, Subscription s) {
        Subscription existing = thisRepository.findById(new SubscriptionId(user_id, provider_id)).orElse(null);
        if (existing != null) {
            existing.setStatus(s.getStatus());
            existing.setIndex_read(s.getIndex_read());
            existing.setIndex_old(s.getIndex_old());
            return thisRepository.save(existing);
        } else {
            return null;
        }
    }

    public Subscription updateStatus(Long user_id, Long provider_id, String updateStatus) {
        Subscription existing = thisRepository.findById(new SubscriptionId(user_id, provider_id)).orElse(null);

        if (existing != null) {
            existing.setStatus(updateStatus);
            return thisRepository.save(existing);
        } else {
            return null;
        }
    }

    public void delete(Long user_id, Long provider_id) {
        thisRepository.deleteById(new SubscriptionId(user_id, provider_id));
    }
}
