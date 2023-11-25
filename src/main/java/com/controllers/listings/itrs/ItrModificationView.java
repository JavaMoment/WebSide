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
import java.util.Set;

@Named("itrModification")
@ViewScoped
public class ItrModificationView implements Serializable {

	@EJB
	private ItrBeanRemote itrBean;
	@EJB
	private DepartamentoBeanRemote depaBean;
	
	private Itr selectedItr;
	private List<Departamento> selectedDepas;
	private List<Departamento> itrDepas;
	private List<Departamento> departamentosSource;
	private DualListModel<Departamento> departamentos;
	
	@PostConstruct
	public void init() {
	}

	public void doUpdateItr() {
		Set<Departamento> relatedDepartamentos = new HashSet<>(departamentos.getTarget());
		selectedItr.setDepartamentos(relatedDepartamentos);
		int exitCode = itrBean.update(selectedItr);
		if(exitCode == 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El ITR " + selectedItr.getNombre() + " ha sido correctamente modificado."));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el ITR no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-itrs");
		PrimeFaces.current().executeScript("PF('manageItrDialog').hide()");
	}
	
	public void loadDepartamentos() {
		departamentosSource = depaBean.selectAll();
		List<Departamento> departamentosTarget = new ArrayList<>(selectedItr.getDepartamentos());
		departamentos = new DualListModel<>(departamentosSource, departamentosTarget);
	}
	
	public Itr getSelectedItr() {
		return selectedItr;
	}

	public void setSelectedItr(Itr selectedItr) {
		this.selectedItr = selectedItr;
	}

	public List<Departamento> getSelectedDepas() {
		return selectedDepas;
	}

	public void setSelectedDepas(List<Departamento> selectedDepas) {
		this.selectedDepas = selectedDepas;
	}

	public List<Departamento> getDepartamentosSource() {
		return departamentosSource;
	}

	public void setDepartamentosSource(List<Departamento> departamentos) {
		this.departamentosSource = departamentos;
	}

	public void setDepartamentos(DualListModel<Departamento> departamentos) {
		this.departamentos = departamentos;
	}
	
	public DualListModel<Departamento> getDepartamentos() {
		return departamentos;
	}
}
