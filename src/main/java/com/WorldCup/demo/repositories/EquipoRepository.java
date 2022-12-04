package com.WorldCup.demo.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.WorldCup.demo.models.EquipoModel;


@Repository
public interface EquipoRepository extends CrudRepository<EquipoModel, Long> {
	public abstract EquipoModel findByPais( String pais);
	public abstract Optional<EquipoModel> findById( Long id);
	Boolean existsByPais(String pais);
	
}
