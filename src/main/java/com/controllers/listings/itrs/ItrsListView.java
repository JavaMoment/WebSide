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
	private ItrBeanRemote itrBean;
	private Itr selectedItr;
	private List<Itr> itrs;
	private List<Itr> selectedItrs;
	private String[] itrStatus = {"Activo", "Inactivo"};
	
	@PostConstruct
	public void init() {
		itrs = itrBean.selectAll();
	}
	
	public void onToggleSwitchChangeActive(Itr itr) {
		int exitCode;
		String itrName = itr.getNombre();
		if(itr.getActivo() == (byte) 1) {
			itr.setActivo((byte) 0);
			exitCode = itrBean.logicalDeleteBy(itrName);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El ITR " + itrName + " ha sido correctamente dado de baja."));
			}
		} else {
			itr.setActivo((byte) 1);
			exitCode = itrBean.activeItrBy(itrName);
			if(exitCode == 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("¡Bien!", "El ITR " + itrName + " ha sido correctamente activado."));
			}
		}
		if(exitCode != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ha ocurrido un error y el estado del ITR no ha podido ser modificado."));
		}
		PrimeFaces.current().ajax().update("form:messages", "form:dt-itrs");
	}
	
	public void reloadItrs() {
		itrs = itrBean.selectAll(); 
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

	public String[] getItrStatus() {
		return itrStatus;
	}

	public void setItrStatus(String[] itrStatus) {
		this.itrStatus = itrStatus;
	}
}