package com.controllers.listings.itrs;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;

import com.entities.Departamento;
import com.entities.Itr;
import com.services.DepartamentoBeanRemote;
import com.services.ItrBeanRemote;

@Named("addItr")
@ViewScoped
public class AddItrView implements Serializable {

	@EJB
	private DepartamentoBeanRemote depaBean;
	@EJB
	private ItrBeanRemote itrBean;
	
	private String name;
	private List<Departamento> relatedDepartamentos;
	private List<Departamento> departamentos;
	
	@PostConstruct
	public void init() {
		departamentos = depaBean.selectAll();
	}

	public void onItemUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Departamento deseleccionado: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update("dialogs:messages");
    }
	
	public void doAddItr() {
		Itr newItr = new Itr(name, relatedDepartamentos);
		int exitCode = itrBean.create(newItr);
		if(exitCode == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El ITR " + newItr.getNombre() + " ha sido correctamente añadido."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el ITR no ha podido ser añadido."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-itrs");
		PrimeFaces.current().executeScript("PF('addItrDialog').hide()");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Departamento> getRelatedDepartamentos() {
		return relatedDepartamentos;
	}

	public void setRelatedDepartamentos(List<Departamento> relatedDepartamentos) {
		this.relatedDepartamentos = relatedDepartamentos;
	}

	public List<Departamento> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
	
}
