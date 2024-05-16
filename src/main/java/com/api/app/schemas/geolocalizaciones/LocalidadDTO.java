package com.api.app.schemas.geolocalizaciones;

public class LocalidadDTO {

	private Long idLocalidad;
	private String nombre;
	private DepartamentoDTO departamento;
	
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
	public DepartamentoDTO getDepartamento() {
		return departamento;
	}
	public void setDepartamento(DepartamentoDTO departamento) {
		this.departamento = departamento;
	}
}
