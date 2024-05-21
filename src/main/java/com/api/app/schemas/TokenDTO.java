package com.api.app.schemas;

public class TokenDTO {
	
	public String message;
    public String token;

    public TokenDTO(String message, String token) {
        this.message = message; // mensaje usuario
        this.token = token; // el token jwt
    }
	
}
