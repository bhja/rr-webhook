package com.bhja.webhook.controller;

import com.bhja.webhook.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequiredArgsConstructor
public class Listener {

    @Value("${secret.key}")
    private String secretKey;

    @PostMapping("/api/test/webhook")
    public ResponseEntity<Void> readMessage(@RequestBody String payload,
                                            @RequestHeader("X-RR-Signature") String signature) {
        CompletableFuture.runAsync(() -> {
            try {
                if (Util.validateHeader(payload, signature, secretKey)) {
                    log.info("Successful");
                } else {
                    log.warn("This payload is not from rocket referral");
                }
            } catch (Exception e) {
                log.error("Error validating the header {}", e.getMessage());
            }
        });
        return ResponseEntity.ok().build();
    }


}
