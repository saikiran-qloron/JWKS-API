package com.jwks.utils;

import java.util.function.BiFunction;
import com.nimbusds.jose.jwk.KeyUse;

public class KeyIdGenerator {
	private final String name;
	private final BiFunction<KeyUse, byte[], String> fn;

	public KeyIdGenerator(String name, BiFunction<KeyUse, byte[], String> fn) {
		this.name = name;
		this.fn = fn;
	}

	public String generate(KeyUse keyUse, byte[] publicKey) {
		return fn.apply(keyUse, publicKey);
	}

	public String getName() {
		return this.name;
	}

	public static KeyIdGenerator get(String key) {
		KeyIdGenerator keyIdGenerator = new KeyIdGenerator("redisKey", (use, pubKey) -> {
			return key;
		});

		return keyIdGenerator;
	}

	public static KeyIdGenerator specified(String kid) {
		return new KeyIdGenerator(null, (u, p) -> kid);
	}
}

