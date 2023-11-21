package com.validators.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("birthdateValidator")
public class BirthdateValidator implements Validator {
	
	private final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	private final String NEW_FORMAT = "dd/MM/yyyy"; 

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if(value == null || value.toString().trim().isBlank()) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "¡Hey!", "El campo de fecha nacimiento no puede ser vacío."));
		}
		LocalDate date = ZonedDateTime
				.parse( 
					    value.toString(),
					    DateTimeFormatter
					    .ofPattern( "EEE MMM dd HH:mm:ss zzz uuuu" )
					    .withLocale( Locale.US )
					)
					.toLocalDate();
		LocalDate now = LocalDate.now();
		int comparison = now.compareTo(date);
		if(comparison <= 0) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Hey!", "Por favor, ingrese una fecha de nacimiento anterior a la fecha actual."));
		}
		if(Period.between(date, now).getYears() < 16) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Hey!", "Usted debe ser mayor de 16 años para poder registrarse en nuestro sistema."));
		}
	}

}
