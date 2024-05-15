package com.api.app.schemas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EstudianteDTO {
	
	UserCreateDTO usuario;
	String generacion;
	
	public String getGeneracion() {
		return generacion;
	}

	public void setGeneracion(String generacion) {
		this.generacion = generacion;
	}

	public UserCreateDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserCreateDTO usuario) {
		this.usuario = usuario;
	}
}
