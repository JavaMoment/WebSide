package com.api.app.schemas.claims;

import java.util.Date;

import com.api.app.schemas.events.EventInDbDTO;
import com.api.app.schemas.users.AnalistaInDbDTO;
import com.api.app.schemas.users.EstudianteInDbDTO;

public class ClaimDTO {
	public Long idReclamo;
	public AnalistaInDbDTO analista;
	public EstudianteInDbDTO estudiante;
	public EventInDbDTO evento;
	public StatusReclamoDTO statusReclamo;
	public Integer semestre;
	public Integer creditos;
	public Date auditDate;
	public Date modifDate;
	public String modifUser;
	public String titulo;
	public String descripcion;
	public String detalle;

}
