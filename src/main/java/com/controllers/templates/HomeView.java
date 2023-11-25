package com.controllers.home;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@Named("home")
@ViewScoped
public class HomeView implements Serializable {

	
	public void logout() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		session.removeAttribute("userLogged");
		FacesContext.getCurrentInstance().getExternalContext()
		.redirect("/WebSide");
	}
}
