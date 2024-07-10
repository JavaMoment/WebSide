package com.converters;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.entities.StatusReclamo;
import com.resources.utils.Utils;
import com.services.StatusReclamoBeanRemote;

@FacesConverter("StatusConverter")
public class StatusConverter implements Converter {

	private StatusReclamoBeanRemote statusReclamoService = Utils.getBean(StatusReclamoBeanRemote.class);

	@Override
	public StatusReclamo getAsObject(FacesContext context, UIComponent component, String value) {
		return statusReclamoService.selectAllBy(List.of(value)).get(0);
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
		String r = "";
		if (o instanceof StatusReclamo) {
            StatusReclamo sr = (StatusReclamo) o;
            r = String.valueOf(sr.getNombre());
        } else if (o instanceof String) {
           r = (String) o;
        }
		return r;
	}
}
