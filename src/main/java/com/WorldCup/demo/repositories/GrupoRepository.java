package com.WorldCup.demo.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.WorldCup.demo.models.GrupoModel;


@Repository
public interface GrupoRepository extends CrudRepository<GrupoModel, Long> {
	public abstract Optional<GrupoModel> findById( Long id);
	public abstract GrupoModel findByNombre(String nombre);
	
	Boolean existsByNombre(String nombre);
	

		
}
