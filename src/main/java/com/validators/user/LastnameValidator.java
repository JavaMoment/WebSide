package com.validators.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("lastnameValidator")
public class LastnameValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if(value.toString().trim().isEmpty() || value == null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Atenti!", "Por favor ingrese su apellido."));
        }
		if(value.toString().trim().length() < 2) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Atenti!", "Su apellido no puede contener menos de 2 caracteres."));
		}
	}

}
