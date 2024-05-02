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

import com.entities.Reclamo;
import com.entities.StatusReclamo;
import com.services.ReclamoBeanRemote;
import com.services.StatusReclamoBeanRemote;

@Named("dtClaimsView")
@ViewScoped
public class ClaimsListView implements Serializable {

	@EJB
	private ReclamoBeanRemote reclamoService;
	@EJB
	private StatusReclamoBeanRemote statusReclamoService;
	
	private List<Reclamo> claims;
	private Reclamo selectedClaim; 
	private StatusReclamo actualStatus;
	private List<StatusReclamo> statuses;
	
	@PostConstruct
	public void init() {
		setClaims(reclamoService.selectAll());
		setStatuses(statusReclamoService.selectAll());
	}
	
	public void onSelectionChangeStatus(Reclamo claim) {
		int exitCode;
		long reclamoId = claim.getIdReclamo();
		StatusReclamo statusReclamoInDB = statusReclamoService.selectById(claim.getStatusReclamo().getIdStatus());
		Reclamo reclamoInDB = reclamoService.selectById(reclamoId);
		reclamoInDB.setStatusReclamo(statusReclamoInDB);
		exitCode = reclamoService.update(reclamoInDB);
		if(exitCode == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El status del reclamo #" + reclamoId + " ha sido correctamente actualizado a: " + statusReclamoInDB.getNombre() + "."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el status del reclamo no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-claims");
	}
	
	public void doUpdateDetail() {
		int exitCode;
		Reclamo reclamoInDB = reclamoService.selectById(selectedClaim.getIdReclamo());
		reclamoInDB.setDetalle(selectedClaim.getDetalle());
		exitCode = reclamoService.update(reclamoInDB);
		if(exitCode == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El detalle del reclamo #" + reclamoInDB.getIdReclamo() + " ha sido correctamente actualizado."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el detalle del reclamo seleccionado no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-claims");
		PrimeFaces.current().executeScript("PF('manageDetailClaimDialog').hide()");
	}

	public List<Reclamo> getClaims() {
		return claims;
	}

	public void setClaims(List<Reclamo> claims) {
		this.claims = claims;
	}

	public Reclamo getSelectedClaim() {
		return selectedClaim;
	}

	public void setSelectedClaim(Reclamo actualClaim) {
		this.selectedClaim = actualClaim;
	}

	public StatusReclamo getActualStatus() {
		return actualStatus;
	}

	public void setActualStatus(StatusReclamo actualStatus) {
		this.actualStatus = actualStatus;
	}

	public List<StatusReclamo> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<StatusReclamo> statuses) {
		this.statuses = statuses;
	}

}