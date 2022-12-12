package az.online.shop.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    USER, CLIENT, MANAGER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}