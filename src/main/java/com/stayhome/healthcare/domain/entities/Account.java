package com.stayhome.healthcare.domain.entities;

import com.stayhome.healthcare.domain.entities.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_id", updatable = false, nullable = false)
    private UUID accountId;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Profile profile;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    // Username will be email for security  ** NOT ACTUAL WAY TO GET USERNAME ** (Used by Spring)
    @Override
    public String getUsername() {
        return email;
    }

    // ** ACTUAL WAY TO GET THE USER'S USERNAME **
    public String getTheUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // TODO: For now true for all accounts
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // TODO: For now true for all accounts
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO: For now true for all accounts
    }

    @Override
    public boolean isEnabled() {
        return true; // TODO: For now true for all accounts
    }
}
