package com.hardwarehub.order_service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient
{
    private RestClient restClient;

    public ProductClient(RestClient.Builder restClient)
    {
        this.restClient = restClient
                .baseUrl("http://product-service/api/products")
                .build();
    }
}
