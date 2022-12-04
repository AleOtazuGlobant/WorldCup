package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WorldCup.demo.models.GrupoModel;


import com.WorldCup.demo.repositories.GrupoRepository;

@Service 
public class GrupoService {
	@Autowired
	GrupoRepository grupoRepository;
	
	
	
	public ArrayList<GrupoModel>obtenerGrupos(){
		return (ArrayList<GrupoModel>)grupoRepository.findAll();
	}
	
	public GrupoModel guardarGrupo(GrupoModel grupo) {
		return grupoRepository.save(grupo);
	}
	public Optional<GrupoModel>obtenerPorId(Long id){
		return grupoRepository.findById(id);
	}
	
	public void actualizarGrupo (Long id, GrupoModel grupo) {
		GrupoModel grupoFromDb= grupoRepository.findById(id).get();
		
				grupoRepository.save(grupoFromDb);
	}
	
	
	
	public boolean eliminarGrupo (Long id) {
		try {
			grupoRepository.deleteById(id);
			return true;
			} catch(Exception err) {
				System.out.println(err.toString());
				return false;
			}

	}
	
	public Boolean existeGrupo (String nombre) {
		return grupoRepository.existsByNombre(nombre);
	}
}
