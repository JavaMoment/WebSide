package com.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import com.entities.Evento;
import com.resources.utils.Utils;
import com.services.EventoBeanRemote;

@FacesConverter("EventoConverter")
public class EventoConverter implements Converter {

	private EventoBeanRemote eventoService = Utils.getBean(EventoBeanRemote.class);

	@Override
	public Evento getAsObject(FacesContext context, UIComponent component, String value) {
		return eventoService.selectBy(value);
	}
	
	@Override
	public String getAsString(FacesContext facesContext, UIComponent uiComponent, Object o) {
		String r = "";
		if (o instanceof Evento) {
            Evento e = (Evento) o;
            r = String.valueOf(e.getTitulo());
        } else if (o instanceof String) {
           r = (String) o;
        }
		return r;
	}
}
