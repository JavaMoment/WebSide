package com.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.entities.TiposTutor;
import com.resources.utils.Utils;
import com.services.TiposTutorBeanRemote;

@FacesConverter("RolConverter")
public class RolConverter implements Converter {

	private TiposTutorBeanRemote rolBean = (TiposTutorBeanRemote) Utils.getBean(TiposTutorBeanRemote.class); 
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return rolBean.selectBy(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String r = "";
		if (arg2 instanceof TiposTutor) {
			TiposTutor tt = (TiposTutor) arg2;
			r = String.valueOf(tt.getNombre());
		} else if(arg2 instanceof String) {
			r = (String) arg2;
		}
		return r;
	}

}
