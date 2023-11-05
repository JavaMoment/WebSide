package com.controllers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class HolaMundo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String mensaje = "La maldita PatoGang broder!";

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
		
}