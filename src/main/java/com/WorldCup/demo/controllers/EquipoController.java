package com.WorldCup.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.services.EquipoService;
import com.WorldCup.demo.services.JugadorService;

@RestController
@RequestMapping("/equipos")
public class EquipoController {
	
	@Autowired
	EquipoService equipoService;
	@Autowired
	JugadorService jugadorService;
	
	@GetMapping()
	public  ArrayList<EquipoModel> obtenerEquipos(){
		return equipoService.obtenerEquipos();
	}
			
	@PostMapping()
	public ResponseEntity<EquipoModel> guardarEquipo(@RequestBody @Valid EquipoModel equipo) {

		return equipoService.verificarJugadores(equipo);
	 
	}
	
	@GetMapping( path = "/{id}")
	public Optional<EquipoModel> obtenerEquipoPorId(@PathVariable ("id") Long id){
		return this.equipoService.obtenerPorId(id);
	}
	
	@GetMapping("/query")
	public  EquipoModel obtenerEquipoPorCountry(@RequestParam("pais")String pais){
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
		
	@PutMapping( path = "/{id}")
	public ResponseEntity<Optional<EquipoModel>>actualizarEquipo(@PathVariable ("id") Long id, @RequestBody EquipoModel equipo) {
		this.equipoService.actualizarEquipo(id, equipo);
	
		Optional<EquipoModel> eq = this.equipoService.obtenerPorId(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(eq);	 
	}
	

}
