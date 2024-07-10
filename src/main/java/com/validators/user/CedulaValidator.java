package com.validators.user;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("ciValidator")
public class CedulaValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String ci = value.toString();
		String digitsOnly = ci.replaceAll("\\D", "");

	    // Paso 2: Chequear largo de la cedula
	    if (digitsOnly.length() > 8) {
	    	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ojo con la cédula ;)", "La cedula ingresada no es válida y/o contiene más de 8 digitos de largo."));
	    }
	    if(digitsOnly.length() < 8) {
	    	String preAppendZeros = "0".repeat(8 - digitsOnly.length());
	    	digitsOnly = preAppendZeros + digitsOnly; 
	    }

	    // Paso 3: Separar digito verificador de los demas digitos
	    String digits = digitsOnly.substring(0, digitsOnly.length()-1);
	    String checkerDigit = digitsOnly.substring(digitsOnly.length()-1, digitsOnly.length());

	    // Paso 4: Convertir los digitos a vectores y crear operador verificador
	    String[] digitsArr = digits.split("");
	    int[] verifier = {2, 9, 8, 7, 6, 3, 4};

	    // Paso 5: El modulo 10 de la multiplicacion vectorial entre digitos y vector verificador debe ser igual al digito verificador
	    int mod = 0;
	    for(int i = 0; i<digitsArr.length; i++) {
	    	mod += (Integer.parseInt(digitsArr[i]) * verifier[i]) % 10;
	    }
	    mod = mod % 10;
	    mod = (mod - 10) * -1;
	    mod %= 10;
	    
	    // Paso 6: ¿Es el modulo de la operacion igual al digito verificador?
	    if(mod != Integer.parseInt(checkerDigit)) {
	    	throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ojo con la cédula ;)", "La cedula ingresada no es válida y/o contiene más de 8 digitos de largo."));
	    }
	}

}
