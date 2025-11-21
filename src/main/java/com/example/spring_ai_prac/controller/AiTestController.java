package com.example.spring_ai_prac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AiTestController {

    @GetMapping("/text/chat")
    public String showTextChatPage() {

        return "text/chat";
    }
}
