package com.pruebas.model;

import java.util.ArrayList;
import java.util.List;

public class LugarOrigen {

	private String ip;
	private String fechaActual;
	private String pais;
	private String isoCode;
	private List idiomas;
	private String Moneda;
	private List horas;
	private Double distanciaEstimada;

	public LugarOrigen() {
		idiomas = new ArrayList<String>();
		horas = new ArrayList<String>();
	}

	public LugarOrigen(String ip) {
		this.setIp(ip);
		idiomas = new ArrayList<String>();
		horas = new ArrayList<String>();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(String fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getIsoCode() {
		return isoCode;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public List<Object> getIdiomas() {
		return idiomas;
	}

	public void setIdiomas(List<Object> idiomas) {
		this.idiomas = idiomas;
	}

	public String getMoneda() {
		return Moneda;
	}

	public void setMoneda(String moneda) {
		Moneda = moneda;
	}

	public List<Object> getHoras() {
		return horas;
	}

	public void setHoras(List<Object> horas) {
		this.horas = horas;
	}

	public Double getDistanciaEstimada() {
		return distanciaEstimada;
	}

	public void setDistanciaEstimada(Double distanciaEstimada) {
		this.distanciaEstimada = distanciaEstimada;
	}
	
	public void addIdioma(String idioma) {
		this.idiomas.add(idioma);
	}

	public void addHora(String hora) {
		this.horas.add(hora);		
	}

}
