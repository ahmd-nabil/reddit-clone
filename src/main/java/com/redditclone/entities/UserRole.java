package com.redditclone.entities;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public enum UserRole {
    USER(new ArrayList<>()),
    ADMIN(new ArrayList<>());

    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    UserRole(List<String> permissions) {
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        for(String permission: permissions)
            authorities.add(new SimpleGrantedAuthority(permission));
    }

    public List<SimpleGrantedAuthority> getAuthorities() {
        return authorities;
    }
}
