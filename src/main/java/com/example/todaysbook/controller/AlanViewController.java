package com.example.todaysbook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AlanViewController {
    @GetMapping("/alan/chat")
    public String chatbotPage(Model model) {
        return "alan/chat";
    }
}
