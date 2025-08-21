package security.jwt;

public class Jwt {
    private JwtHeader jwtHeader;
    private JwtPayload jwtPayload;
    private String signature;

    public Jwt(JwtHeader jwtHeader, JwtPayload jwtPayload, String signature) {
        this.jwtHeader = jwtHeader;
        this.jwtPayload = jwtPayload;
        this.signature = signature;
    }
}
