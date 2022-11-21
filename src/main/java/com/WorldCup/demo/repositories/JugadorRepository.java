package com.WorldCup.demo.repositories;


import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.WorldCup.demo.models.JugadorModel;

@Repository
public interface JugadorRepository extends CrudRepository <JugadorModel, Long> {
	public abstract ArrayList<JugadorModel> findByPais( String pais);
	public abstract JugadorModel findByPasaporte( String pasaporte);

}


