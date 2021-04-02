package com.pruebas.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pruebas.model.Estadistica;

public interface EstadisticaDAO extends JpaRepository<Estadistica, String> {
	
	@Query(value="SELECT * FROM ESTADISTICA ORDER BY distancia ASC limit 1", nativeQuery=true)
	public Estadistica getMenorDistancia();
	
	@Query(value="SELECT * FROM ESTADISTICA ORDER BY distancia DESC limit 1", nativeQuery=true)
	public Estadistica getMayorDistancia();
	
	@Query(value="SELECT * FROM ESTADISTICA",nativeQuery=true)
	public List<Estadistica> getEstadisticas();

}
