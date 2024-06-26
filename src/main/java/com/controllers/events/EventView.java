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
import javax.servlet.RequestDispatcher;

import com.entities.Estado;
import com.entities.Evento;
import com.entities.Itr;
import com.entities.Modalidad;
import com.entities.TiposEvento;
import com.entities.Tutor;
import com.entities.TutorEvento;
import com.services.EstadoBeanRemote;
import com.services.EventoBeanRemote;
import com.services.ItrBean;
import com.services.ItrBeanRemote;
import com.services.LocalidadBean;
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
	private EstadoBeanRemote estadoBeanRemote;
	
	@EJB
	private TiposEventoBeanRemote tiposEventoBeanRemote;

	private long idEvento;
	private Date fechaHoraFinal;
	private Date fechaHoraInicio;
	private String titulo;
	private Estado estado;
	private Long[] selectedTutores;
	private int activo;
	private Evento evento;
	private long modalidadId;
	private long itrId;
	private long estadoId;
	private long tipoEventoID;
	private TiposEvento tipoEvento;
	private List<Evento> events;
	
	@PostConstruct
	public void init() {
		this.evento = new Evento();
		events = eventBeanRemote.selectAll();
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
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

	public List<Estado> getListaEstado() {
		return estadoBeanRemote.selectAll();
	}

	public List<TiposEvento> getListaTiposEvento() {
		return tiposEventoBeanRemote.selectAll();
	}
	
	public TiposEvento getTipoEvento() {
		return tipoEvento;
	}
	
	public long getTipoEventoID() {
		return tipoEventoID;
	}
	public void setTipoEventoID(long tipoEventoID) {
		this.tipoEventoID = tipoEventoID;
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
	
	public void cancel() throws IOException {
		// Mensaje de cancelacion
		FacesContext conext = FacesContext.getCurrentInstance();
		Flash flash = conext .getExternalContext().getFlash();
		flash.setKeepMessages(true); 
		
		conext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancelación", "Evento cancelado con éxito."));
		
		
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect("/WebSide/views/static/dashboard/dashboard.xhtml");
	}

	public List<Evento> getEvents() {
		return events;
	}

	public void setEvents(List<Evento> events) {
		this.events = events;
	}

	public void save() {
	try {
		System.out.print(evento.getTiposEvento());
		if(selectedTutores.length  == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error", "Selecciona al menos un tutor"));
		}else {
			
			
			if(evento.getFechaHoraInicio().after( evento.getFechaHoraFinal())) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error", "La fecha de inicio no puede ser mayor que la de finalizacion"));
			}else {
			
				
				this.evento.setModalidad(modalidadBeanRemote.selectById(modalidadId));
				this.evento.setItr(itrBeanRemote.selectById(itrId));
				this.evento.setstatusEvento(estadoBeanRemote.selectById(estadoId)); 
				evento = eventBeanRemote.createEvento(evento); 
				for (Long tutorId : selectedTutores) { 
					tutorBeanRemote.asignarEventoTutor(evento, tutorBeanRemote.selectById(tutorId));
				}
				// Mensaje de éxito
				FacesContext conext = FacesContext.getCurrentInstance();
				Flash flash = conext .getExternalContext().getFlash();
				flash.setKeepMessages(true); 
				
				conext.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Evento creado con éxito."));
				
				
				ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
				ec.redirect("/WebSide/views/static/dashboard/dashboard.xhtml");
			}
			
		}
		

	}catch (Exception e) {
		// Mensaje de error
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"Error", "Falta completar datos."));
	}
	}

}
