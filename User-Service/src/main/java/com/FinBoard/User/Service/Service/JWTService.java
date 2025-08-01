package com.FinBoard.User.Service.Service;

import com.FinBoard.User.Service.DTO.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Log
public class JWTService {

       private String secretKey="";
       public JWTService(){
           try {
               KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
               SecretKey sk= keyGenerator.generateKey();
               secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
           }catch(NoSuchAlgorithmException e){
               log.info("Exception while generating key with keygenerator: "+ e.getMessage());
           }
       }

       public String generateToken(UserPrincipal userPrincipal){
           Map<String , Object> claims= new HashMap<>();
           claims.put("id", userPrincipal.getId());
           claims.put("email", userPrincipal.getUserEmail());
           claims.put("role", userPrincipal.getRole());
           claims.put("userName", userPrincipal.getUserName());
           claims.put("address", userPrincipal.getUserAddress());
           claims.put("profession", userPrincipal.getProfession());
           claims.put("purpose", userPrincipal.getPurpose());
           claims.put("contact", userPrincipal.getUserContact());


           return Jwts.builder()
                   .claims()
                   .add(claims)
                   .subject(userPrincipal.getUsername())
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                   .and()
                   .signWith(getKey())
                   .compact();
       }

       public SecretKey getKey(){
            byte[] keybytes= Decoders.BASE64.decode(secretKey);
             return Keys.hmacShaKeyFor(keybytes);
       }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
