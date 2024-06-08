package com.api.app.schemas;

public class LoginResponseDTO {
	
	public String message;
    public String token;
    public String username;
    public String tipoUsuario;

    public LoginResponseDTO(String message, String token, String username, String tipoUsuario) {
        this.message = message; // mensaje usuario
        this.token = token; // el token jwt
        this.username = username;
        this.tipoUsuario = tipoUsuario.toUpperCase();
    }
	
}
