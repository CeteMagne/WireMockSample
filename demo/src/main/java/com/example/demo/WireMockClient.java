package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WireMockClient {

    private static final String WIREMOCK_URL = "https://63g7v.wiremockapi.cloud/sample-endpoint";

    @Autowired
    private RestTemplate wireMockRestTemplate;

    private String requestJson;       // リクエスト内容を格納
    private String responseJson;      // レスポンス内容を格納
    private String extractedMessage;  // 抽出したメッセージを格納

    public void sendRequest() {
        try {
            // リクエストJSONの設定
            requestJson = "{\"exampleKey\": \"exampleValue\"}";

            // リクエストを送信し、レスポンスを取得
            responseJson = wireMockRestTemplate.getForObject(WIREMOCK_URL, String.class);

            // JSONをパースして、messageフィールドの値を抽出
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

    // ゲッターメソッドを追加
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
