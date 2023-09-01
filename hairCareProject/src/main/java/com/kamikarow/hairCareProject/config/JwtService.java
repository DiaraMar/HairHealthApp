package com.kamikarow.hairCareProject.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    /**
     * Secret_key is used to code or decode the
     * Base.64 is the common level of security needed.
     * Base.64 is hyper sensible to specials characters and can leads to errors.
     * If bit doesn't meet bit requirement it can also lead to errors
     */
    static Properties properties = new Properties();
    private static final String SECRET_KEY = "6E3272357538782F413F4428472D4B6150645367566B59703373367639792442"; //todo : move to application properties

    /**
     * extractAllClaims
     * set sign-in key to decode token
     * build object
     * try to parse claims of token
     * get body
     *
     * @param token
     * @return Claims
     */
    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * getSignInKey
     * Generate sign-in key to digitally sign the JWT
     *
     * @return Key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);//todo :extract variable from method
        // requestTokenHeader.substring("Bearer ".length());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extract Claim method extract specific claim based on claimsResolver param
     *
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return type depends of ClaimResolver param
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * extractUsername method extract getSubject attribute from Claims
     * getSubject store username
     *
     * @param token
     * @return String (username)
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * IsTokenValid method Validate or invalidate token based on token expiration and equality between username within token and  username within UserDetails
     *
     * @param token
     * @param userDetails
     * @return
     */

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    /**
     * GenerateToken
     *
     * @param extraClaims of type Map contains extra claims we want to add
     * @param userDetails stored username need to initialize subjectVariable of the token
     *                    IssuedAt attribute of the token is set up with initialize time of the variable of claims
     *                    Expiration attribute of the token is set up with expiration time of the token : 24h plus 1 000 milliseconds after it creation
     *                    Sign-in key and SignatureAlgorithm are added
     * @return String representing token
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        String jwts = Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 24)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
        return jwts;
    }

    /**
     * GenerateToken method provide capacity to no populate token with extra claims
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }


}