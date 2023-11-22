package com.controllers.signup;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.entities.Departamento;
import com.entities.Itr;
import com.entities.Localidad;
import com.entities.Usuario;
import com.services.DepartamentoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.LocalidadBeanRemote;
import com.services.UsuarioBeanRemote;

@Named("signup")
@ViewScoped
public class SignupView implements Serializable {

	@EJB
	private UsuarioBeanRemote userBeanRemote;
	@EJB
	private ItrBeanRemote itrBeanRemote;
	@EJB
	private DepartamentoBeanRemote depaBeanRemote;
	@EJB
	private LocalidadBeanRemote cityBeanRemote;
	private String nombreUsuario;
	private byte activo;
	private String apellido1;
	private String apellido2;
	private String contrasenia;
	private String documento;
	private char genero;
	private Departamento departamento;
	private Date birthdate;
	private Itr itr;
	private Localidad localidad;
	private String mailInstitucional;
	private String mailPersonal;
	private String nombre1;
	private String nombre2;
	private String telefono;
	private Usuario newUser;
	private List<Itr> itrs;
	private List<Departamento> depas;
	private List<Localidad> cities;
	private Map<String, List<Localidad>> depaCity = new HashMap<>();
	
	@PostConstruct
	public void init() {
		itrs = itrBeanRemote.selectAll();
		depas = depaBeanRemote.selectAll();
		
		for(Departamento d : depas) {
			depaCity.put(d.toString(), cityBeanRemote.selectAllByObject(departamento));
		}
	}
	
	public void onDepaChange() {
		if(departamento != null && !"".equals(departamento.toString())) {
			cities = depaCity.get(departamento.toString());
		} else {
			cities = null;
		}
//		FacesMessage msg = new FacesMessage("hola" + departamento.toString());
//		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public byte getActivo() {
		return activo;
	}

	public void setActivo(byte activo) {
		this.activo = activo;
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

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public char getGenero() {
		return genero;
	}

	public void setGenero(char genero) {
		this.genero = genero;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Itr getItr() {
		return itr;
	}

	public void setItr(Itr itr) {
		this.itr = itr;
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
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

	public List<Itr> getItrs() {
		return itrs;
	}

	public void setItrs(List<Itr> itrs) {
		this.itrs = itrs;
	}

	public List<Departamento> getDepas() {
		return depas;
	}

	public void setDepas(List<Departamento> depas) {
		this.depas = depas;
	}

	public List<Localidad> getCities() {
		return cities;
	}

	public void setCities(List<Localidad> cities) {
		this.cities = cities;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
