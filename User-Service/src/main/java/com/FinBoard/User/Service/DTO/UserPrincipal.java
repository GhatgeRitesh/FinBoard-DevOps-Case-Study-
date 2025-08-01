package com.FinBoard.User.Service.DTO;


import com.FinBoard.User.Service.Entities.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Collections;


public class UserPrincipal implements UserDetails {
    private final Client user;

    public UserPrincipal(Client user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getRole() {
        return user.getRole();
    }

    public String getUserEmail() {
        return user.getUserEmail();
    }

    public String getUserName() {
        return user.getUserName();
    }

    public String getUserAddress() {
        return user.getUserAddress();
    }

    public String getProfession() {
        return user.getProfession();
    }

    public String getPurpose() {
        return user.getPurpose();
    }

    public Long getUserContact() {
        return user.getUserContact();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("User")); // Or use dynamic role
    }

    @Override
    public String getPassword() {
        return user.getUserPassword(); // Will not go in token
    }

    @Override
    public String getUsername() {
        return user.getUserEmail();  // Better to use email as username
    }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

