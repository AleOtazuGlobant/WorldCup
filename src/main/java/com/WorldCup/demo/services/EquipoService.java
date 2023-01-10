package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.models.GrupoModel;
import com.WorldCup.demo.models.JugadorModel;
import com.WorldCup.demo.repositories.EquipoRepository;
import com.WorldCup.demo.repositories.GrupoRepository;

@Service 
public class EquipoService {
	@Autowired
	EquipoRepository equipoRepository;
	@Autowired
	GrupoRepository grupoRepository;
	@Autowired
	JugadorService jugadorService;
	
	
	public List<EquipoModel>obtenerEquipos(){

		return (ArrayList<EquipoModel>) equipoRepository.findAll();
	}
	
	public EquipoModel guardarEquipo(EquipoModel equipo) {
		return equipoRepository.save(equipo);
	}
	public Optional<EquipoModel> obtenerPorId(Long id){
		return equipoRepository.findById(id);
	}



	public void actualizarEquipo (Long id, EquipoModel equipo) {
		Optional<EquipoModel> equipoFromDb = equipoRepository.findById(id);
		
		Optional<GrupoModel> g = grupoRepository.findById(equipo.getGrupo().getId());
		
		if(equipoFromDb.isPresent()&& g.isPresent()) {
			
		EquipoModel eq = equipoFromDb.get();
		
		eq.setPais(equipo.getPais());
		eq.setGrupo(g.get());
		
		equipoRepository.save(eq);
		}
	}

	public  EquipoModel guardarEquipoJugadores (EquipoModel equipo) {
	    Long cantidadJugadores = this.jugadorService.contarPorPais(equipo.getPais());
	    Boolean equipoExistente = existeEquipo(equipo.getPais());
	  
	    //primero verifico que el equipo no este creado previamente
	    if (Boolean.TRUE.equals(equipoExistente)) {
	    	
	    	return null;
	    }
	    
	    if (cantidadJugadores >= 11 && cantidadJugadores <= 26) {
	    	List <JugadorModel> jugPorPais = this.jugadorService.obtenerPorPais(equipo.getPais());
	   		equipo.setJugadores(jugPorPais); 
	   		
	   		return this.guardarEquipo(equipo);	
	    } else {
	    	
	    	return null;	
	    }
			 
	}
	
	public EquipoModel obtenerEquipoPorPais(String pais){
		return equipoRepository.findByPais(pais);
	}
	
	public boolean eliminarEquipo (Long id) {
		try {
			equipoRepository.deleteById(id);
			return true;
			} catch(Exception err) {
				System.out.println(err.toString());
				return false;
			}

	}
	
	public boolean existeEquipo (String pais) {
		return equipoRepository.existsByPais(pais);
	}
	
	public boolean existeEquipoPorId (Long id) {
		return equipoRepository.existsById(id);
	}

	
}
