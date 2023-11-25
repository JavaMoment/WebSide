package com.controllers.signup;

import java.io.Serializable;
import java.util.Date;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import com.services.UsuarioBeanRemote;

@Named("signup")
@ViewScoped
public class SignupView implements Serializable {

	@EJB
	private UsuarioBeanRemote userBeanRemote;
	private Date birthdate;
	
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
