package security.jwt;

public record JwtPayload(long iat, long exp, String userId) {
}