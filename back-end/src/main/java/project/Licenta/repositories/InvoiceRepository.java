package project.Licenta.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.Licenta.embedded.InvoiceId;
import project.Licenta.embedded.SubscriptionId;
import project.Licenta.models.Invoice;
import project.Licenta.models.Subscription;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, InvoiceId> {
    @Query("select i from Invoice i where i.id.user_id = ?1")
    List<Invoice> findAllById_UserId(Long user_id);
}
