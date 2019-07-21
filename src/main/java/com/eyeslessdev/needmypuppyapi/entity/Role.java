package com.eyeslessdev.needmypuppyapi.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN, VISITOR, CREATEDUSER, BLOCKEDUSER, DELETEDUSER;

    @Override
    public String getAuthority() {
        return name();
    }
}
