package br.com.estudo.message_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public OrderClient(RestTemplate restTemplate,@Value("${services.order-service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public String getOrderById(Long id) {
        String url = baseUrl + "/order/" + id;
        return restTemplate.getForObject(url, String.class);
    }
}
