package com.noteshub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class ProtectedTestController {

    @GetMapping("/secure")
    public ResponseEntity<String> securedEndpoint() {
        return ResponseEntity.ok("✅ You are authenticated and allowed to access this secure endpoint.");
    }
}
