package com.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.entities.DtItr;
import com.resources.utils.Utils;
import com.services.DtItrBeanRemote;

@FacesConverter("dtItrConverter")
public class DtItrConverter implements Converter {
	
	private DtItrBeanRemote itrBean = (DtItrBeanRemote) Utils.getBean(DtItrBeanRemote.class); 
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return itrBean.getDtItrBy(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String r = "";
		if (arg2 instanceof DtItr) {
			DtItr i = (DtItr) arg2;
			r = String.valueOf(i.getPkItr());
		} else if(arg2 instanceof String) {
			r = (String) arg2;
		}
		return r;
	}

}
