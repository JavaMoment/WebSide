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
	private String[] status;
	
	@PostConstruct
	public void init() {
		setClaims(reclamoService.selectAll());
		setStatus(statusReclamoService.selectAll().stream().map(s -> s.getNombre()).distinct().toArray(String[]::new));
	}
	
	public void onToggleSwitchChangeActive(Reclamo claim) {
		int exitCode;
		long reclamoId = claim.getIdReclamo();
		// TODO: Implementar atributo de activo al reclamo
//		if(claim.isActive()) {
//			claim.setActivo((byte) 0);
//			exitCode = reclamoService.logicalDeleteBy(reclamoId); TODO: Implementar baja logica por ID
//			if(exitCode == 0) {
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El ITR " + claimName + " ha sido correctamente dado de baja."));
//			}
//		} else {
//			claim.setActivo((byte) 1);
//			exitCode = reclamoService.activeItrBy(claimName);
//			if(exitCode == 0) {
//				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El ITR " + claimName + " ha sido correctamente activado."));
//			}
//		}
//		if(exitCode != 0) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el estado del ITR no ha podido ser modificado."));
//		}
//		PrimeFaces.current().ajax().update("form:messages", "form:dt-claims");
	}

	public List<Reclamo> getClaims() {
		return claims;
	}

	public void setClaims(List<Reclamo> claims) {
		this.claims = claims;
	}

	public String[] getStatus() {
		return status;
	}

	public void setStatus(String[] status) {
		this.status = status;
	}

	public Reclamo getSelectedClaim() {
		return selectedClaim;
	}

	public void setSelectedClaim(Reclamo selectedClaim) {
		this.selectedClaim = selectedClaim;
	}

}