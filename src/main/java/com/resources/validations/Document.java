package com.resources.validations;

import javax.faces.application.FacesMessage; 
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.resources.utils.ValidarCedula;
 
@FacesValidator("com.resources.validations.Document")
public class Document implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String model = (String) value;
		
		ValidarCedula valid = new ValidarCedula(); 
		
		if(!valid.validateCi(model)) {
			FacesMessage msg = new FacesMessage("Cedula mala :(");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		
	}

}
