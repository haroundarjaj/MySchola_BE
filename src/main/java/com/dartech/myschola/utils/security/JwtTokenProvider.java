package com.dartech.myschola.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpirationDate;

    public String generateToken(
            Map<String, Object> claims,
            UserDetails userDetails
    ) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);
        SecretKey key = getSignInKey();
        System.out.println("auth key");
        System.out.println(key);
        System.out.println(Arrays.toString(key.getEncoded()));
        System.out.println("JWT Secret Key: " + Arrays.toString(key.getEncoded()));
        String token = Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expireDate)
                .signWith(key)
                .compact();
        System.out.println("JWT Token: " + token);
        return token;
    }

    private SecretKey getSignInKey() {
        System.out.println("jwtSecret" + jwtSecret);
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    // extract username from JWT token
    public String getUsername(String token){

        return extractClaim(token, Claims::getSubject);
    }

    public Claims extractAllClaims(String token){
        SecretKey key = getSignInKey();
        System.out.println("access key");
        System.out.println(key);
        System.out.println("JWT Secret Key: " + Arrays.toString(key.getEncoded()));
        System.out.println(Arrays.toString(key.getEncoded()));
        System.out.println("JWT Token: " + token);
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T extractClaim (String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // validate JWT token
    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = getUsername(token);
        Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parse(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
