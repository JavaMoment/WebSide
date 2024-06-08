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

import com.entities.Modalidad;
import com.services.ModalidadBeanRemote;


@Named(value = "dtModalidadesView")
@ViewScoped
public class ModalidadesListView implements Serializable {

	@EJB
	private ModalidadBeanRemote modalidadBean;
	private Modalidad selectedModalidad;
	private List<Modalidad> modalidades;
	private List<Modalidad> selectedModalidades;
	private String[] modalidadStatus = {"Activo", "Inactivo"};
	
	@PostConstruct
	public void init() {
		modalidades = modalidadBean.selectAll();
	}
	
	public void onToggleSwitchChangeActive(Modalidad modalidad) {
		int exitCode;
		String modalidadName = modalidad.getNombre();
		if(modalidad.getActivo()) {
			modalidad.setActivo(true);
			exitCode = modalidadBean.activeModalidadBy(modalidadName);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "La modalidad " + modalidadName + " ha sido correctamente activada."));
			}
		} else {
			modalidad.setActivo(false);
			exitCode = modalidadBean.logicalDeleteBy(modalidadName);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "La modalidad " + modalidadName + " ha sido correctamente dada de baja."));
			}
		}
		if(exitCode != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el estado de la modalidad no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-modalidades");
	}
	
	public void reloadModalidades() {
		modalidades = modalidadBean.selectAll(); 
	}
	
	public List<Modalidad> getModalidades() {
		return modalidades;
	}
	
	public Modalidad getselectedModalidad() {
        return selectedModalidad;
    }

    public void setselectedModalidad(Modalidad selectedModalidad) {
        this.selectedModalidad = selectedModalidad;
    }

    public List<Modalidad> getSelectedModalidades() {
        return selectedModalidades;
    }

    public void setselectedModalidades(List<Modalidad> selectedModalidades) {
        this.selectedModalidades = selectedModalidades;
    }

	public String[] getModalidadStatus() {
		return modalidadStatus;
	}

	public void setModalidadStatus(String[] modalidadStatus) {
		this.modalidadStatus = modalidadStatus;
	}
}