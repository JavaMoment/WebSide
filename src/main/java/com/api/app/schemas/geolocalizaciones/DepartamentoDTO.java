package com.api.app.schemas.geolocalizaciones;

import com.api.app.schemas.itr.ItrInDbDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class DepartamentoDTO {

	private Long idDepartamento;
	private ItrInDbDTO itr;
	private String nombre;
	
	public Long getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public ItrInDbDTO getItr() {
		return itr;
	}
	public void setItr(ItrInDbDTO itr) {
		this.itr = itr;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
