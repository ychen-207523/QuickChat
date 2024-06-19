package ychen.quickchat.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/api/public/test")
    public String publicTest() {
        return "Public access allowed";
    }
}
