package com.api.app.schemas;

public class PatchDTO {

	private String op;
	private String path;
	private String value;
	
	public PatchDTO(String op, String path, String value) {
		this.op = op;
		if(!path.startsWith("/")) {
			path = "/" + path;
		}
		this.path = path;
		this.value = value;
	}
	
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
