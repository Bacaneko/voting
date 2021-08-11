package ru.bacaneco.voting.model;



import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.Instant;

@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity {

    @Email
    @Size(max = 100)
    private String email;

    @NotBlank
    @Size(min = 5, max = 100)
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @NotNull
    @PastOrPresent
    private Instant registered = Instant.now();

    protected boolean enabled = true;

    public User() {
    }

    public User(Integer id, String name, String email, String password, Role role) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.role = role;
        this.registered = Instant.now();
    }

    public User(Integer id, String name, String email, String password, Role role, Instant registered, boolean enabled) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.role = role;
        this.registered = registered;
        this.enabled = enabled;
    }

    public User(User u) {
        this(u.getId(), u.getName(), u.getEmail(), u.getPassword(), u.getRole(), u.getRegistered(), u.isEnabled());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instant getRegistered() {
        return registered;
    }

    public void setRegistered(Instant registered) {
        this.registered = registered;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", registered=" + registered +
                ", enabled=" + enabled +
                '}';
    }
}
