package com.cartube.config;

import com.cartube.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;


public class MyUserDetails implements UserDetails {
    private final User myUser;

    public MyUserDetails(final User myUser) {
        this.myUser = myUser;

    }

    @Override
    public String getUsername() {
        return myUser.getUsername();
    }

    @Override
    public String getPassword() {
        return myUser.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(myUser.getRole().split(", "))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}
