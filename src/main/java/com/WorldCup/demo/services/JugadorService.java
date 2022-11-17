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
	
	public Optional<JugadorModel>obtenerPorId(Long id){
		return jugadorRepository.findById(id);
	}
	
	public ArrayList<JugadorModel> obtenerPorPais (String pais){
		return jugadorRepository.findByPais(pais);
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
