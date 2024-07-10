package com.api.app.schemas.itr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ItrInDbDTO extends ItrDTO{

	private Long idItr;
	private Boolean activo;
	private String nombre;
	
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public Long getIdItr() {
		return idItr;
	}
	public void setIdItr(Long idItr) {
		this.idItr = idItr;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
