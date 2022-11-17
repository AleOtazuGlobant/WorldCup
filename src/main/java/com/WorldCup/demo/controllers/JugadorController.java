package com.WorldCup.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.WorldCup.demo.models.JugadorModel;
import com.WorldCup.demo.services.JugadorService;

@RestController
@RequestMapping("/jugadores")
public class JugadorController {
	
	@Autowired
	JugadorService jugadorService;
	
	@GetMapping()
	public ArrayList<JugadorModel> obtenerJugadores(){
		return jugadorService.obtenerJugadores();
	}
		
	@PostMapping()
	public JugadorModel guardarJugador (@RequestBody @Valid JugadorModel jugador) {
		return this.jugadorService.guardarJugador(jugador);
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
