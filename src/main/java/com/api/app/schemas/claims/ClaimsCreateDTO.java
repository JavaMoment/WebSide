package com.api.app.schemas.claims;

import java.util.Date;

import com.api.app.schemas.events.EventInDbDTO;
import com.api.app.schemas.users.AnalistaInDbDTO;
import com.api.app.schemas.users.EstudianteInDbDTO;

public class ClaimsCreateDTO {

	private AnalistaInDbDTO analista;
	private EstudianteInDbDTO estudiante;
	private EventInDbDTO evento;
	private StatusReclamoDTO statusReclamo;
	private Integer semestre;
	private Integer creditos;
	private Date auditDate;
	private Date modifDate;
	private String modifUser;
	private String titulo;
	private String descripcion;
	private String detalle;
	

	public AnalistaInDbDTO getAnalista() {
		return analista;
	}
	public void setAnalista(AnalistaInDbDTO analista) {
		this.analista = analista;
	}
	public EstudianteInDbDTO getEstudiante() {
		return estudiante;
	}
	public void setEstudiante(EstudianteInDbDTO estudiante) {
		this.estudiante = estudiante;
	}
	public EventInDbDTO getEvento() {
		return evento;
	}
	public void setEvento(EventInDbDTO evento) {
		this.evento = evento;
	}
	public StatusReclamoDTO getStatusReclamo() {
		return statusReclamo;
	}
	public void setStatusReclamo(StatusReclamoDTO statusReclamo) {
		this.statusReclamo = statusReclamo;
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
	public Date getModifDate() {
		return modifDate;
	}
	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}
	public String getModifUser() {
		return modifUser;
	}
	public void setModifUser(String modifUser) {
		this.modifUser = modifUser;
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
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
}
