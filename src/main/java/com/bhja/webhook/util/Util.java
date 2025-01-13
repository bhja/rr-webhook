package com.bhja.webhook.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class Util {
    private static final String HASH_256 = "HmacSHA256";

    public static String encryptWithHash256(String message, String key) throws NoSuchAlgorithmException,
            InvalidKeyException {
        Mac hmacSHA256 = Mac.getInstance(HASH_256);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),
                                                        HASH_256);
        hmacSHA256.init(secretKeySpec);
        return HexFormat.of().formatHex(
                hmacSHA256.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean validateHeader(String message, String header, String key) throws Exception {
        return header.equals(encryptWithHash256(message, key));
    }
}
