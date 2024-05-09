package com.resources.utils;

import java.util.Collections;
import java.util.List;

public class Constants {
	private final static List<String> CREDITS_BEARING_EVENTS = Collections.unmodifiableList(List.of("VME","APE", "OPTATIVA"));

	public List<String> getCREDITS_BEARING_EVENTS() {
		return CREDITS_BEARING_EVENTS;
	}

}
