package project.Licenta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.Licenta.embedded.SubscriptionId;
import project.Licenta.models.Subscription;
import project.Licenta.models.User;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
}
