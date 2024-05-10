package com.api.app.schemas.itr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ItrInDbDTO extends ItrDTO{

	private Long idItr;
	private Byte activo;
	private String nombre;
	
	public Byte getActivo() {
		return activo;
	}
	public void setActivo(Byte activo) {
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
