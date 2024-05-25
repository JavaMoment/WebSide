package com.api.app.schemas.events;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EventInDbDTO {

	private Long idEvento;
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


	public Long getIdEvento() {
		return idEvento;
	}

	public void setIdEvento(Long idEvento) {
		this.idEvento = idEvento;
	}

}
