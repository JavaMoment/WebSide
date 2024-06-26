package com.validators.user;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.resources.utils.Utils;
import com.services.UsuarioBeanRemote;


@FacesValidator("emailValidator")
public class EmailValidator implements Validator {
    private Pattern pattern;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private UsuarioBeanRemote userBean;
    
    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
        userBean = Utils.getBean(UsuarioBeanRemote.class);
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) {
        if(value.toString().trim().isEmpty() || value == null) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Cuidadiiiitooo","Por favor ingrese una dirección de correo electrónico institucional."));
        }
        if(value.toString().contains("@") && !pattern.matcher(value.toString()).matches()) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cuidadiiiitooo", "Por favor ingrese una dirección de correo electrónico institucional válida."));
        }
        if(value.toString().contains("@") && !value.toString().endsWith(".utec.edu.uy")) {
        	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cuidadiiiitooo", "Por favor ingrese una dirección de correo electrónico institucional válida."));
        }
        try {
        	if(!component.getId().equals("email") && userBean.isUserRegistered(value.toString().split("@")[0])) {
        		throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Atención!", "Usted ya se encuentra registrado."));
        	}
        	
        }catch (Exception e) {

		}
    }

}