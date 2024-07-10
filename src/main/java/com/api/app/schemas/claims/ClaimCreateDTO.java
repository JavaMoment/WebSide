package com.api.app.schemas.claims;

import java.util.Date;

import com.api.app.schemas.events.EventInDbDTO;
import com.api.app.schemas.users.AnalistaInDbDTO;
import com.api.app.schemas.users.EstudianteInDbDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class ClaimCreateDTO {

	private String nombreUsuario;
	private EventInDbDTO evento;
	private Integer semestre;
	private Integer creditos;
	private Date auditDate;
	private String titulo;
	private String descripcion;

	public ClaimCreateDTO() {}
	
	public ClaimCreateDTO(String nombreUsuario, EventInDbDTO evento, Integer semestre, Integer creditos, Date auditDate,
			String titulo, String descripcion) {
		super();
		this.nombreUsuario = nombreUsuario;
		this.evento = evento;
		this.semestre = semestre;
		this.creditos = creditos;
		this.auditDate = auditDate;
		this.titulo = titulo;
		this.descripcion = descripcion;
	}
	
	public EventInDbDTO getEvento() {
		return evento;
	}
	public void setEvento(EventInDbDTO evento) {
		this.evento = evento;
	}
	
	public Integer getSemestre() {
		return semestre;
	}
	public void setSemestre(Integer semestre) {
		this.semestre = semestre;
	}
	public Integer getCreditos() {
		return creditos;
	}
	public void setCreditos(Integer creditos) {
		this.creditos = creditos;
	}
	public Date getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
}
