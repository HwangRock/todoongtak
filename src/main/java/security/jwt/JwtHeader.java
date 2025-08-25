package security.jwt;

public class JwtHeader {
    private final String alg = "HS256";
    private final String typ = "JWT";

    public String getAlg() {
        return alg;
    }

    public String getTyp() {
        return typ;
    }
}
