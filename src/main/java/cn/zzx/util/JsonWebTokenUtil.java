package cn.zzx.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;
import java.util.Date;

/**
 * @author fzh
 * @since 2017/10/1
 */
public final class JsonWebTokenUtil {
    private static final String COMPANY = "zhengzaixingkeji";
    private static final String ISSUER = "zzx.cn";
    private static final String SUBJECT = "admin";
    private static final long PERIOD = 10800000L;
    private static final Key KEY;

    static {
        KEY = generateKey();
    }

    private static Key generateKey() {
        return MacProvider.generateKey();
    }

    public static String generateJWT(String val) {
        long now = System.currentTimeMillis();
        Claims claims = Jwts.claims();
        claims.setSubject(SUBJECT);
        claims.put("val", val);
        claims.put("copyright", COMPANY);
        claims.setIssuer(ISSUER);
        claims.setIssuedAt(new Date(now));
        claims.setExpiration(new Date(now + PERIOD));
        return Jwts.builder()
                .setHeaderParam("typ", "jwt")
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, KEY)
                .compact();
    }

    public static boolean availableJWT(String jwt) {
        if (jwt == null) {
            return false;
        }
        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(KEY).parseClaimsJws(jwt);
            return jws.getBody().getSubject().equals(SUBJECT);
        } catch (ExpiredJwtException | UnsupportedJwtException | SignatureException | MalformedJwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
