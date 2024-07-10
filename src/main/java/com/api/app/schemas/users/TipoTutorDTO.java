package com.api.app.schemas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class TipoTutorDTO {
	
	private Long idTipoTutor;
	private String nombre;
	
	public Long getIdTipoTutor() {
		return idTipoTutor;
	}
	public void setIdTipoTutor(Long idTipoTutor) {
		this.idTipoTutor = idTipoTutor;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
