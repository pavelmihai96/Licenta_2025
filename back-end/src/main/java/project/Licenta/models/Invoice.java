package project.Licenta.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.Licenta.embedded.InvoiceId;
import project.Licenta.embedded.SubscriptionId;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @EmbeddedId
    private InvoiceId id;

    @MapsId("user_id")
    @ManyToOne
    @JsonBackReference(value="user_inv_serial")
    @JoinColumn(name = "user_id", nullable = false)
    private User user_inv;  // Foreign key to a User with ROLE_USER

    @MapsId("provider_id")
    @ManyToOne
    @JsonBackReference(value="provider_inv_serial")
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider_inv;  // Foreign key to a User with ROLE_PROVIDER

    private String status;
    private Double amount;
    private Date due_date;
}
