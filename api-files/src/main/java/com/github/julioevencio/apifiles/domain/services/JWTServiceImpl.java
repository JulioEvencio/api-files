package com.github.julioevencio.apifiles.domain.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;
import java.util.List;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private Long validityInMilliseconds;

    @Override
    public String createAccessToken(String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        String issueURL = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withSubject(username)
                .withIssuer(issueURL)
                .sign(Algorithm.HMAC256(secretKey.getBytes()))
                .strip();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            return !this.decodedToken(token).getExpiresAt().before(new Date());
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public String getUsername(String token) {
        return this.decodedToken(token).getSubject();
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }

}
