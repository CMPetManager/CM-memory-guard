package com.catchthemoment.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", indexes = @Index(name = "usr_mail_index", columnList = "email,name"))
@NamedEntityGraph(name = "usr-entity-graph", attributeNodes = {
        @NamedAttributeNode("id"),
        @NamedAttributeNode("name"),
        @NamedAttributeNode("password") //entity graph for avoiding n+1 problem multiple select queries
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @NotNull
    private Long id;

    public User(Long id, String name, String email, String password) {
        Objects.requireNonNull(this.id = id);
        Objects.requireNonNull(this.name = name, "Invalid or empty name ");
        Objects.requireNonNull(this.email = email, "Invalid email address");
        Objects.requireNonNull(this.password = password, "Invalid password ");
    }

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    @Column(name = "confirmation_reset_token", length = 20)
    private String confirmationResetToken;

    @Column(name = "reset_password_token", length = 20)
    private String resetPasswordToken;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Album> albums;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email)
                && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("email", email)
                .append("password", password)
                .append("confirmationResetToken", confirmationResetToken)
                .append("resetPasswordToken", resetPasswordToken)
                .append("role", role)
                .append("albums", albums)
                .toString();

    }
}
