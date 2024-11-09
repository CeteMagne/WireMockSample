package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WireMockController {

    @Autowired
    private WireMockClient wireMockClient;

    @GetMapping("/display-message")
    public String displayMessage(Model model) {
        // リクエストを送信し、レスポンスとメッセージを取得
        wireMockClient.sendRequest();

        // モデルにリクエスト、レスポンス、メッセージの値を追加
        model.addAttribute("requestJson", wireMockClient.getRequestJson());
        model.addAttribute("responseJson", wireMockClient.getResponseJson());
        model.addAttribute("extractedMessage", wireMockClient.getExtractedMessage());

        return "message";
    }
}
