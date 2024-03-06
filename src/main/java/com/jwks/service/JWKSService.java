package com.jwks.service;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jwks.models.PID;
import com.jwks.repositories.PIDdao;
import com.nimbusds.jose.jwk.KeyUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jwks.utils.RSAKeyMaker;
import com.jwks.utils.KeyIdGenerator;
import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.KeyType;

@Service
public class JWKSService {

	private PIDdao dao;

	public JWKSService(PIDdao dao) {
		this.dao = dao;
	}

	public Object getJWKSByKeyType(String size, String alg) throws Exception {
		PID pid = dao.findPidById(1);

		try {
			KeyIdGenerator generator = KeyIdGenerator.get(pid.getKey());

			KeyType keyType = KeyType.parse("RSA");  // key type = RSA
			
			KeyUse keyUse = KeyUse.parse("sig");
			Algorithm keyAlg = null;

			if (alg != null || alg.length() != 0)
				keyAlg = JWSAlgorithm.parse(alg);

			JWK jwk = makeKey(size, keyUse, generator, keyAlg);

			Gson gson = new GsonBuilder().setPrettyPrinting().create();

			List<JWK> keys = new ArrayList<>();
			keys.add(jwk.toPublicJWK());       // toPublicJWK is to remove sensitive fields like p, q, d, dp, qi, dq

			Map<String, Object> map = new HashMap<>();
			map.put("keys", keys);

			JsonElement json = JsonParser.parseString(map.toString());
			
			String jsonString = gson.toJson(json);
			
			return jsonString;
		} catch (Exception e) {
			throw new Exception("Invalid key size: " + e.getMessage());
		}
	}

	private JWK makeKey(String size, KeyUse keyUse, KeyIdGenerator kid, Algorithm keyAlg) throws Exception {
		PID pid = dao.findPidById(1);
		JWK jwk = makeRsaKey(kid, keyUse, size, keyAlg, pid.getPublicKey(), pid.getPrivateKey());
		return jwk;
	}

	private JWK makeRsaKey(KeyIdGenerator kid, KeyUse keyUse, String size, Algorithm keyAlg, RSAPublicKey pubKey, RSAPrivateKey privKey)
			throws Exception {
		if (size == null || size.length() == 0) throw new Exception("Key size (in bits) is required for key type ");

		Integer keySize = Integer.decode(size);
		if (keySize % 8 != 0) throw new Exception("Key size (in bits) must be divisible by 8, got " + keySize);

		return RSAKeyMaker.make(keySize, keyUse, keyAlg, kid, pubKey, privKey);
	}
}
