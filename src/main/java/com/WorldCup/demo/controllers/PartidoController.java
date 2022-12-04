package com.WorldCup.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.WorldCup.demo.models.PartidoModel;
import com.WorldCup.demo.services.EquipoService;
import com.WorldCup.demo.services.PartidoService;

@RestController
@RequestMapping("/partidos")

public class PartidoController {
	
	@Autowired
	PartidoService partidoService;
	
	@Autowired
	EquipoService equipoService;
	
	@GetMapping()
	public ArrayList<PartidoModel> obtenerPartidos(){
		return partidoService.obtenerPartidos();
	}
	
	@PostMapping()
	public ResponseEntity<PartidoModel> guardarPartido(@RequestBody PartidoModel partido) {
						
		Boolean equipo1Exists= this.equipoService.existeEquipoPorId(partido.getEquipo1_id());
		Boolean equipo2Exists= this.equipoService.existeEquipoPorId(partido.getEquipo2_id());
		
	
		
		 if (!equipo1Exists || !equipo2Exists) {
			 System.out.println ("No se puede crear el partido equipo/equipos ingresados no existe");
		    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		 }
		 
		 if(partido.getEquipo1_id()== partido.getEquipo2_id()) {
			System.out.println ("No se puede crear el partido con un solo equipo");
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		    	
	    }
		 	System.out.println ("partido creado");
	    	 return ResponseEntity.status(HttpStatus.OK).body(this.partidoService.guardarPartido(partido));
	    		   	        
	}
	
	@GetMapping( path = "/{id}")
	public Optional<PartidoModel>obtenerPartidoPorId(@PathVariable ("id") Long id){
		return this.partidoService.obtenerPorId(id);
	}
	@PutMapping( path = "/{id}")
	public ResponseEntity<Optional<PartidoModel>> actualizarPartido(@PathVariable ("id") Long id,@RequestBody PartidoModel partido) {
		 this.partidoService.actualizarPartido(id,partido);
		 Optional<PartidoModel>p = this.partidoService.obtenerPorId(id);
		 			 
		return ResponseEntity.status(HttpStatus.OK).body(p);	 
	}

	@GetMapping( path = "/{id}/simular")
	public  PartidoModel obtenerSimularPartido(@PathVariable ("id") Long id){
		return this.partidoService.simularPartido(id);
		
	}
		
	@DeleteMapping( path = "/{id}")
	public String eliminarPorId(@PathVariable ("id") Long id) {
		boolean ok = this.partidoService.eliminarPartido(id);
		if(ok) {
			return "Se ha eliminado con éxito el partido con id: " + id;
		
		}else {
			return "No se pudo eliminar el partido con id: " + id;
		}
	}
	
}
