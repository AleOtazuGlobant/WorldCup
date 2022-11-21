package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WorldCup.demo.models.JugadorModel;
import com.WorldCup.demo.repositories.JugadorRepository;

@Service
public class JugadorService {
	@Autowired	
	JugadorRepository jugadorRepository;
	
	public ArrayList<JugadorModel> obtenerJugadores(){
		return (ArrayList<JugadorModel>) jugadorRepository.findAll();
	}
	
	public JugadorModel	guardarJugador(JugadorModel jugador) {
		return jugadorRepository.save(jugador);
	}
	
	public void actualizarJugador (Long id, JugadorModel jugador) {
		JugadorModel jugadorFromDb= jugadorRepository.findById(id).get();
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
		try {
			jugadorRepository.deleteById(id);
			return true;
			} catch(Exception err) {
				return false;
			}
	}

}
