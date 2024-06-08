package com.controllers.events;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;

import com.services.ModalidadBeanRemote;
import com.entities.Modalidad;




@Named("addModalidad")
@ViewScoped
public class AddModalidadView implements Serializable {

	
	@EJB
	private ModalidadBeanRemote modalidadBean;
	
	private String name;

	
	@PostConstruct
	public void init() {
	}

	
	public void doAddModalidad() {
		Modalidad newModalidad = new Modalidad(name);
		int exitCode = modalidadBean.create(newModalidad);
		newModalidad = modalidadBean.selectModalidadBy(newModalidad.getNombre());
		exitCode = modalidadBean.update(newModalidad);
		// Esto es a causa de la falta de manejo por transacciones je
		if(exitCode == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "La modalidad " + newModalidad.getNombre() + " ha sido correctamente añadida."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y la modalidad no ha podido ser añadida."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-modalidades");
		PrimeFaces.current().executeScript("PF('addModalidadDialog').hide()");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
