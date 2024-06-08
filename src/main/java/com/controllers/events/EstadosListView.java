package com.controllers.events;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.entities.StatusEvento;
import com.services.StatusEventoBeanRemote;

/**
 * Session Bean implementation class usuarioController
 */
@Named(value = "dtEstadosView")
@ViewScoped
public class EstadosListView implements Serializable {

	@EJB
	private StatusEventoBeanRemote estadoBean;
	private StatusEvento selectedEstado;
	private List<StatusEvento> estados;
	private List<StatusEvento> selectedEstados;
	private String[] estadoStatus = {"Activo", "Inactivo"};
	
	@PostConstruct
	public void init() {
		estados = estadoBean.selectAll();
	}
	
	public void onToggleSwitchChangeActive(StatusEvento statusEvento) {
		int exitCode;
		String estadoName = statusEvento.getNombre();
		if(statusEvento.getActivo()) {
			statusEvento.setActivo(true);
			exitCode = estadoBean.activeEstadoBy(estadoName);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El Estado " + estadoName + " ha sido correctamente activado."));
			}
		} else {
			statusEvento.setActivo(false);
			exitCode = estadoBean.logicalDeleteBy(estadoName);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El Estado " + estadoName + " ha sido correctamente dado de baja."));
			}
		}
		if(exitCode != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el estado del Estado no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-estados");
	}
	
	public void reloadEstados() {
		estados = estadoBean.selectAll(); 
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

	public List<StatusEvento> getEstados() {
		return estados;
	}

	public void setEstados(List<StatusEvento> estados) {
		this.estados = estados;
	}

	public List<StatusEvento> getSelectedEstados() {
		return selectedEstados;
	}

	public void setSelectedEstados(List<StatusEvento> selectedEstados) {
		this.selectedEstados = selectedEstados;
	}

	public String[] getEstadoStatus() {
		return estadoStatus;
	}

	public void setEstadoStatus(String[] estadoStatus) {
		this.estadoStatus = estadoStatus;
	}
	

}