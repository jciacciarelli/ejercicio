package com.pruebas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.dao.EstadisticaDAO;
import com.pruebas.model.Estadistica;

@RestController
@RequestMapping("estadisticas")
public class EstadisticaRest {

	@Autowired
	private EstadisticaDAO estadisticaDAO;

	@PostMapping("/guardar")
	public void guardar(@RequestBody Estadistica estadistica) {
		this.estadisticaDAO.save(estadistica);
	}

	@GetMapping("/listar")
	public List<Estadistica> listar() {
		return this.estadisticaDAO.findAll();
	}

	@DeleteMapping("/eliminar/{id}")
	public void eliminar(@PathVariable String pais) {
		this.estadisticaDAO.deleteById(pais);
	}

	@PutMapping
	public void actualizar(@RequestBody Estadistica estadistica) {
		this.estadisticaDAO.save(estadistica);
	}
	
	@GetMapping("/menordistancia")
	public double getMenorDistancia() {
		return this.estadisticaDAO.getMenorDistancia().getDistancia();
	}
	
	@GetMapping("/mayordistancia")
	public double getMayorDistancia() {
		return this.estadisticaDAO.getMayorDistancia().getDistancia();
	}
	
	@GetMapping("/promediodistancia")
	public double getPromedioDistancia() {
		
		double distancia = 0.0;
		int invocaciones = 0;
		
		for (Estadistica e : this.estadisticaDAO.getEstadisticas()) {
			distancia += e.getDistancia() * e.getInvocaciones();
			invocaciones += e.getInvocaciones();
		}
		return distancia/invocaciones;
	}

}
