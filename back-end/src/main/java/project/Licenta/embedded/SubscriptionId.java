package project.Licenta.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class SubscriptionId implements Serializable {
    @Column(name="user_id")
    private Long user_id;

    @Column(name="provider_id")
    private Long provider_id;

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionId s = (SubscriptionId) o;
        return Objects.equals(this.user_id, s.user_id) && Objects.equals(this.provider_id, s.provider_id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(user_id, provider_id);
    }
}
