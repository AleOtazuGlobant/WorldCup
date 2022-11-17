package com.WorldCup.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.WorldCup.demo.models.EquipoModel;

import com.WorldCup.demo.services.EquipoService;

@RestController
@RequestMapping("/equipos")
public class EquipoController {
	
	@Autowired
	EquipoService equipoService;
	
	@GetMapping()
	public  ArrayList<EquipoModel> obtenerEquipos(){
		return equipoService.obtenerEquipos();
	}
	
		
	@PostMapping()
	public EquipoModel guardarEquipo(@RequestBody EquipoModel equipo) {
		return this.equipoService.guardarEquipo(equipo);
	}
	

	@GetMapping( path = "/{id}")
	public Optional<EquipoModel>obtenerEquipoPorId(@PathVariable ("id") Long id){
		return this.equipoService.obtenerPorId(id);
	}
	
	@GetMapping("/query")
	public  ArrayList<EquipoModel>obtenerEquipoPorCountry(@RequestParam("pais")String pais){
		return this.equipoService.obtenerEquipoPorPais(pais);
	}
	
	@DeleteMapping( path = "/{id}")
	public String eliminarPorId(@PathVariable ("id") Long id) {
		boolean ok = this.equipoService.eliminarEquipo(id);
		if(ok) {
			return "Se ha eliminado con éxito el equipo con id: " + id;
		
		}else {
			return "No se pudo eliminar el equipo con id: " + id;
		}
	}
	

}
