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

import com.entities.Evento;
import com.entities.Itr;
import com.entities.Modalidad;
import com.entities.StatusEvento;
import com.entities.TiposEvento;
import com.entities.Tutor;
import com.services.StatusEventoBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ModalidadBeanRemote;
import com.services.TiposEventoBeanRemote;
import com.services.TutorBeanRemote;

@Named(value = "eventModification")
@ViewScoped
public class EventModificationView implements Serializable {

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
	private Evento evento;
	private Long modalidadId;
	private Long itrId;
	private Long statusEventoID;
	private Long tipoEventoId;
	private TiposEvento tipoEvento;
	private List<Evento> events;
	private List<Itr> itrs;

	private List<StatusEvento> statusEventos; // List of StatusEvento for dropdown
	private List<TiposEvento> tiposEventos; // List of TiposEvento for dropdown
	private List<Modalidad> modalidadEventos;

	@PostConstruct
	public void init() {
		evento = new Evento();
		events = eventBeanRemote.selectAll();
		statusEventos = estadoBeanRemote.selectAll();
		tiposEventos = tiposEventoBeanRemote.selectAll();
		modalidadEventos = modalidadBeanRemote.selectAll();
		itrs = itrBeanRemote.selectAll();

		System.out.println("ITRs loaded: " + itrs.size());
		// System.out.println("Modalidades loaded: " + modalidadEventos.size());
	}

	public void doUpdateEvent() {
		try {
		    System.out.println("doUpdateEvent se llama bien");

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
					evento = eventBeanRemote.createEvento(evento);
					for (Long tutorId : selectedTutores) {
						tutorBeanRemote.asignarEventoTutor(evento, tutorBeanRemote.selectById(tutorId));
					}
					// Mensaje de éxito
					FacesContext context = FacesContext.getCurrentInstance();
					Flash flash = context.getExternalContext().getFlash();
					flash.setKeepMessages(true);

					context.addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Evento actualizado con éxito."));

					ExternalContext ec = context.getExternalContext();
					ec.redirect("/WebSide/views/static/events/eventsList.xhtml");

				}
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Excepción: " + e.getMessage()));
		}
	}

	public void cancel() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		Flash flash = context.getExternalContext().getFlash();
		flash.setKeepMessages(true);

		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancelación", "Modificación cancelada con éxito."));

		ExternalContext ec = context.getExternalContext();
		ec.redirect("/WebSide/views/static/events/eventsList.xhtml");
	}

	public EventoBeanRemote getEventBeanRemote() {
		return eventBeanRemote;
	}

	public void setEventBeanRemote(EventoBeanRemote eventBeanRemote) {
		this.eventBeanRemote = eventBeanRemote;
	}

	public ModalidadBeanRemote getModalidadBeanRemote() {
		return modalidadBeanRemote;
	}

	public void setModalidadBeanRemote(ModalidadBeanRemote modalidadBeanRemote) {
		this.modalidadBeanRemote = modalidadBeanRemote;
	}

	public ItrBeanRemote getItrBeanRemote() {
		return itrBeanRemote;
	}

	public void setItrBeanRemote(ItrBeanRemote itrBeanRemote) {
		this.itrBeanRemote = itrBeanRemote;
	}

	public TutorBeanRemote getTutorBeanRemote() {
		return tutorBeanRemote;
	}

	public void setTutorBeanRemote(TutorBeanRemote tutorBeanRemote) {
		this.tutorBeanRemote = tutorBeanRemote;
	}

	public StatusEventoBeanRemote getEstadoBeanRemote() {
		return estadoBeanRemote;
	}

	public void setEstadoBeanRemote(StatusEventoBeanRemote estadoBeanRemote) {
		this.estadoBeanRemote = estadoBeanRemote;
	}

	public TiposEventoBeanRemote getTiposEventoBeanRemote() {
		return tiposEventoBeanRemote;
	}

	public void setTiposEventoBeanRemote(TiposEventoBeanRemote tiposEventoBeanRemote) {
		this.tiposEventoBeanRemote = tiposEventoBeanRemote;
	}

	public Long[] getSelectedTutores() {
		return selectedTutores;
	}

	public void setSelectedTutores(Long[] selectedTutores) {
		this.selectedTutores = selectedTutores;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Long getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(Long modalidadId) {
		this.modalidadId = modalidadId;
	}

	public Long getItrId() {
		return itrId;
	}

	public void setItrId(Long itrId) {
		this.itrId = itrId;
	}

	public Long getStatusEventoID() {
		return statusEventoID;
	}

	public void setStatusEventoID(Long statusEventoID) {
		this.statusEventoID = statusEventoID;
	}

	public Long getTipoEventoId() {
		return tipoEventoId;
	}

	public void setTipoEventoId(Long tipoEventoId) {
		this.tipoEventoId = tipoEventoId;
	}

	public TiposEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TiposEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
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

	public List<TiposEvento> getTiposEventos() {
		return tiposEventos;
	}

	public void setTiposEventos(List<TiposEvento> tiposEventos) {
		this.tiposEventos = tiposEventos;
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

	public List<Modalidad> getListaModalidadEventos() {
		return modalidadBeanRemote.selectAll();
	}

	public void setModalidadEventos(List<Modalidad> modalidadEventos) {
		this.modalidadEventos = modalidadEventos;
	}

}
