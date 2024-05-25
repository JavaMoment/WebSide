package com.api.app.schemas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EstudianteInDbDTO {
	

	private Long idEstudiante;
	private UserInDbDTO usuario;
	private String generacion;
	
	public UserInDbDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UserInDbDTO usuario) {
		this.usuario = usuario;
	}
	public String getGeneracion() {
		return generacion;
	}
	public void setGeneracion(String generacion) {
		this.generacion = generacion;
	}

	public Long getIdEstudiante() {
		return idEstudiante;
	}
	public void setIdEstudiante(Long idEstudiante) {
		this.idEstudiante = idEstudiante;
	}

}
