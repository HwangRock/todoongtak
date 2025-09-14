package security.jwt;

public record JwtHeader(String alg, String typ) {
    public JwtHeader() {
        this("HS256", "JWT");
    }
}