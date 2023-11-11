package com.controllers.login;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.entities.Usuario;
import com.services.UsuarioBeanRemote;

import java.io.IOException;
import java.io.Serializable;

@Named("login")
@ViewScoped
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
	
	public void doLogin() {
		user = userBeanRemote.selectUserBy(emailUtec);
		if(user.isValidUser(password)) {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/WebSide/views/static/templates/home.xhtml");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
