package com.controllers.listings.itrs;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DualListModel;

import com.entities.Departamento;
import com.entities.Itr;
import com.services.DepartamentoBeanRemote;
import com.services.ItrBeanRemote;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Named("itrModification")
@ViewScoped
public class ItrModificationView implements Serializable {

	@EJB
	private ItrBeanRemote itrBean;
	@EJB
	private DepartamentoBeanRemote depaBean;
	
	private Itr selectedItr;
	private List<String> departamentosSource;
	private List<String> departamentosTarget;
	private DualListModel<String> departamentos;
	
	@PostConstruct
	public void init() {
	}

	public void doUpdateItr() {
		selectedItr.setDepartamentos(departamentos.getTarget() != null && departamentos.getTarget().size() > 1 ? new HashSet<Departamento>(depaBean.selectAllBy(departamentos.getTarget())) : null);
		int exitCode = itrBean.update(selectedItr);
		selectedItr = itrBean.selectById(selectedItr.getIdItr());
		if(exitCode == 0 && selectedItr != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El ITR " + selectedItr.getNombre() + " ha sido correctamente modificado."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el ITR no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-itrs");
		PrimeFaces.current().executeScript("PF('manageItrDialog').hide()");
	}
	
	public void loadDepartamentos() {
		departamentosSource = depaBean.getDepartamentosWithoutItr().stream().map(d -> d.getNombre()).toList();
		departamentosTarget = new ArrayList<>(selectedItr.getDepartamentos().stream().map(d -> d.getNombre()).toList());
		departamentos = new DualListModel<>(departamentosSource, departamentosTarget);
	}
	
	public Itr getSelectedItr() {
		return selectedItr;
	}

	public void setSelectedItr(Itr selectedItr) {
		this.selectedItr = selectedItr;
	}

	public List<String> getDepartamentosSource() {
		return departamentosSource;
	}

	public void setDepartamentosSource(List<String> departamentos) {
		this.departamentosSource = departamentos;
	}

	public void setDepartamentos(DualListModel<String> departamentos) {
		this.departamentos = departamentos;
	}
	
	public DualListModel<String> getDepartamentos() {
		return departamentos;
	}
}
