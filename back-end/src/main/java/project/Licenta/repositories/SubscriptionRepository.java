package project.Licenta.repositories;

import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.Licenta.embedded.SubscriptionId;
import project.Licenta.models.Subscription;
import project.Licenta.models.User;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    Subscription findSubscriptionById(SubscriptionId id);

    @Query("select s from Subscription s where s.id.user_id = ?1")
    List<Subscription> findAllById_UserId(Long user_id);
}
