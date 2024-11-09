package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WireMockClient {

    private static final String WIREMOCK_URL = "https://63g7v.wiremockapi.cloud/sample-endpoint";

    @Autowired
    private RestTemplate wireMockRestTemplate;

    public String sendRequest() {
        return wireMockRestTemplate.getForObject(WIREMOCK_URL, String.class);
    }
}
