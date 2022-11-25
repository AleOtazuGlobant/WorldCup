package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.WorldCup.demo.models.PartidoModel;
import com.WorldCup.demo.repositories.PartidoRepository;



@Service
public class PartidoService {
	
	@Autowired
	PartidoRepository partidoRepository;
	
	public ArrayList<PartidoModel> obtenerPartidos(){
		
		return (ArrayList<PartidoModel>) partidoRepository.findAll();
		
		
	}
	
	public PartidoModel guardarPartido(PartidoModel partido) {
		return partidoRepository.save(partido);
	}
	
	public Optional<PartidoModel>obtenerPorId(Long id){
		return partidoRepository.findById(id);
	}
	
	public void actualizarPartido (Long id, PartidoModel partido) {
		PartidoModel encuentro= partidoRepository.findById(id).get();
		encuentro.setNombre_equipo1(partido.getNombre_equipo1());
		encuentro.setNombre_equipo2(partido.getNombre_equipo2());
		encuentro.setEquipo1_id(partido.getEquipo1_id());
		encuentro.setEquipo2_id(partido.getEquipo2_id());
		encuentro.setGoles_equipo1(partido.getGoles_equipo1());
		encuentro.setGoles_equipo2(partido.getGoles_equipo2());
		encuentro.setResultado(partido.getResultado());
		partidoRepository.save(encuentro);
		
	}
	
	

	public boolean eliminarPartido (Long id) {
		try {
			partidoRepository.deleteById(id);
			return true;
			} catch(Exception err) {
				System.out.println(err.toString());
				return false;
			}

	}

}
