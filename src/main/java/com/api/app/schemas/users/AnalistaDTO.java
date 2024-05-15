package com.api.app.schemas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AnalistaDTO {

	private UserCreateDTO usuario;

	public UserCreateDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserCreateDTO usuario) {
		this.usuario = usuario;
	}
}
