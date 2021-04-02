package com.pruebas.exception;

public class ErrorMessage {

	private String excepcion;
	private String mensaje;
	private String ruta;

	public ErrorMessage(Exception excepcion, String ruta) {
		super();
		this.excepcion = excepcion.getClass().getSimpleName();
		this.mensaje = excepcion.getMessage();
		this.ruta = ruta;
	}

	public String getExcepcion() {
		return excepcion;
	}

	public void setExcepcion(String excepcion) {
		this.excepcion = excepcion;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	@Override
	public String toString() {
		return "ErrorMessage [excepcion=" + excepcion + ", mensaje=" + mensaje + ", ruta=" + ruta + "]";
	}

}
