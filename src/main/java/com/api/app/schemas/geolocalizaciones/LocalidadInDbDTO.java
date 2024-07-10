package com.api.app.schemas.geolocalizaciones;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class LocalidadInDbDTO {
	
	private Long idLocalidad;
	private String nombre;
	
	public Long getIdLocalidad() {
		return idLocalidad;
	}
	public void setIdLocalidad(Long idLocalidad) {
		this.idLocalidad = idLocalidad;
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
