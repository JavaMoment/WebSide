package com.controllers.listings.claims;

import java.io.IOException;
import java.io.Serializable;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.api.app.schemas.PatchDTO;
import com.entities.Reclamo;
import com.entities.StatusReclamo;
import com.entities.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.services.HttpRequestDispatcher;
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
	private HttpSession session;
	protected HttpRequestDispatcher dispatcher;
	protected ObjectMapper objectMapper;
	
	@PostConstruct
	public void init() {
		dispatcher = new HttpRequestDispatcher();
		HttpResponse resp = null;
		objectMapper = new ObjectMapper();
		
		FacesContext context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);
		Usuario userLogged = (Usuario) session.getAttribute("userLogged");
		try {
			if(userLogged.getTipoUsuario().toUpperCase().equals("ESTUDIANTE")) {
				resp = dispatcher.sendGet(new ArrayList<String>(List.of("reclamos", "estudiante", userLogged.getNombreUsuario())));
				setClaims(objectMapper.readValue(resp.body().toString(), new TypeReference<List<Reclamo>>(){}));
			} else {
				resp = dispatcher.sendGet(new ArrayList<String>(List.of("reclamos")));
				setClaims(objectMapper.readValue(resp.body().toString(), new TypeReference<List<Reclamo>>(){}));
			}
		} catch(IOException | InterruptedException e) {
			// TODO: Si la api esta caida, avisar con algun dialogo.
			e.printStackTrace();
		}
		setStatuses(statusReclamoService.selectAll());
	}
	
	public void onSelectionChangeStatus(Reclamo claim) {
		long reclamoId = claim.getIdReclamo();
		HttpResponse resp = null;
		try {
			resp = dispatcher.sendPatch(
					new ArrayList<String>(List.of("reclamos", claim.getIdReclamo().toString())), 
					new PatchDTO("replace", "/statusReclamo/idStatus", claim.getStatusReclamo().getIdStatus().toString())
					);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el status del reclamo no ha podido ser modificado."));
		}
		StatusReclamo statusReclamoInDB = statusReclamoService.selectById(claim.getStatusReclamo().getIdStatus());
		Reclamo reclamoInDB = reclamoService.selectById(reclamoId);
		if(resp != null && resp.statusCode() == 200) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El status del reclamo #" + reclamoId + " ha sido correctamente actualizado a: " + statusReclamoInDB.getNombre() + "."));
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