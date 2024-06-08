package com.controllers.events;

	import javax.annotation.PostConstruct;
	import javax.ejb.EJB;
	import javax.faces.application.FacesMessage;
	import javax.faces.context.FacesContext;
	import javax.faces.view.ViewScoped;
	import javax.inject.Named;

	import org.primefaces.PrimeFaces;
	import org.primefaces.model.DualListModel;

	import com.entities.Modalidad;
	import com.services.ModalidadBeanRemote;

	import java.io.Serializable;


	@Named("modalidadModification")
	@ViewScoped
	public class ModalidadModificationView implements Serializable {

		@EJB
		private ModalidadBeanRemote modalidadBean;
		
		private Modalidad selectedModalidad;
		
		@PostConstruct
		public void init() {
		}

		public void doUpdateModalidad() {
			int exitCode = modalidadBean.update(selectedModalidad);
			selectedModalidad = modalidadBean.selectById(selectedModalidad.getIdModalidad());
			if(exitCode == 0 && selectedModalidad != null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "La modalidad " + selectedModalidad.getNombre() + " ha sido correctamente modificada."));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y la modalidad no ha podido ser modificada."));
			}
			PrimeFaces.current().ajax().update("form:messages", "form:dt-modalidades");
			PrimeFaces.current().executeScript("PF('manageModalidadDialog').hide()");
		}
		
		
		public Modalidad getSelectedModalidad() {
			return selectedModalidad;
		}

		public void setSelectedModalidad(Modalidad selectedModalidad) {
			this.selectedModalidad = selectedModalidad;
		}

}
