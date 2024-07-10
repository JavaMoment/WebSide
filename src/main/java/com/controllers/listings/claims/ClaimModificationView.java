package com.controllers.listings.claims;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.entities.Evento;
import com.entities.Reclamo;
import com.resources.utils.Constants;
import com.services.EventoBeanRemote;
import com.services.ReclamoBeanRemote;
import com.services.StatusReclamoBeanRemote;

@Named("claimModificationView")
@ViewScoped
public class ClaimModificationView implements Serializable {

	@EJB
	private ReclamoBeanRemote reclamoService;
	@EJB
	private StatusReclamoBeanRemote statusReclamoService;
	@EJB
	private EventoBeanRemote eventoService;
	
	private Reclamo selectedClaim; 
	private List<Evento> eventos;
	private Constants constants = new Constants();
	
	@PostConstruct
	public void init() {
		eventos = eventoService.selectAll();
	}
	
	public void doUpdateClaim() {
		int exitCode;
		if(selectedClaim.getEvento().getTiposEvento() == null || !constants.getCREDITS_BEARING_EVENTS().contains(selectedClaim.getEvento().getTiposEvento().getNombre().toUpperCase())) {
			selectedClaim.setSemestre(null);
			selectedClaim.setCreditos(null);
			exitCode = reclamoService.update(selectedClaim);
		} else {
			exitCode = reclamoService.update(selectedClaim);
		}
		if(exitCode == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El reclamo #" + selectedClaim.getIdReclamo() + " ha sido correctamente actualizado."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el reclamo no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-claims");
		PrimeFaces.current().executeScript("PF('manageClaimDialog').hide()");
	}
	
	public Reclamo getSelectedClaim() {
		return selectedClaim;
	}

	public void setSelectedClaim(Reclamo actualClaim) {
		this.selectedClaim = actualClaim;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public Constants getConstants() {
		return constants;
	}

}