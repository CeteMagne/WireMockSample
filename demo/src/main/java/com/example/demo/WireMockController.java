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
        String message = wireMockClient.sendRequest();
        model.addAttribute("message", message);
        return "message";
    }
}
