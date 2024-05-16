package com.api.app.schemas.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AreaDTO {

	private Long idArea;
	private String descripcion;
	private String nombre;
	
	public Long getIdArea() {
		return idArea;
	}
	public void setIdArea(Long idArea) {
		this.idArea = idArea;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
