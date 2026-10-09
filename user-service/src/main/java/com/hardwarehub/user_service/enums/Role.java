package com.hardwarehub.user_service.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.hardwarehub.user_service.enums.Permission.*;

@RequiredArgsConstructor
public enum Role
{
    ADMIN(Set.of(
            ROLE_UPDATE,
            ADMIN_READ,
            STAFF_READ, STAFF_DELETE,
            CUSTOMER_READ,
            PRODUCT_CREATE, PRODUCT_READ, PRODUCT_UPDATE, PRODUCT_DELETE,
            ORDER_READ
    )),
    STAFF(Set.of(
            CUSTOMER_READ,
            PRODUCT_CREATE, PRODUCT_READ, PRODUCT_UPDATE, PRODUCT_DELETE,
            ORDER_READ
    )),
    CUSTOMER(Set.of(
            PRODUCT_READ,
            ORDER_READ, ORDER_UPDATE,
            ORDER_ITEM_CREATE, ORDER_ITEM_UPDATE, ORDER_ITEM_DELETE
    ));

    private final Set<Permission> permissions;

    public Set<SimpleGrantedAuthority> getAuthorities()
    {
        Set<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(p -> new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toSet());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
