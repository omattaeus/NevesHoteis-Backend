package br.com.nevesHoteis.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("admin"),
    EMPLOYEE("employee"),
    USER("user");
    private String role;
    Role(String role) {
        this.role= role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
