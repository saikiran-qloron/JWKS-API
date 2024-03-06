package com.jwks.utils;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;

public class RSAKeyMaker {

    public static RSAKey make(Integer keySize, KeyUse keyUse, Algorithm keyAlg, KeyIdGenerator kid, RSAPublicKey pubKey, RSAPrivateKey privKey) {
        RSAKey rsaKey = new RSAKey.Builder(pubKey)
                .privateKey(privKey)
                .algorithm(keyAlg)
                .keyUse(keyUse)
                .keyID(kid.generate(null, pubKey.getEncoded()))
                .build();

        return rsaKey;
    }
}
