package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class WireMockController {

    @Autowired
    private WireMockClient wireMockClient;

    // 新しいエンドポイントの追加
    @GetMapping("/api/send-request")
    @ResponseBody
    public Map<String, String> sendRequest() {
        wireMockClient.sendRequest();

        Map<String, String> response = new HashMap<>();
        response.put("requestJson", wireMockClient.getRequestJson());
        response.put("responseJson", wireMockClient.getResponseJson());
        response.put("extractedMessage", wireMockClient.getExtractedMessage());
        
        return response;
    }

    @GetMapping("/display-message")
    public String displayMessage(Model model) {
        return "message";
    }
}
