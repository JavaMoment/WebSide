package com.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.entities.Localidad;
import com.resources.utils.Utils;
import com.services.LocalidadBeanRemote;

@FacesConverter("LocalidadConverter")
public class LocalidadConverter implements Converter {

	private LocalidadBeanRemote localidadBean = (LocalidadBeanRemote) Utils.getBean(LocalidadBeanRemote.class); 
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return localidadBean.selectBy(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String r = "";
		if (arg2 instanceof Localidad) {
			Localidad l = (Localidad) arg2;
			r = String.valueOf(l.getNombre());
		} else if(arg2 instanceof String) {
			r = (String) arg2;
		}
		return r;
	}

}
