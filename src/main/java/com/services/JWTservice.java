package com.services;

import com.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class JWTservice {
	public static String SECRET = "k9onTf7tQg6IBVlpr96STrfehMyC21AB";

	public static String generateToken(Usuario user) {

		Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(SECRET), SignatureAlgorithm.HS256.getJcaName());

		
		String jwtToken = Jwts.builder().setSubject(user.getDocumento()).claim("name", user.getNombreUsuario())
				 .signWith(SignatureAlgorithm.HS256, SECRET).compact();

		return jwtToken;

	}

	public static boolean validateToken(String token,Usuario user) {

		try {
			if(token != null && user != null) {				
				Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
				String document = claims.getSubject();
				return document != user.getDocumento();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
