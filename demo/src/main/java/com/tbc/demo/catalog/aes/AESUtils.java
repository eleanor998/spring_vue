package com.tbc.demo.catalog.aes;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

public class AESUtils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) {
        try {
            String plaintext = "Hello, World!";
            String secretKey = generateSecretKey();
            String encryptedText = encrypt(plaintext, secretKey);
            String decryptedText = decrypt(encryptedText, secretKey);

            System.out.println("Original: " + plaintext);
            System.out.println("Encrypted: " + encryptedText);
            System.out.println("Decrypted: " + decryptedText);

            // TODO: Provide the secretKey and the encryption/decryption instructions to the third party.
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        SecureRandom secureRandom = new SecureRandom();
        keyGenerator.init(256, secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    private static String encrypt(String plaintext, String secretKey) throws Exception {
        Map<Character, Integer> sortedMap = sortString(plaintext);
        String sortedPlaintext = mapToString(sortedMap);

        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(sortedPlaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decrypt(String encryptedText, String secretKey) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(secretKey), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedPlaintext = new String(decryptedBytes, StandardCharsets.UTF_8);
        Map<Character, Integer> sortedMap = stringToMap(decryptedPlaintext);
        String originalPlaintext = mapToString(sortedMap);
        return originalPlaintext;
    }

    private static Map<Character, Integer> sortString(String input) {
        Map<Character, Integer> map = new TreeMap<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            map.put(c, i);
        }
        return map;
    }

    private static String mapToString(Map<Character, Integer> map) {
        StringBuilder sb = new StringBuilder();
        for (char c : map.keySet()) {
            sb.append(c);
        }
        return sb.toString();
    }

    private static Map<Character, Integer> stringToMap(String input) {
        Map<Character, Integer> map = new TreeMap<>();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            map.put(c, i);
        }
        return map;
    }
}
