package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.models.JugadorModel;
import com.WorldCup.demo.repositories.JugadorRepository;

@Service
public class JugadorService {
	@Autowired	
	JugadorRepository jugadorRepository;
	
	@Autowired	
	EquipoService equipoService;
	
	
	public ArrayList<JugadorModel> obtenerJugadores(){
		return (ArrayList<JugadorModel>) jugadorRepository.findAll();
	}
	
	public JugadorModel	guardarJugador(JugadorModel jugador) {
		return jugadorRepository.save(jugador);
	}
	
	public Long contarPorPais (String pais) {
		return jugadorRepository.countByPais(pais);
	}	
	
	public void actualizarJugador (Long id, JugadorModel jugador) {
		JugadorModel jugadorFromDb = jugadorRepository.findById(id).get();
		jugadorFromDb.setNombre(jugador.getNombre());
		jugadorFromDb.setApellido(jugador.getApellido());
		jugadorFromDb.setPais(jugador.getPais());
		jugadorFromDb.setPasaporte(jugador.getPasaporte());
		jugadorRepository.save(jugadorFromDb);
		
	}
	
	public Optional<JugadorModel>obtenerPorId(Long id){
		return jugadorRepository.findById(id);
	}
	
	public ArrayList<JugadorModel> obtenerPorPais (String pais){
		return jugadorRepository.findByPais(pais);
	}
	
	public JugadorModel obtenerPorPasaporte (String pasaporte){
		return jugadorRepository.findByPasaporte(pasaporte);
	}
	
	public boolean eliminarJugador (Long id) {
		JugadorModel jugFromDb = jugadorRepository.findById(id).get();
		EquipoModel equipoDelJugador = jugFromDb.getEquipo();
		try {
			
			if (equipoDelJugador != null) {
				if (equipoDelJugador.getCantJugadores()==11) {
					
					return false;
				};
			};
			
			jugadorRepository.deleteById(id);
			return true;
		} catch(Exception err) {
			return false;
		}
	}
	//////////////////////////////////////////
	//Prueba unitaria
	public ResponseEntity<JugadorModel>comprobarJugador(@RequestBody @Valid JugadorModel jugador) {
		
		JugadorModel existente = this.obtenerPorPasaporte(jugador.getPasaporte());
		if(existente != null) {
			 
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}else {
			Long cantJugadores = this.contarPorPais(jugador.getPais());
			
			if(cantJugadores == 26) {
								 
				 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
				 
			}else {
				EquipoModel equipo = this.equipoService.obtenerEquipoPorPais(jugador.getPais());
				if (equipo != null) {			
					jugador.setEquipo(equipo);
				}
				
				JugadorModel jugadorNuevo = this.guardarJugador(jugador);
				
				return ResponseEntity.status(HttpStatus.CREATED).body(jugadorNuevo);
			}						
		}	
	}

}
