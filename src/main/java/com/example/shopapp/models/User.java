package com.example.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbstractAuditingModel implements UserDetails {

    @Column(name = "name", length = 350, nullable = false)
    String name;

    @Column(name = "full_name", length = 100)
    String fullName;

    @Column(name = "phone_number", length = 11, nullable = false)
    String phoneNumber;

    @Column(name = "address", length = 200, nullable = false)
    String password;

    boolean active;

    @Column(name = "date_of_birth")
    Date dateOfBirth;

    @Column(name = "facebook_account_id")
    int facebookAccountId;

    @Column(name = "google_account_id")
    int googleAccountId;

    @ManyToMany
    Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .flatMap(role -> {
                    Set<GrantedAuthority> authorities = role.getPermissions().stream()
                            .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                            .collect(Collectors.toSet());
                    authorities.add(new SimpleGrantedAuthority("Role_" + role.getName()));
                    return authorities.stream();
                }).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
