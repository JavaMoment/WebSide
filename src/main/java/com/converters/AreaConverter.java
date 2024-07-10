package com.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.entities.Area;
import com.resources.utils.Utils;
import com.services.AreaBeanRemote;

@FacesConverter("AreaConverter")
public class AreaConverter implements Converter {

	private AreaBeanRemote statusAreaService = Utils.getBean(AreaBeanRemote.class);

	@Override
	public Area getAsObject(FacesContext context, UIComponent component, String value) {
		return statusAreaService.selectAllBy(List.of(value)).get(0);
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
		String r = "";
		if (o instanceof Area) {
            Area a = (Area) o;
            r = String.valueOf(a.getNombre());
        } else if (o instanceof String) {
           r = (String) o;
        }
		return r;
	}
}
