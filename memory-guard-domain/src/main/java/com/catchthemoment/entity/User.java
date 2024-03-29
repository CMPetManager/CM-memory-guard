package com.catchthemoment.entity;

import liquibase.repackaged.org.apache.commons.lang3.builder.ToStringBuilder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", indexes = @Index(name = "usr_mail_index", columnList = "email"))
@NamedEntityGraph(name = "user-graph",attributeNodes = {@NamedAttributeNode(value = "albums")
       , @NamedAttributeNode(value = "image")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "confirmation_reset_token", length = 20)
    private String confirmationResetToken;

    @Column(name = "reset_password_token", length = 20)
    private String resetPasswordToken;

    @Getter
    @Column(name = "enabled")
    private boolean enabled;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Album> albums;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Image image;

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

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
