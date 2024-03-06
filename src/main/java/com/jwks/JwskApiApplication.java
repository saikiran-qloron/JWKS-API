package com.jwks;

import com.jwks.models.PID;
import com.jwks.repositories.PIDdao;
import com.jwks.service.JWTservice;
import com.jwks.service.JWTverificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;

@SpringBootApplication
public class JwskApiApplication implements CommandLineRunner {

	private PIDdao dao;

	public JwskApiApplication(PIDdao dao) {
		this.dao = dao;
	}

	public static void main(String[] args) {
		SpringApplication.run(JwskApiApplication.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
			generator.initialize(2048);
			KeyPair kp = generator.generateKeyPair();

			RSAPublicKey publicKey = (RSAPublicKey) kp.getPublic();
			RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) kp.getPrivate();

			PID pid = new PID(1, "35343252343234546", publicKey, privateKey);
			dao.save(pid);
		}
		catch (NoSuchAlgorithmException exception){
			exception.printStackTrace();
		}
	}
}
