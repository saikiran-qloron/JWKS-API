/**
 * 
 */
package com.jwks.controller;

import com.jwks.service.JWKSService;
import com.jwks.service.JWTservice;
import com.jwks.service.JWTverificationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/jwks")
public class JWKSController {
	private JWKSService jwksService;
	private JWTverificationService verificationService;
	private JWTservice jwTservice;

	public JWKSController(JWKSService jwksService, JWTverificationService verificationService, JWTservice jwTservice) {
		this.jwksService = jwksService;
		this.verificationService = verificationService;
		this.jwTservice = jwTservice;
	}

	@GetMapping (value = "/json", produces = "application/json")
	public Object getJWKS() throws Exception {
		return jwksService.getJWKSByKeyType("2048", "RS256");
	}

	@GetMapping(value = "/verify", produces = "application/json")
	public boolean verifyToken() throws Exception{
		String token = jwTservice.createJWTtoken();

		return verificationService.verifyJWTtoken(token);
	}
}
