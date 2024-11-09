package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WireMockClient {

    private static final String WIREMOCK_URL = "https://63g7v.wiremockapi.cloud/sample-endpoint";
    private static final String WIREMOCK_FAULTY_URL = "https://63g7v.wiremockapi.cloud/sample-endpoint-fault";

    @Autowired
    private RestTemplate wireMockRestTemplate;

    private String requestJson;
    private String responseJson;
    private String extractedMessage;

    public void sendRequest() {
        try {
            requestJson = "{\"exampleKey\": \"exampleValue\"}";
            responseJson = wireMockRestTemplate.getForObject(WIREMOCK_URL, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseJson);
            extractedMessage = root.path("message").asText();
        } catch (Exception e) {
            e.printStackTrace();
            requestJson = "Error creating request";
            responseJson = "Error retrieving response";
            extractedMessage = "Error extracting message";
        }
    }

    // エラーパターンのリクエストを送信するメソッド
    public void sendFaultyRequest() {
        try {
            requestJson = "{\"exampleKey\": \"exampleFaultyValue\"}";

            // エラーパターンのエンドポイントにリクエストを送信
            responseJson = wireMockRestTemplate.getForObject(WIREMOCK_FAULTY_URL, String.class);

            // レスポンスをパースしてメッセージを抽出
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseJson);
            extractedMessage = root.path("message").asText();
        } catch (HttpClientErrorException e) {
            // 404などのHTTPエラーをキャッチしてエラーメッセージを設定
            e.printStackTrace();
            requestJson = "Error: Faulty request sent, but received HTTP error " + e.getStatusCode();
            responseJson = "Error: Faulty response could not be retrieved (HTTP status " + e.getStatusCode() + ")";
            extractedMessage = "Error: Unable to extract message due to HTTP error";
        } catch (Exception e) {
            // その他のエラーをキャッチ
            e.printStackTrace();
            requestJson = "Error: Failed to send faulty request";
            responseJson = "Error: Unable to retrieve faulty response";
            extractedMessage = "Error: Unable to extract message from faulty response";
        }
    }

    public String getRequestJson() {
        return requestJson;
    }

    public String getResponseJson() {
        return responseJson;
    }

    public String getExtractedMessage() {
        return extractedMessage;
    }
}
