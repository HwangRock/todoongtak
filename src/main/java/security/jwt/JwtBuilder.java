package security.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Objects;

public class JwtBuilder {
    private static final ObjectMapper om = new ObjectMapper();
    private final Encrypt encrypt = new Encrypt();

    private String userId;
    private long ttl;
    private String secret;

    public JwtBuilder userId(String userId) {
        this.userId = userId;
        return this;
    }

    public JwtBuilder ttl(long ttl) {
        this.ttl = ttl;
        return this;
    }

    public JwtBuilder secret(String secret) {
        this.secret = secret;
        return this;
    }

    public String build() throws JsonProcessingException, NoSuchAlgorithmException, InvalidKeyException {
        Objects.requireNonNull(userId, "userId required");
        Objects.requireNonNull(secret, "secret required");

        long now = Instant.now().getEpochSecond();
        long expire = now + ttl;

        JwtHeader header = new JwtHeader();
        JwtPayload payload = new JwtPayload(now, expire, userId);

        String headerJson = om.writeValueAsString(header);
        String payloadJson = om.writeValueAsString(payload);

        String h = encrypt.Base64(headerJson);
        String p = encrypt.Base64(payloadJson);
        String unsigned = h + "." + p;

        String signed = encrypt.HmacSha256(unsigned, secret);

        String jwt = unsigned + "." + signed;

        return jwt;
    }
}
