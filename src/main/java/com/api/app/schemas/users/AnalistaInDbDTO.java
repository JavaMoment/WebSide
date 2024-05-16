package com.api.app.schemas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AnalistaInDbDTO {
	
	private UserInDbDTO usuario;

	public UserInDbDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserInDbDTO usuario) {
		this.usuario = usuario;
	}

}
