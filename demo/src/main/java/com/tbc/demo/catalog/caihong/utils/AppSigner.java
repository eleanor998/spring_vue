package com.tbc.demo.catalog.caihong.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * 百度APP认证签名串生成
 */
public class AppSigner {


    private static final String BCE_AUTH_VERSION = "bce-auth-v1";
    private static final String BCE_PREFIX_LOWER_CASE = "x-bce-";
    private static final String SIGNATURE_HEADER_NAME = "X-Bce-Signature";
    private static final String ONLINE_USELESS_PATH = "/seller-open";
    private static final String X_BCE_STAGE = "X-Bce-Stage";

    private static final Set<String> DEFAULT_HEADERS_TO_SIGN = new HashSet<>(Arrays.asList("host", "x-bce-date"));

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC);

    private static final String HEADER_JOINER = "\n";

    private static final String QUERY_STRING_JOINER = "&";
    private static final String SIGNED_HEADER_STRING_JOINER = ";";

    private Credential credential = new Credential("6264ab66934941deb0ab97c34e912da6", "6504e03c9a9b4368a4996557d22f6db9");
    private String env = "release";

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public AppSigner(Credential credential) {
        this.credential = credential;
    }

    public AppSigner() {
    }

    public AppSigner(Credential credential, String env) {
        this.credential = credential;
        this.env = env;
    }


    /**
     * 生成签名参数
     *
     * @param uri
     * @return
     */
    public Map<String, String> signGenerate(URI uri) {
        String time = DATE_TIME_FORMATTER.format(new Date().toInstant());
        SignOptions options = new SignOptions();
        options.setTimestamp(time);
        options.setHeadersToSign(new HashSet<>(Arrays.asList("host", "x-bce-date")));
        Map<String, String> headers = new HashMap<>();
        headers.put("Host", uri.getHost());
        headers.put("x-bce-date", time);
        this.sign("POST", uri, headers, options);
        headers.put("Content-Type", "application/json");
        headers.put(X_BCE_STAGE, env);
        return headers;
    }

    /**
     * Sign the given request with the given credential.
     * <p>
     * Signature header will be added to the passed-in request.
     *
     * @param method  http method
     * @param uri     path and query will be used
     * @param headers must contain host header
     */
    public void sign(String method, URI uri, Map<String, String> headers) {
        sign(method, uri, headers, null);
    }

    /**
     * Sign the given request with the given credential.
     * <p>
     * Signature header will be added to the passed-in request.
     *
     * @param method  http method
     * @param uri     path and query will be used to generate signature
     * @param headers must contain host header
     * @param options nullable
     */
    public void sign(String method, URI uri, Map<String, String> headers, SignOptions options) {
        Map<String, String> httpHeaders = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        httpHeaders.putAll(headers);

        Objects.requireNonNull(httpHeaders.get("host"), "Host header is required.");

        if (options == null) {
            options = SignOptions.DEFAULT;
        }

        String timestamp = options.getTimestamp();

        if (timestamp == null) {
            timestamp = DATE_TIME_FORMATTER.format(Instant.now());
        }

        String authString = BCE_AUTH_VERSION + "/" + credential.getAppKey() + "/"
                + timestamp + "/" + options.getExpirationInSeconds();

        String signingKey = SignUtils.sha256Hex(credential.getSecretKey(), authString);

        System.out.println("signingKey: " + signingKey);

        // Formatting the URL with signing protocol.
        String canonicalURI = this.getCanonicalURIPath(uri.getPath());
        // Formatting the query string with signing protocol.
        String canonicalQueryString = getCanonicalQueryString(uri.getRawQuery());
        // Sorted the headers should be signed from the request.
        SortedMap<String, String> headersToSign =
                getHeadersToSign(httpHeaders, options.getHeadersToSign());
        // Formatting the headers from the request based on signing protocol.
        String canonicalHeader = this.getCanonicalHeaders(headersToSign);
        String signedHeaders = "";
        if (options.getHeadersToSign() != null) {
            signedHeaders = String.join(SIGNED_HEADER_STRING_JOINER, headersToSign.keySet());
            signedHeaders = signedHeaders.trim().toLowerCase();
        }

        String canonicalRequest = String.join(HEADER_JOINER, method.toUpperCase(), canonicalURI,
                canonicalQueryString, canonicalHeader);

        System.out.println("CanonicalRequest:" + canonicalRequest.replaceAll(HEADER_JOINER, "-->"));

        // Signing the canonical request using key with sha-256 algorithm.

        String signature = SignUtils.sha256Hex(signingKey, canonicalRequest);

        String signatureHeaderValue = authString + "/" + signedHeaders + "/" + signature;


        System.out.println(SIGNATURE_HEADER_NAME + ":" + signatureHeaderValue);

        headers.put(SIGNATURE_HEADER_NAME, signatureHeaderValue);

    }

    /**
     * 百度msg消息鉴权签名
     *
     * @param messageBody 消息body json string
     * @return String 校验串
     */
    public String msgSign(String messageBody) {
        try {
            // 拼接原始串
            String originStr = credential.getAppKey() + messageBody + credential.getSecretKey();

            // 计算MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(originStr.getBytes(StandardCharsets.UTF_8));

            StringBuilder builder = new StringBuilder();
            byte[] var2 = digest.digest();

            for (byte b : var2) {
                builder.append(Integer.toHexString(b >> 4 & 15));
                builder.append(Integer.toHexString(b & 15));
            }

            return builder.toString();
        } catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
            return null;
        }
    }

    private String getCanonicalURIPath(String path) {
        if (path == null) {
            return "/";
        } else if (path.startsWith("/")) {
            return SignUtils.normalizePath(path.replace(ONLINE_USELESS_PATH, ""));
        } else {
            return "/" + SignUtils.normalizePath(path);
        }
    }

    private static String getCanonicalQueryString(String queryStr) {
        if (queryStr == null || queryStr.isEmpty()) {
            return "";
        }

        List<String> queryStrings = new ArrayList<>();

        for (String pair : queryStr.split("&")) {
            String[] kv = pair.split("=", 2);
            String queryString = SignUtils.normalize(kv[0]) + "=";
            if (kv.length == 2) {
                queryString += SignUtils.normalize(kv[1]);
            }
            queryStrings.add(queryString);
        }

        Collections.sort(queryStrings);

        return String.join(QUERY_STRING_JOINER, queryStrings);
    }

    private String getCanonicalHeaders(SortedMap<String, String> headers) {
        if (headers.isEmpty()) {
            return "";
        }

        List<String> headerStrings = new ArrayList<>();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (key == null) {
                continue;
            }
            String value = entry.getValue();
            if (value == null || value.isEmpty()) {
                throw new RuntimeException("Header to sign should have non-empty value.");
            }
            headerStrings.add(SignUtils.normalize(key.trim().toLowerCase()) + ':' + SignUtils.normalize(value.trim()));
        }
        Collections.sort(headerStrings);

        return String.join(HEADER_JOINER, headerStrings);
    }

    private SortedMap<String, String> getHeadersToSign(Map<String, String> headers, Set<String> headersToSign) {
        SortedMap<String, String> ret = new TreeMap<>();
        if (headersToSign != null) {
            Set<String> tempSet = new HashSet<>();
            for (String header : headersToSign) {
                tempSet.add(header.trim().toLowerCase());
            }
            headersToSign = tempSet;
        }
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            String key = entry.getKey();
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                if ((headersToSign == null && this.isDefaultHeaderToSign(key))
                        || (headersToSign != null && headersToSign.contains(key.toLowerCase())
                        && !SIGNATURE_HEADER_NAME.equalsIgnoreCase(key))) {
                    ret.put(key, entry.getValue());
                }
            }
        }
        return ret;
    }

    private boolean isDefaultHeaderToSign(String header) {
        header = header.trim().toLowerCase();
        return header.startsWith(BCE_PREFIX_LOWER_CASE) || DEFAULT_HEADERS_TO_SIGN.contains(header);
    }

    public static class SignUtils {

        private static final BitSet URI_UNRESERVED_CHARACTERS = new BitSet();
        private static final String[] PERCENT_ENCODED_STRINGS = new String[256];
        private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

        static {
            for (int i = 'a'; i <= 'z'; i++) {
                URI_UNRESERVED_CHARACTERS.set(i);
            }
            for (int i = 'A'; i <= 'Z'; i++) {
                URI_UNRESERVED_CHARACTERS.set(i);
            }
            for (int i = '0'; i <= '9'; i++) {
                URI_UNRESERVED_CHARACTERS.set(i);
            }
            URI_UNRESERVED_CHARACTERS.set('-');
            URI_UNRESERVED_CHARACTERS.set('.');
            URI_UNRESERVED_CHARACTERS.set('_');
            URI_UNRESERVED_CHARACTERS.set('~');

            for (int i = 0; i < PERCENT_ENCODED_STRINGS.length; ++i) {
                PERCENT_ENCODED_STRINGS[i] = String.format("%%%02X", i);
            }
        }

        public static String join(StringJoiner joiner, Iterable<String> iterable) {
            iterable.forEach(joiner::add);
            return joiner.toString();
        }

        public static String normalizePath(String path) {
            return normalize(path).replace("%2F", "/");
        }

        /**
         * Normalize a string for use in BCE web service APIs. The normalization algorithm is:
         * <p>
         * <ol>
         *   <li>Convert the string into a UTF-8 byte array.</li>
         *   <li>Encode all octets into percent-encoding, except all URI unreserved characters per the RFC 3986.</li>
         * </ol>
         *
         * <p>
         * All letters used in the percent-encoding are in uppercase.
         *
         * @param value the string to normalize.
         * @return the normalized string.
         */
        public static String normalize(String value) {
            StringBuilder builder = new StringBuilder();
            for (byte b : value.getBytes(StandardCharsets.UTF_8)) {
                if (URI_UNRESERVED_CHARACTERS.get(b & 0xFF)) {
                    builder.append((char) b);
                } else {
                    builder.append(PERCENT_ENCODED_STRINGS[b & 0xFF]);
                }
            }
            return builder.toString();
        }

        private static String sha256Hex(String signingKey, String stringToSign) {
            try {
                Mac instance = Mac.getInstance("HmacSHA256");
                instance.init(new SecretKeySpec(signingKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
                return bytesToHex(instance.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8)));
            } catch (Exception e) {
                throw new RuntimeException("Failed to generate signature.", e);
            }
        }

        private static String bytesToHex(byte[] bytes) {
            char[] hexChars = new char[bytes.length * 2];
            for (int j = 0; j < bytes.length; j++) {
                int v = bytes[j] & 0xFF;
                hexChars[j * 2] = HEX_ARRAY[v >>> 4];
                hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
            }
            return new String(hexChars);
        }
    }

    public static class SignOptions {

        public static final SignOptions DEFAULT = new SignOptions();

        private String timestamp;
        private int expirationInSeconds = 1800;
        private Set<String> headersToSign;

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public int getExpirationInSeconds() {
            return expirationInSeconds;
        }

        public void setExpirationInSeconds(int expirationInSeconds) {
            this.expirationInSeconds = expirationInSeconds;
        }

        public Set<String> getHeadersToSign() {
            return headersToSign;
        }

        public void setHeadersToSign(Set<String> headersToSign) {
            this.headersToSign = headersToSign;
        }
    }

    public static class Credential {
        private String appKey;
        private String secretKey;

        public Credential(String appKey, String secretKey) {
            this.appKey = appKey;
            this.secretKey = secretKey;
        }

        public String getAppKey() {
            return appKey;
        }

        public void setAppKey(String appKey) {
            this.appKey = appKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }
}
