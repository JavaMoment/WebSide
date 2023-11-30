package com.validators.user;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("phoneValidator")
public class PhoneValidator implements Validator {

	private Pattern pattern;
    private static final String PHONE_PATTERN = "\\d{8}$";
    
    public PhoneValidator() {
    	pattern = Pattern.compile(PHONE_PATTERN);
    }
    
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if(value.toString().isEmpty() || !pattern.matcher(value.toString()).matches()) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cuidadiiiitooo", "El número de telefono ingresado no contiene sólo números y/o tiene menos o mas de 8 digitos"));
        }
	}

}
