package com.pji2918.springdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {
    @GetMapping("/quiz")
    public ResponseEntity<String> quiz(@RequestParam("code") int code) {
        return switch (code) {
            case 1 -> //noinspection DataFlowIssue
                    ResponseEntity.created(null).body("Created!"); // 201: Created!
            case 2 -> ResponseEntity.badRequest().body("Bad Request!"); // 400: Bad Request!
            default -> ResponseEntity.ok("OK!"); // 200: OK!
        };
    }

    @PostMapping("/quiz")
    public ResponseEntity<String> quiz2(@RequestBody Code code) {
        if (code.value() == 1) {
            return ResponseEntity.status(403).body("Forbidden!");
        } else {
            return ResponseEntity.ok("OK!");
        }
    }
}


