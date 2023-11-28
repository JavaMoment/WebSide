package com.validators.user;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


@FacesValidator("emailValidator")
public class EmailValidator implements Validator {
    private Pattern pattern;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if(value.toString().trim().isEmpty() || value == null) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Por favor ingrese una dirección de correo electrónico institucional.","Por favor ingrese una dirección de correo electrónico institucional."));
        }
        if(value.toString().contains("@") && !pattern.matcher(value.toString()).matches()) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingrese una dirección de correo electrónico institucional válida.", "Por favor ingrese una dirección de correo electrónico institucional válida."));
        }
        if(value.toString().contains("@") && !value.toString().endsWith(".utec.edu.uy")) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Por favor ingrese una dirección de correo electrónico institucional válida.", "Por favor ingrese una dirección de correo electrónico institucional válida."));
        }
    }

}