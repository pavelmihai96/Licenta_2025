package project.Licenta.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    //--------------------Relationship between User and Subscription
    //--------------------defined both in here and in Subscription entity
    @OneToMany(mappedBy = "user_sub", cascade = CascadeType.ALL)
    @JsonManagedReference(value="user_sub_serial")
    private Set<Subscription> subscriptionsAsUser = new HashSet<>();

    @OneToMany(mappedBy = "provider_sub", cascade = CascadeType.ALL)
    @JsonManagedReference(value="provider_sub_serial")
    private Set<Subscription> subscriptionsAsProvider = new HashSet<>();
    //-------------------------------------------------------------------

    //--------------------Relationship between User and Invoice
    //--------------------defined both in here and in Invoice entity
    @OneToMany(mappedBy = "user_inv", cascade = CascadeType.ALL)
    @JsonManagedReference(value="user_inv_serial")
    private Set<Invoice> invoicesAsUser = new HashSet<>();

    @OneToMany(mappedBy = "provider_inv", cascade = CascadeType.ALL)
    @JsonManagedReference(value="provider_inv_serial")
    private Set<Invoice> invoicesAsProvider = new HashSet<>();
    //-------------------------------------------------------------------

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
