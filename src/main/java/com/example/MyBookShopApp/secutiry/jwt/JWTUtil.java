package com.example.MyBookShopApp.secutiry.jwt;

import com.example.MyBookShopApp.secutiry.UserDetailsI;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtil {

    @Value("${auth.secret}")
    private String secret;

    private String createToken(Map<String,Object> claims, String username){
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256,secret)
                .compact();
    }

    public String generateToken(UserDetailsI userDetails){
        Map<String,Object> claims = new HashMap<>();
        return  createToken(claims,userDetails.getEmail());
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //возвращает имя юзера
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }

    //возвращает срок годности токена
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }

    //истек ли срок годности токена
    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    //проверка равен ли username из userDetails  name из токена
    public Boolean validateToken(String token, UserDetailsI userDetails){
        String username = extractUsername(token);
        return (username.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }
}
