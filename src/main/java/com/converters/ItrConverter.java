package com.converters;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.entities.Itr;
import com.resources.utils.Utils;
import com.services.ItrBeanRemote;

@FacesConverter("ItrConverter")
public class ItrConverter implements Converter {
	
	private ItrBeanRemote itrBean = (ItrBeanRemote) Utils.getBean(ItrBeanRemote.class); 
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return itrBean.selectAllBy(List.of(arg2)).get(0);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String r = "";
		if (arg2 instanceof Itr) {
			Itr i = (Itr) arg2;
			r = String.valueOf(i.getNombre());
		} else if(arg2 instanceof String) {
			r = (String) arg2;
		}
		return r;
	}

}
