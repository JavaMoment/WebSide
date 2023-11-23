package com.validators.user;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {
	
	private Pattern pattern;
    private static final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+-=]).{8,}$";

    public PasswordValidator() {
    	pattern = Pattern.compile(PASSWORD_PATTERN);
    }
    
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if(value.toString().trim().isEmpty() || value == null) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Login error","Por favor ingrese una contraseña."));
        }
		if(!pattern.matcher(value.toString()).matches()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"¡Cuidadiiitoo!",
					"Por favor ingrese una contraseña válida que contenga al menos una letra mayúscula, una letra minúscula, un número y un carácter especial, y tenga una longitud de al menos 8 caracteres."
					));
		}
	}

}
