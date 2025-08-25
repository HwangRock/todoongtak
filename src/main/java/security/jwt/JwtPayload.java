package security.jwt;

public class JwtPayload {
    private long iat;
    private long exp;
    private String userId;

    public long getIat() {
        return iat;
    }

    public long getExp() {
        return exp;
    }

    public String getUserId() {
        return userId;
    }

    public JwtPayload(long iat, long exp, String userId) {
        this.iat = iat;
        this.exp = exp;
        this.userId = userId;
    }
}
