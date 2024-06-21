package com.converters;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.entities.DtItr;
import com.entities.Estudiante;
import com.resources.utils.Utils;
import com.services.EstudianteBeanRemote;

@FacesConverter("EstudianteConverter")
public class EstudianteConverter implements Converter {
	
	private EstudianteBeanRemote estudianteBean = (EstudianteBeanRemote) Utils.getBean(EstudianteBeanRemote.class); 
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return estudianteBean.selectUserBy(arg2);
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		String r = "";
		if (arg2 instanceof Estudiante) {
			Estudiante e = (Estudiante) arg2;
			r = String.valueOf(e.getUsuario().getNombreUsuario());
		} else if(arg2 instanceof String) {
			r = (String) arg2;
		}
		return r;
	}

}
