package com.api.app.schemas.users;

import java.util.Date;

import com.api.app.schemas.LocalidadDTO;
import com.api.app.schemas.itr.ItrInDbDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class UserInDbDTO {
	
	private String nombreUsuario;
//	@JsonIgnore
//	private String contrasenia;
	private Byte activo;
	private String apellido1;
	private String apellido2;
	private String documento;
	private Date fechaNacimiento;
	private Character genero;
	private ItrInDbDTO itr;
	private LocalidadDTO localidad;
	private String mailInstitucional;
	private String mailPersonal;
	private String nombre1;
	private String nombre2;
	private String telefono;
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public Byte getActivo() {
		return activo;
	}
	public void setActivo(Byte activo) {
		this.activo = activo;
	}
	public ItrInDbDTO getItr() {
		return itr;
	}
	public void setItr(ItrInDbDTO itr) {
		this.itr = itr;
	}
	public LocalidadDTO getLocalidad() {
		return localidad;
	}
	public void setLocalidad(LocalidadDTO localidad) {
		this.localidad = localidad;
	}
	public String getApellido1() {
		return apellido1;
	}
	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}
	public String getApellido2() {
		return apellido2;
	}
	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Character getGenero() {
		return genero;
	}
	public void setGenero(Character genero) {
		this.genero = genero;
	}
	public String getMailInstitucional() {
		return mailInstitucional;
	}
	public void setMailInstitucional(String mailInstitucional) {
		this.mailInstitucional = mailInstitucional;
	}
	public String getMailPersonal() {
		return mailPersonal;
	}
	public void setMailPersonal(String mailPersonal) {
		this.mailPersonal = mailPersonal;
	}
	public String getNombre1() {
		return nombre1;
	}
	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}
	public String getNombre2() {
		return nombre2;
	}
	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
