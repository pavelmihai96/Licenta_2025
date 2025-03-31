package project.Licenta.services;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.Licenta.embedded.SubscriptionId;
import project.Licenta.models.Subscription;
import project.Licenta.repositories.SubscriptionRepository;

import java.util.List;

@Service
@NoArgsConstructor
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository thisRepository;

    public SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.thisRepository = subscriptionRepository;
    }

    public List<Subscription> getAll() {
        return thisRepository.findAll();
    }

    public Subscription getById(Long user_id, Long provider_id) {
        return thisRepository.findById(new SubscriptionId(user_id, provider_id)).orElse(null);
    }

    public Subscription create(Subscription subscription) {
        return thisRepository.save(subscription);
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
