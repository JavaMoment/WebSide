package com.controllers.events;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;

import com.entities.StatusEvento;
import com.services.StatusEventoBeanRemote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Named("estadoModification")
@ViewScoped
public class EstadoModificationView implements Serializable {

	@EJB
	private StatusEventoBeanRemote estadoBean;
	
	
	private StatusEvento selectedEstado;
	
	
	@PostConstruct
	public void init() {
	}

	public void doUpdateEstado() {
		int exitCode = estadoBean.update(selectedEstado);
		selectedEstado = estadoBean.selectById(selectedEstado.getIdStatus());
		if(exitCode == 0 && selectedEstado != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El Estado " + selectedEstado.getNombre() + " ha sido correctamente modificado."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el Estado no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-estados");
		PrimeFaces.current().executeScript("PF('manageEstadoDialog').hide()");
	}

	public StatusEventoBeanRemote getEstadoBean() {
		return estadoBean;
	}

	public void setEstadoBean(StatusEventoBeanRemote estadoBean) {
		this.estadoBean = estadoBean;
	}

	public StatusEvento getSelectedEstado() {
		return selectedEstado;
	}

	public void setSelectedEstado(StatusEvento selectedEstado) {
		this.selectedEstado = selectedEstado;
	}
	
	
	
	
}