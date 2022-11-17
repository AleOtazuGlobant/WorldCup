package com.WorldCup.demo.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.WorldCup.demo.models.EquipoModel;


@Repository
public interface EquipoRepository extends CrudRepository<EquipoModel, Long> {
	public abstract ArrayList<EquipoModel> findByPais( String pais);
}
