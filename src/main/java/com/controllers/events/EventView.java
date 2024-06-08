package com.controllers.events;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import com.entities.StatusEvento;
import com.entities.Evento;
import com.entities.Itr;
import com.entities.Modalidad;
import com.entities.TiposEvento;
import com.entities.Tutor;
import com.services.StatusEventoBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ModalidadBeanRemote;
import com.services.TiposEventoBeanRemote;
import com.services.TutorBeanRemote;

@Named("event")
@ViewScoped
public class EventView implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private EventoBeanRemote eventBeanRemote;

	@EJB
	private ModalidadBeanRemote modalidadBeanRemote;

	@EJB
	private ItrBeanRemote itrBeanRemote;

	@EJB
	private TutorBeanRemote tutorBeanRemote;

	@EJB
	private StatusEventoBeanRemote estadoBeanRemote;

	@EJB
	private TiposEventoBeanRemote tiposEventoBeanRemote;

	private Long[] selectedTutores;
	private Byte activo;
	private Evento evento;
	private Long modalidadId;
	private Long itrId;
	private Long statusEventoID;
	private Long tipoEventoId;
	private TiposEvento tipoEvento;
	private String titulo;
	private List<Evento> events;
	private List<StatusEvento> statusEventos; // List of StatusEvento for dropdown
	private List<TiposEvento> tiposEventos; // List of TiposEvento for dropdown

	@PostConstruct
	public void init() {
		this.evento = new Evento();
		events = eventBeanRemote.selectAll();
		statusEventos = estadoBeanRemote.selectAll(); // Initialize statusEventos list
		tiposEventos = tiposEventoBeanRemote.selectAll(); // Initialize tiposEventos list
		
	}

	// Getters and Setters

	public Long getModalidadId() {
		return modalidadId;
	}

	public Long getItrId() {
		return itrId;
	}

	public Long getStatusEventoID() {
		return statusEventoID;
	}

	public void setModalidadId(Long v) {
		modalidadId = v;
	}

	public Long getTipoEventoId() {
		return tipoEventoId;
	}

	public void setTipoEventoId(Long v) {
		tipoEventoId = v;
	}

	public void setItrId(Long v) {
		itrId = v;
	}

	public void setStatusEventoID(Long v) {
		statusEventoID = v;
	}

	public Evento getEvento() {
		return this.evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Long[] getSelectedTutores() {
		return selectedTutores;
	}

	public void setSelectedTutores(Long[] selectedTutores) {
		this.selectedTutores = selectedTutores;
	}

	public List<Evento> getEvents() {
		return events;
	}

	public void setEvents(List<Evento> events) {
		this.events = events;
	}

	public List<StatusEvento> getStatusEventos() {
		return statusEventos;
	}

	public void setStatusEventos(List<StatusEvento> statusEventos) {
		this.statusEventos = statusEventos;
	}

	public List<TiposEvento> getListaTiposEventos() {
		return tiposEventoBeanRemote.selectAll();
	}

	public void setListaTiposEventos(List<TiposEvento> tiposEventos) {
		this.tiposEventos = tiposEventos;
	}

	public List<Modalidad> getListaModalidad() {
		return modalidadBeanRemote.selectAll();
	}

	public List<Itr> getListaItr() {
		return itrBeanRemote.selectAll();
	}

	public List<Tutor> getListaTutor() {
		return tutorBeanRemote.selectAll();
	}

	public List<StatusEvento> getListaStatusEvento() {
		return estadoBeanRemote.selectAll();
	}

	public void cancel() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		Flash flash = context.getExternalContext().getFlash();
		flash.setKeepMessages(true);

		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancelación", "Evento cancelado con éxito."));

		ExternalContext ec = context.getExternalContext();
		ec.redirect("/WebSide/views/static/dashboard/dashboard.xhtml");
	}



	public void save() {
		try {
			if (selectedTutores.length == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Selecciona al menos un tutor"));
			} else {
				if (evento.getFechaHoraInicio().after(evento.getFechaHoraFinal())) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error", "La fecha de inicio no puede ser mayor que la de finalización"));
				} else {

					this.evento.setModalidad(modalidadBeanRemote.selectById(modalidadId));
					this.evento.setItr(itrBeanRemote.selectById(itrId));
					this.evento.setstatusEvento(estadoBeanRemote.selectById(statusEventoID));
					this.evento.setTiposEvento(tiposEventoBeanRemote.selectById(tipoEventoId));

					// creo el evento
					evento = eventBeanRemote.createEvento(evento);
					System.out.println(evento);
					System.out.println(evento.getIdEvento());
					System.out.println("ID del evento creado: " + evento.getIdEvento());
					System.out.println("Detalle del evento creado: " + evento.toString()); 
																							

					if (evento == null || evento.getIdEvento() == null) {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear el evento."));
						return;
					}

					System.out.println("Evento creado con ID: " + evento.getIdEvento());

					boolean allTutorsAssigned = true;
					for (Long tutorId : selectedTutores) {
						if (!tutorBeanRemote.asignarEventoTutor(evento, tutorBeanRemote.selectById(tutorId))) {
							allTutorsAssigned = false;
							break;
						}
					}

					if (!allTutorsAssigned) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error", "No se pudieron asignar todos los tutores"));
						return;
					}

//                    for (Long tutorId : selectedTutores) {
//                        tutorBeanRemote.asignarEventoTutor(evento, tutorBeanRemote.selectById(tutorId));
//                    }
					// Mensaje de éxito
					FacesContext context = FacesContext.getCurrentInstance();
					Flash flash = context.getExternalContext().getFlash();
					flash.setKeepMessages(true);

					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Evento creado con éxito."));

					ExternalContext ec = context.getExternalContext();
					ec.redirect("/WebSide/views/static/dashboard/dashboard.xhtml");
				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Excepción: " + e.getMessage()));
		}
	}

	// logica par que funcione el boton

	public void onToggleSwitchChangeActive(Evento evento) {
		int exitCode;
		Long eventoID = evento.getIdEvento();
		if (evento.getActivo()) {
			evento.setActivo(true);
			exitCode = eventBeanRemote.activeEventBy(eventoID);
			if (exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("¡Bien!", "El evento " + eventoID + " ha sido correctamente activado."));
			}
		} else {
			evento.setActivo(false);
			exitCode = eventBeanRemote.logicalDeleteBy(eventoID);
			if (exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("¡Bien!", "El evento " + eventoID + " ha sido correctamente dado de baja."));
			}
		}
		if (exitCode != 0) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Ha ocurrido un error y el estado del evento no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:msgs", "form:dt-events");
	}

	public Byte getActivo() {
		return activo;
	}

	public void setActivo(Byte activo) {
		this.activo = activo;
	}

}
