package com.controllers.events;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
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

@Named("eventView")
@ViewScoped
public class EventsListView implements Serializable{
	
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



	private long idEvento;
	private Date fechaHoraFinal;
	private Date fechaHoraInicio;
	private String titulo;
	private StatusEvento statusEvento;
	private Long[] selectedTutores;
	private Byte activo;
	private Evento evento;
	private long modalidadId;
	private long itrId;
	private long estadoId;
	private TiposEvento tipoEvento;
	private List<Evento> events;
	
	@PostConstruct
	public void init() {
		this.evento = new Evento();
		events = eventBeanRemote.selectAll();
	}
	
	public void onToggleSwitchChangeActive(Evento event) {
		int exitCode;
		String eventName = evento.getTitulo();
		Long eventId = event.getIdEvento();
		if(event.getActivo() == true) {
			event.setActivo(false);
			exitCode = eventBeanRemote.logicalDeleteBy(eventId);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El Evento: " + eventName + " ha sido correctamente dado de baja."));
			}
		} else {
			event.setActivo(true);
			exitCode = eventBeanRemote.activeEventBy(eventId);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El Evento: " + eventName + " ha sido correctamente activado."));
			}
		}
		if(exitCode != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el estado del ITR no ha podido ser modificado."));
		}

	}
	
	public Evento getEvento() {
		return evento;
	}

	public EventoBeanRemote getEventBeanRemote() {
		return eventBeanRemote;
	}

	public void setEventBeanRemote(EventoBeanRemote eventBeanRemote) {
		this.eventBeanRemote = eventBeanRemote;
	}

	public long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(long idEvento) {
		this.idEvento = idEvento;
	}

	public Date getFechaHoraFinal() {
		return fechaHoraFinal;
	}

	public void setFechaHoraFinal(Date fechaHoraFinal) {
		this.fechaHoraFinal = fechaHoraFinal;
	}

	public Date getFechaHoraInicio() {
		return fechaHoraInicio;
	}

	public void setFechaHoraInicio(Date fechaHoraInicio) {
		this.fechaHoraInicio = fechaHoraInicio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public StatusEvento getEstado() {
		return statusEvento;
	}

	public void setEstado(StatusEvento statusEvento) {
		this.statusEvento = statusEvento;
	}

	public Byte getActivo() {
		return activo;
	}

	public void setActivo(Byte activo) {
		this.activo = activo;
	}

	public long getModalidadId() {
		return modalidadId;
	}

	public void setModalidadId(long modalidadId) {
		this.modalidadId = modalidadId;
	}

	public long getItrId() {
		return itrId;
	}

	public void setItrId(long itrId) {
		this.itrId = itrId;
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

	public List<StatusEvento> getListaEstado() {
		return estadoBeanRemote.selectAll();
	}
	
	public List<TiposEvento> getListaTiposEvento() {
		return tiposEventoBeanRemote.selectAll();
	}

	public TiposEvento getTipoEvento() {
		return tipoEvento;
	}


	public void setTipoEvento(TiposEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public long getEstadoId() {
		return estadoId;
	}

	public void setEstadoId(long estadoId) {
		this.estadoId = estadoId;
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

	
}
