package io.siliconsavannah.backend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String key = "38364244617577626a384e6853615942384a6b3567626e773265545271317269";
    //private static final String key = "6eeZsYEaUcjRU5VHuQh5sWEtm0n8SDt4";
    //private static final SecretKey key = Jwts.SIG.HS256.key().build();
    public String extractUsername(String jws) {
        return extractClaim(jws, Claims::getSubject);
    }

    public <T> T extractClaim(String jws, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(jws);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails){
        return generateToken((new HashMap<>()),userDetails);
    }
    public String generateToken(Map<String, Object> extraClaims,
                                UserDetails userDetails){
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*24*60*60))
                .claims(extraClaims)
                .signWith(getSigningKey())
                .compact();
    }
    public String generateRefreshToken(Map<String,Object> extraClaims,
                                       UserDetails userDetails){
        return Jwts
                .builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*24*60*60))
                .claims(extraClaims)
                .signWith(getSigningKey())
                .compact();
    }

    public boolean isTokenValid(String jws, UserDetails userDetails){
        final String username = extractUsername(jws);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(jws);

    }

    private boolean isTokenExpired(String jws) {
        Date expiration = extractClaim(jws, Claims::getExpiration);
        return expiration.before(new Date());
    }

    public Claims extractAllClaims(String jws){
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(jws)
                .getPayload();
    }
    public SecretKey getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(key);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public void setSigningKey(){
        SecretKey key = Jwts.SIG.HS256.key().build();
        String secretString = Encoders.BASE64.encode(key.getEncoded());
    }
}
