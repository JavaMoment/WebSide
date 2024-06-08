package com.controllers.events;

import com.resources.utils.Utils;
import com.services.EventoBeanRemote;
import com.entities.StatusEvento;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import com.entities.Evento;
import com.entities.Itr;
import com.entities.Modalidad;
import com.entities.TiposEvento;
import com.entities.Tutor;
import com.services.StatusEventoBeanRemote;
import com.services.ItrBeanRemote;
import com.services.ModalidadBeanRemote;
import com.services.TiposEventoBeanRemote;
import com.services.TutorBeanRemote;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		EventoBeanRemote ebr = (EventoBeanRemote)Utils.getBean(EventoBeanRemote.class);
		ModalidadBeanRemote mbr = (ModalidadBeanRemote)Utils.getBean(ModalidadBeanRemote.class);
		ItrBeanRemote ibr = (ItrBeanRemote)Utils.getBean(ItrBeanRemote.class);
		TutorBeanRemote tbr = (TutorBeanRemote)Utils.getBean(TutorBeanRemote.class);
		StatusEventoBeanRemote esbr = (StatusEventoBeanRemote)Utils.getBean(StatusEventoBeanRemote.class);
		TiposEventoBeanRemote tebr = (TiposEventoBeanRemote)Utils.getBean(TiposEventoBeanRemote.class);

		Evento evento = new Evento ("PFT", tebr.selectById(1L), new Date(System.currentTimeMillis()),  new Date(System.currentTimeMillis() + 1L ),
				mbr.selectById(2L), ibr.selectById(2L), "Salon 101", esbr.selectById(1L), (byte)1);
		Evento eventoC = ebr.createEvento(evento);
		System.out.println(eventoC.getIdEvento());
		System.out.println(eventoC.getTitulo());
	}

}