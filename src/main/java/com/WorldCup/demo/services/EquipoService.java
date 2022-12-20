package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	
	public ArrayList<EquipoModel>obtenerEquipos(){
		return (ArrayList<EquipoModel>) equipoRepository.findAll();
	}
	
	public EquipoModel guardarEquipo(EquipoModel equipo) {
		return equipoRepository.save(equipo);
	}
	public Optional<EquipoModel> obtenerPorId(Long id){
		return equipoRepository.findById(id);
	}
	//Prueba unitaria de este metodo
	public void actualizarEquipo (Long id, EquipoModel equipo) {
		Optional<EquipoModel> equipoFromDb = equipoRepository.findById(id);
		Optional<GrupoModel> g = grupoRepository.findById(equipo.getGrupo().getId());
		
		EquipoModel eq = equipoFromDb.get();
		
		eq.setPais(equipo.getPais());
		eq.setGrupo(g.get());
		
		equipoRepository.save(eq);
		
	}
	
	//prueba unitaria de este metodo
	public  ResponseEntity<EquipoModel> verificarJugadores (@RequestBody @Valid EquipoModel equipo) {
		  Long cantidadJugadores = this.jugadorService.contarPorPais(equipo.getPais());
		  Boolean equipoExistente = existeEquipo(equipo.getPais());
		  
		    
		    if (equipoExistente) {
		    	
		    	
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		    }
		    
		    if (cantidadJugadores >= 11 & cantidadJugadores <= 26) {
		    	   List <JugadorModel> jugPorPais = this.jugadorService.obtenerPorPais(equipo.getPais());
		   		
		   			equipo.setJugadores(jugPorPais); 
		   			
		   		return ResponseEntity.status(HttpStatus.CREATED).body(guardarEquipo(equipo));
		    }else {
		    	
		    	
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		    			    	
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
