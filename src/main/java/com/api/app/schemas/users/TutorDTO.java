package com.api.app.schemas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class TutorDTO {
	
	private UserCreateDTO usuario;
	private TipoTutorDTO rol;
	private AreaDTO area;

	public UserCreateDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UserCreateDTO usuario) {
		this.usuario = usuario;
	}

	public TipoTutorDTO getRol() {
		return rol;
	}

	public void setRol(TipoTutorDTO tipo) {
		this.rol = tipo;
	}

	public AreaDTO getArea() {
		return area;
	}

	public void setArea(AreaDTO area) {
		this.area = area;
	}

}
