package com.WorldCup.demo.repositories;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.WorldCup.demo.models.PartidoModel;

@Repository
public interface PartidoRepository extends CrudRepository <PartidoModel, Long>{

	PartidoModel findByResultado(String resultado);
	



}
