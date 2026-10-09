package com.hardwarehub.api_gateway.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.Nullable;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CookieBearerTokenResolver implements BearerTokenResolver
{
    @Override
    public @Nullable String resolve(HttpServletRequest request)
    {
        if (request.getCookies() == null) return null;

        return Arrays.stream(request.getCookies())
                .filter(c -> c.getName().equals("access-token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);
    }
}
