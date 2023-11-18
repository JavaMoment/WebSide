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

import com.entities.Itr;
import com.services.ItrBeanRemote;

/**
 * Session Bean implementation class usuarioController
 */
@Named(value = "dtItrsView")
@ViewScoped
public class ItrsListView implements Serializable {

	@EJB
	private ItrBeanRemote itrBeanRemote;
	private Itr selectedItr;
	private List<Itr> itrs;
	private List<Itr> selectedItrs;
	
	@PostConstruct
	public void init() {
		itrs = itrBeanRemote.selectAll();
	}
	
	public List<Itr> getItrs() {
		return itrs;
	}
	
	public Itr getselectedItr() {
        return selectedItr;
    }

    public void setselectedItr(Itr selectedItr) {
        this.selectedItr = selectedItr;
    }

    public List<Itr> getSelectedItrs() {
        return selectedItrs;
    }

    public void setselectedItrs(List<Itr> selectedItrs) {
        this.selectedItrs = selectedItrs;
    }

    public void deleteProduct() {
        this.itrs.remove(this.selectedItr);
        this.selectedItrs.remove(this.selectedItr);
        this.selectedItr = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Product Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-itrs");
    }

    public String getDeleteButtonMessage() {
        if (this.selectedItrs != null && !this.selectedItrs.isEmpty()) {
            int size = this.selectedItrs.size();
            return size > 1 ? size + " ITRs seleccionados" : "1 ITR seleccionado";
        }

        return "Eliminar";
    }

    public void deleteSelectedUsers() {
        this.itrs.removeAll(this.selectedItrs);
        this.selectedItrs = null;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("users Removed"));
        PrimeFaces.current().ajax().update("form:messages", "form:dt-itrs");
        PrimeFaces.current().executeScript("PF('dtItrs').clearFilters()");
    }
}