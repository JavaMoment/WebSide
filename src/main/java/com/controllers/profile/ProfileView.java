package com.controllers.profile;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Size;

import com.entities.Departamento;
import com.entities.Itr;
import com.entities.Localidad;
import com.entities.Usuario;
import com.services.DepartamentoBean;
import com.services.LocalidadBean;
import com.services.UsuarioBeanRemote;
import com.services.ItrBean;

import java.io.Serializable;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("profile")
@ViewScoped
public class ProfileView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private UsuarioBeanRemote userBeanRemote;

	private String mailInstitucional;
	private String mailPersonal;
	private String nombre1;
	private String nombre2;
	private String telefono;
	private String passwordNueva;
	private Usuario usuario;
	private String apellido1;
	private String apellido2;
	private String contrasenia;
	private String documento;
	private String genero;
	private Date fechaNacimiento;
	private String passwordActual;

	private long departmentId;
	private long locationId;
	private long itrId;
	private List<Localidad> listaLocalidad = new ArrayList<>();
	private List<Departamento> listaDepartamento = new ArrayList<>();
	private List<Itr> listaItr = new ArrayList<>();

	public Usuario getUser() {
		return usuario;
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

	public String getPasswordNueva() {
		return passwordNueva;
	}

	public void setPasswordNueva(String passwordNueva) {
		this.passwordNueva = passwordNueva;
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

	@Size(min = 8, max = 8, message = "Ingresar 8 caracteres.")
	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public long getItrId() {
		return itrId;
	}

	public void setLocationId(long localidad) {
		this.locationId = localidad;
	}

	public void setItrId(long itr) {
		this.itrId = itr;
	}

	public void setDepartmentId(long departamento) { // le cambio a long
		this.departmentId = departamento;
	}

	public long getLocationId() {
		return locationId;
	}

	public List<Localidad> getListaLocalidad() {
		return this.listaLocalidad;
	}

	public List<Departamento> getListaDepartamento() {
		return this.listaDepartamento;
	}

	public List<Itr> getListaItr() {
		return this.listaItr;
	}

	public Date getBirthdayDate() {
		return fechaNacimiento;
	}

	public void setBirthdayDate(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getPasswordActual() {
		return passwordActual;
	}

	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}

	@PostConstruct
	public void init() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			usuario = (Usuario) session.getAttribute("userLogged");
			if (usuario == null)
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/WebSide/views/static/login/login.xhtml");

			System.out.println("entra al try");
			LocalidadBean lbean = new LocalidadBean();
			ItrBean itrbean = new ItrBean();
			DepartamentoBean depabean = new DepartamentoBean();
			this.listaLocalidad = lbean.selectAll();

			this.listaItr = itrbean.selectAll();

			this.listaDepartamento = depabean.selectAll();

			System.out.println(depabean);
			System.out.println(itrbean);
			System.out.println(lbean);

			// Asignar los datos a las propiedades del bean
			if (usuario != null) {
				this.nombre1 = usuario.getNombre1();
				this.nombre2 = usuario.getNombre2();
				this.apellido1 = usuario.getApellido1();
				this.apellido2 = usuario.getApellido2();
				this.documento = usuario.getDocumento();
				this.genero = String.valueOf(usuario.getGenero()); // tener en cuenta q es char
				this.mailInstitucional = usuario.getMailInstitucional();
				this.mailPersonal = usuario.getMailPersonal();
				this.telefono = usuario.getTelefono();
				this.departmentId = usuario.getDepartamento().getIdDepartamento();
				this.itrId = usuario.getItr().getIdItr();
				this.locationId = usuario.getLocalidad().getIdLocalidad();
				this.fechaNacimiento = usuario.getFechaNacimiento();

			} else {
				System.out.println("no agarra los datos rey");
			}
		} catch (Exception e) {
			// Manejo de excepciones
			System.out.println("entra al catch");

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al cargar los datos del usuario."));
		}
	}

	public void testButtonAction() throws InterruptedException {
		// System.out.println(e.getComponent().getClientId());
		System.out.println(this.usuario.toString());
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "test", null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	// Método para actualizar la información del usuario
	public void actualizar() {
		try {
			// Establecer el mailInstitucional mientras no sea global
			// String mailInstitucional = "gon.ruiz@tutores.utec.edu.uy";

			// usuario = userBeanRemote.selectUserBy(mailInstitucional);
			// System.out.println(usuario);

			LocalidadBean lbean = new LocalidadBean();
			ItrBean itrbean = new ItrBean();
			DepartamentoBean depabean = new DepartamentoBean();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			usuario = (Usuario) session.getAttribute("userLogged");

			// Verifica si se encontró el usuario
			if (usuario != null) {
				// Actualiza los campos del usuario
				usuario.setNombre1(this.nombre1);
				usuario.setNombre2(this.nombre2);
				usuario.setApellido1(this.apellido1);
				usuario.setApellido2(this.apellido2);
				usuario.setDocumento(this.documento);
				usuario.setGenero(this.genero.charAt(0));
				usuario.setMailInstitucional(this.mailInstitucional);
				usuario.setMailPersonal(this.mailPersonal);
				usuario.setTelefono(this.telefono);
				usuario.setDepartamento(depabean.selectById(this.departmentId));
				usuario.setLocalidad(lbean.selectById(this.locationId));
				usuario.setItr(itrbean.selectById(this.itrId));
				usuario.setFechaNacimiento(fechaNacimiento);

				Boolean isValid = true;

				if (this.passwordActual != null && this.passwordActual != "") {
					if (!this.passwordActual.equals(this.passwordNueva)) {
						// Mensaje de error
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error", "Error, las contraseñas no coinciden."));
						isValid = false;
					} else {
						usuario.setContrasenia(passwordNueva);
					}
				}
				if (isValid) {
					// Intenta guardar los cambios
					int resultado = userBeanRemote.update(usuario);
					System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + resultado);
					if (resultado == 0) {
						System.out.println("se actualiza todo bien");

						// Mensaje de éxito
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Éxito", "Usuario actualizado con éxito."));
					} else {
						System.out.println("error");

						// Mensaje de error
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error", "Error al actualizar el usuario."));

					}

				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Usuario no encontrado."));
				System.out.println("no encuentra el usuario");

			}
		} catch (Exception e) {
			System.out.println("funciono todo mal");

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Error Fatal", "Excepción al actualizar el usuario: " + e.getMessage()));

		}

	}

}
