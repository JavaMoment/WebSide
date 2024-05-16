package com.api.app.schemas.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EventInDbDTO {
	
	private String titulo;
	private TipoEventoDTO tiposEvento;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public TipoEventoDTO getTiposEvento() {
		return tiposEvento;
	}

	public void setTiposEvento(TipoEventoDTO tiposEvento) {
		this.tiposEvento = tiposEvento;
	}

}
