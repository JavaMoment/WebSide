package com.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.entities.StatusEvento;
import com.resources.utils.Utils;
import com.services.StatusEventoBeanRemote;
import java.util.logging.Logger;

@FacesConverter("EstadoConverter")
public class StatusEventoConverter implements Converter {

    private static final Logger LOGGER = Logger.getLogger(StatusEventoConverter.class.getName());
    private StatusEventoBeanRemote estadoBean = Utils.getBean(StatusEventoBeanRemote.class);

    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        List<StatusEvento> statusList = estadoBean.selectAllBy(List.of(arg2));
        if (statusList.isEmpty()) {
            LOGGER.warning("No StatusEvento found for value: " + arg2);
            return null; // or handle it accordingly, maybe throw a custom exception
        }
        return statusList.get(0);
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        String r = "";
        if (arg2 instanceof StatusEvento) {
            StatusEvento d = (StatusEvento) arg2;
            r = String.valueOf(d.getNombre());
        } else if(arg2 instanceof String) {
            r = (String) arg2;
        }
        return r;
    }
}
