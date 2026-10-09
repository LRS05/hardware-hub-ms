package com.hardwarehub.api_gateway.security;

import org.springframework.cloud.gateway.server.mvc.filter.SimpleFilterSupplier;
import org.springframework.stereotype.Component;

@Component
public class JwtPropagationFilterSupplier extends SimpleFilterSupplier
{
    public JwtPropagationFilterSupplier()
    {
        super(JwtPropagationFilter.class);
    }
}
