package com.ceox.intelligence.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient chatClient;

    @Autowired // Spring inyectará automáticamente el ChatClient configurado
    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat") // Este endpoint responderá a /chat
    public String chat(@RequestParam(value = "message", defaultValue = "Tell me a joke!") String message) {
        // Envía el mensaje al modelo Gemini y devuelve la respuesta
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }

    // Endpoint para probar si la app responde
    @GetMapping("/")
    public String home() {
        return "¡La aplicación Spring Boot está funcionando! Prueba /chat?message=tu_pregunta";
    }
}