package com.example.sukagram.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;


public enum Role implements GrantedAuthority {
    USER,
    DELETED,
    BANNED_USER,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
