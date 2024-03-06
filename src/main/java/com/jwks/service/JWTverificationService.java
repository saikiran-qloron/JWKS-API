package com.jwks.service;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import com.nimbusds.jose.jwk.RSAKey;

import java.util.List;

@Service
public class JWTverificationService {
    private final String JWKS_URL = "http://localhost:8080/jwks/json";

    public boolean verifyJWTtoken(String jwtToken) throws Exception {
        RSAPublicKey publicKey = extractRSAPublicKey();

        // parseJWT by splitting the token into 3 parts
        SignedJWT signedJWT = SignedJWT.parse(jwtToken);

        // Create a verifier using the public key
        JWSVerifier verifier = new RSASSAVerifier(publicKey);

        // verify the JWT with public key
        if (signedJWT.verify(verifier)) {
            // JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            return true;
        } else {
            return false;
        }
    }

    public RSAPublicKey extractRSAPublicKey() throws Exception {  // for extracting public key
        URL url = new URL(JWKS_URL);

        // Retrieve the JWKS from the endpoint
        JWKSet jwkSet = JWKSet.load(url);

        // Get the list of JWKs from the JWKS
        List<JWK> keys = jwkSet.getKeys();

        if(keys.get(0) instanceof RSAKey){
            RSAKey rsaKey = (RSAKey) keys.get(0);

          //  Extract and return the RSA public key
            return rsaKey.toRSAPublicKey();
        }

        throw new RuntimeException("No RSA key found in JWKS");
    }
}
