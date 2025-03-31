package project.Licenta.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.Licenta.embedded.SubscriptionId;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "subscriptions")
public class Subscription {
    @EmbeddedId
    private SubscriptionId id;

    @MapsId("user_id")
    @ManyToOne
    @JsonBackReference(value="user_sub_serial")
    @JoinColumn(name = "user_id", nullable = false)
    private User user_sub;  // Foreign key to a User with ROLE_USER

    @MapsId("provider_id")
    @ManyToOne
    @JsonBackReference(value="provider_sub_serial")
    @JoinColumn(name = "provider_id", nullable = false)
    private User provider_sub;  // Foreign key to a User with ROLE_PROVIDER

    private String status;
    private Long index_read;
    private Long index_old;
}


