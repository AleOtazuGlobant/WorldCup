package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WorldCup.demo.models.EquipoModel;

import com.WorldCup.demo.repositories.EquipoRepository;

@Service 
public class EquipoService {
	@Autowired
	EquipoRepository equipoRepository;
	
	
	
	public ArrayList<EquipoModel>obtenerEquipos(){
		return (ArrayList<EquipoModel>) equipoRepository.findAll();
	}
	
	public EquipoModel guardarEquipo(EquipoModel equipo) {
		return equipoRepository.save(equipo);
	}
	public Optional<EquipoModel>obtenerPorId(Long id){
		return equipoRepository.findById(id);
	}
	
	public EquipoModel obtenerEquipoPorPais(String pais){
		return equipoRepository.findByPais(pais);
	}
	
	public boolean eliminarEquipo (Long id) {
		try {
			equipoRepository.deleteById(id);
			return true;
			} catch(Exception err) {
				return false;
			}

	}
	
	public boolean existeEquipo (String pais) {
		return equipoRepository.existsByPais(pais);
	}
}
