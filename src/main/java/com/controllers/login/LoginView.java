package com.controllers.login;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.entities.Usuario;
import com.services.UsuarioBeanRemote;

import java.io.IOException;
import java.io.Serializable;

@Named("login")
@SessionScoped
public class LoginView implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@EJB
	private UsuarioBeanRemote userBeanRemote;

	private String emailUtec;
	private String password;
	private Usuario user;
	
	@PostConstruct
	public void init() {
		
	}
	
	public void doLogin() {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Oh no! Oh no no no", "El usuario o contraseña no es correcto");;
		user = userBeanRemote.selectUserBy(emailUtec);
		if(user != null && user.isValidUser(password)) {
			try {
				msg = new FacesMessage("¡Bienvenido!");
				FacesContext.getCurrentInstance().getExternalContext().redirect("/WebSide/views/static/dashboard/dashboard.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}
	
	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public String getEmailUtec() {
		return emailUtec;
	}
	
	public void setEmailUtec(String email) {
		this.emailUtec = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

}
