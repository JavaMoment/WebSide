package com.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.entities.Departamento;
import com.resources.utils.Utils;
import com.services.DepartamentoBeanRemote;

@FacesConverter("DepartamentoConverter")
public class DepartamentoConverter implements Converter {
	
	private DepartamentoBeanRemote departamentoBean = (DepartamentoBeanRemote) Utils.getBean(DepartamentoBeanRemote.class); 

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return departamentoBean.selectAllBy(List.of(arg2)).get(0);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String r = "";
		if (arg2 instanceof Departamento) {
			Departamento d = (Departamento) arg2;
			r = String.valueOf(d.getNombre());
		} else if(arg2 instanceof String) {
			r = (String) arg2;
		}
		return r;
	}

}
