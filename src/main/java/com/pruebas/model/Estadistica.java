package com.pruebas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Estadistica {

	@Id
	private String pais;
	@Column(columnDefinition = "double")
	private Double distancia;
	@Column
	private Integer invocaciones;

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Double getDistancia() {
		return distancia;
	}

	public void setDistancia(Double distancia) {
		this.distancia = distancia;
	}

	public Integer getInvocaciones() {
		return invocaciones;
	}

	public void setInvocaciones(Integer invocaciones) {
		this.invocaciones = invocaciones;
	}

}
