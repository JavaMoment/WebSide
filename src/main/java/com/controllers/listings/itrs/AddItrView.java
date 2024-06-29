package com.controllers.listings.itrs;

import java.io.Serializable;
import java.util.HashSet;
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
	private List<String> relatedDepartamentos;
	private List<String> departamentos;
	
	@PostConstruct
	public void init() {
		departamentos = depaBean.getDepartamentosWithoutItr().stream().map(d -> d.getNombre()).toList();
	}

	public void onItemUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Departamento deseleccionado: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update("dialogs:messages");
    }
	
	public void doAddItr() {
		// Crear el ITR primero
		Itr newItr = new Itr(name);
		int exitCode = itrBean.create(newItr);
		// Luego adicionar la relacion con los departamentos
		newItr = itrBean.selectItrBy(newItr.getNombre());
		newItr.setDepartamentos(relatedDepartamentos != null && relatedDepartamentos.size() > 0 ? new HashSet<>(depaBean.selectAllBy(relatedDepartamentos)) : null);
		exitCode = itrBean.update(newItr);
		// Esto es a causa de la falta de manejo por transacciones je
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

	public List<String> getRelatedDepartamentos() {
		return relatedDepartamentos;
	}

	public void setRelatedDepartamentos(List<String> relatedDepartamentos) {
		this.relatedDepartamentos = relatedDepartamentos;
	}

	public List<String> getDepartamentos() {
		return departamentos;
	}

	public void setDepartamentos(List<String> departamentos) {
		this.departamentos = departamentos;
	}
	
}
