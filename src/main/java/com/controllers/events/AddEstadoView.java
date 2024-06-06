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

import com.entities.StatusEvento;
import com.services.StatusEventoBeanRemote;

@Named("addEstado")
@ViewScoped
public class AddEstadoView implements Serializable {

	
	@EJB
	private StatusEventoBeanRemote estadoBean;
	
	private String name;

	
	@PostConstruct
	public void init() {
		
	}



	public void doAddEstado() {
	    FacesContext context = FacesContext.getCurrentInstance();

	    // Intentar crear el nuevo Estado
	    StatusEvento newEstado = new StatusEvento(name);
	    try {
	        int exitCode = estadoBean.create(newEstado);
	        if (exitCode != 0) {
	            throw new Exception("Fallo al crear el estado.");
	        }

	        // Intentar seleccionar el Estado recién creado
	        newEstado = estadoBean.selectEstadoBy(newEstado.getNombre());
	        if (newEstado == null) {
	            throw new Exception("Estado creado no encontrado en la base de datos.");
	        }

	        // Intentar actualizar el Estado
	        exitCode = estadoBean.update(newEstado);
	        if (exitCode != 0) {
	            throw new Exception("El Estado no ha podido ser actualizado.");
	        }

	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Bien!", "El Estado " + newEstado.getNombre() + " ha sido correctamente añadido."));
	        PrimeFaces.current().ajax().update("form:messages", "form:dt-estados");
	        PrimeFaces.current().executeScript("PF('addEstadoDialog').hide()");
	    } catch (Exception e) {
	        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
	    }
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public StatusEventoBeanRemote getEstadoBean() {
		return estadoBean;
	}


	public void setEstadoBean(StatusEventoBeanRemote estadoBean) {
		this.estadoBean = estadoBean;
	}

	
	
}