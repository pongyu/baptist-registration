package org.bapp.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bapp.exception.TokenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

public class TokenAuthenticationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 1;
    private String secret = "ThisIsASecret";
    private String tokenPrefix = "Bearer";
    private String headerString = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username){

        // generate token
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
        logger.info("Generated JWT Token {} ", JWT);
        logger.info("Token Prefix: {}", tokenPrefix);
        logger.info("Header String: {}", headerString);
        response.addHeader(headerString, tokenPrefix + " " + JWT);
        logger.info("Appended header to the response: {} ", response.getHeader(headerString));

    }

    public Authentication getAuthentication(HttpServletRequest request){
        String token = request.getHeader(headerString);

        try{
            if(token.contains("Bearer")){
                token = token.substring(7, token.length());
            }
        }catch (NullPointerException e){
            throw new TokenNotFoundException("Authentication Header not found in request header");
        }

        if(token != null){

            // parse token
            String username = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();

            logger.info("Parse JWT Token: {} ", token);
            logger.info("JWT Extracted User: {}", username);
            if(username != null){ // we managed to retrieve user
                return new AuthenticatedUser(username);
            }
        }
        return null;
    }

}
