package com.jwks.service;

import com.jwks.models.PID;
import com.jwks.repositories.PIDdao;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public class JWTservice {
    private PIDdao dao;

    public JWTservice(PIDdao dao) {
        this.dao = dao;
    }

    public String createJWTtoken(){
        PID pid = dao.findPidById(1);

        String token = Jwts.builder()
                .setSubject("test")
                .setIssuer("test@domain.com")
                .setExpiration(new Date(2024, 3, 12))
                .claim("roles", new String[] { "user", "admin" })
                .signWith(SignatureAlgorithm.RS256, pid.getPrivateKey())  // signing with private key
                .compact();

        return token;
    }
}
