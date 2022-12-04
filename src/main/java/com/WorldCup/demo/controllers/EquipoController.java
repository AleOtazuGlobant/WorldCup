package com.WorldCup.demo.controllers;

import java.util.ArrayList;
import java.util.List;
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
import com.WorldCup.demo.models.JugadorModel;
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
	  Long cantidadJugadores= this.jugadorService.contarPorPais(equipo.getPais());
	  Boolean equipoExistente= this.equipoService.existeEquipo(equipo.getPais());
	  System.out.println ("Ya esxiste el pais? " + equipoExistente);
	    System.out.println ("Jugadores existentes " + cantidadJugadores);
	    
	    if (equipoExistente) {
	    	
	    	System.out.println ("El pais que desea cargar ya se encuentra registrado");
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    }
	    
	    if (cantidadJugadores >= 11 & cantidadJugadores <= 26) {
	    	   List <JugadorModel> jugPorPais = this.jugadorService.obtenerPorPais(equipo.getPais());
	   		
	   			equipo.setJugadores(jugPorPais); 
	   			System.out.println ("Equipo creado");
	   		return ResponseEntity.status(HttpStatus.CREATED).body(this.equipoService.guardarEquipo(equipo));
	    }else {
	    	
	    	System.out.println ("La cantidad de jugadores es incorrecta: necesita min 11 y maximo 26");
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    			    	
	    }
	
	 
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
