package com.WorldCup.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

@Validated
@RestController
@RequestMapping("/jugadores")
public class JugadorController {
	
	@Autowired
	JugadorService jugadorService;
	@Autowired
	EquipoService equipoService;
	
	@GetMapping()
	public ArrayList<JugadorModel> obtenerJugadores(){
		return jugadorService.obtenerJugadores();
	}
		
	@PostMapping()
	public ResponseEntity<JugadorModel> guardarJugador (@RequestBody @Valid JugadorModel jugador) {
	
		JugadorModel existente = this.jugadorService.obtenerPorPasaporte(jugador.getPasaporte());
		if(existente != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}else {
			Long cantJugadores= this.jugadorService.contarPorPais(jugador.getPais());
			
			if(cantJugadores == 26) {
				
				 System.out.println ("El equipo ya tiene el maximo de jugadores");
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}else {
				EquipoModel equipo = this.equipoService.obtenerEquipoPorPais(jugador.getPais());
				if (equipo != null) {			
					jugador.setEquipo(equipo);
				}
				
				JugadorModel jugadorNuevo = this.jugadorService.guardarJugador(jugador);
				
				return ResponseEntity.status(HttpStatus.CREATED).body(jugadorNuevo);
			}
			
						
		}
		
	}
	
	@PutMapping( path = "/{id}")
	public ResponseEntity<Optional<JugadorModel>>actualizarJugador(@PathVariable ("id") Long id,@RequestBody JugadorModel jugador) {
		 this.jugadorService.actualizarJugador(id,jugador);
		 Optional<JugadorModel>j = this.jugadorService.obtenerPorId(id);
		 			 
		return ResponseEntity.status(HttpStatus.OK).body(j);	 
	}
	
	@GetMapping( path = "/{id}")
	public Optional<JugadorModel>obtenerJugadorPorId(@PathVariable ("id") Long id){
		return this.jugadorService.obtenerPorId(id);
	}
	
	@GetMapping("/query")
	public  ArrayList<JugadorModel>obtenerJugadorPorPais(@RequestParam("pais")String pais){
		return this.jugadorService.obtenerPorPais(pais);
	}
	
	@DeleteMapping( path = "/{id}")
	public String eliminarPorId(@PathVariable ("id") Long id) {
		boolean ok = this.jugadorService.eliminarJugador(id);
		if(ok) {
			return "Se ha eliminado con éxito el jugador con id: " + id;
		
		}else {
			return "No se pudo eliminar el jugador con id: " + id;
		}
	}

}
