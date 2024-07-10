package com.controllers.listings.claims;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.primefaces.PrimeFaces;

import com.entities.Evento;
import com.entities.Reclamo;
import com.entities.StatusReclamo;
import com.entities.Usuario;
import com.resources.utils.Constants;
import com.services.EstudianteBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ReclamoBeanRemote;
import com.services.StatusReclamoBeanRemote;

@Named("addClaimView")
@ViewScoped
public class AddClaimView implements Serializable {

	@EJB
	private ReclamoBeanRemote reclamoService;
	@EJB
	private EventoBeanRemote eventoService;
	@EJB
	private EstudianteBeanRemote estudService;
	@EJB
	private StatusReclamoBeanRemote statusService;
	
	private List<Evento> eventos;
	private Reclamo newClaim;
	private Constants constants = new Constants();
	private HttpSession session;
	private Usuario userLogged;
	private StatusReclamo status;
	
	@PostConstruct
	public void init() {
		eventos = eventoService.selectAll();
		newClaim = new Reclamo();
		FacesContext context = FacesContext.getCurrentInstance();
		session = (HttpSession) context.getExternalContext().getSession(true);
		userLogged = (Usuario) session.getAttribute("userLogged");
		status = statusService.selectById(1L); // cambiar por default
	}
	
	public void doCreateClaim() {
		int exitCode;
		if(newClaim.getEvento().getTiposEvento() == null || !constants.getCREDITS_BEARING_EVENTS().contains(newClaim.getEvento().getTiposEvento().getNombre().toUpperCase())) {
			newClaim.setSemestre(null);
			newClaim.setCreditos(null);
		}
		newClaim.setEstudiante(estudService.selectUserBy(userLogged.getNombreUsuario()));
		newClaim.setStatusReclamo(status);
		exitCode = reclamoService.create(newClaim);
		if(exitCode == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El reclamo ha sido correctamente creado."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el reclamo no ha podido ser creado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-claims");
		PrimeFaces.current().executeScript("PF('addClaimDialog').hide()");
	}
	
	public Reclamo getNewClaim() {
		return newClaim;
	}

	public void setNewClaim(Reclamo claim) {
		this.newClaim = claim;
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
