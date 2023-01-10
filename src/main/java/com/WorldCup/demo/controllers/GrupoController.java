package com.WorldCup.demo.controllers;


import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
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

import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.models.GrupoModel;
import com.WorldCup.demo.models.PartidoModel;

import com.WorldCup.demo.services.GrupoService;



@RestController
@RequestMapping("/grupos")
public class GrupoController {
	

	
	@Autowired
	GrupoService grupoService;
	
	
	

	@GetMapping()
	public  List<GrupoModel> obtenerGrupos(){
		return grupoService.obtenerGrupos();
	}
	
		
	@PostMapping()
	public ResponseEntity<GrupoModel> guardarGrupo(@RequestBody @Valid GrupoModel grupo) {
		
				return this.grupoService.comprobarGrupo(grupo);
	}
	
	
	@GetMapping( path = "/{id}")
	public Optional<GrupoModel>obtenerGrupoPorId(@PathVariable ("id") Long id){
		return this.grupoService.obtenerPorId(id);
	}
	

	@GetMapping(path = "/{id}/mejoresPuntos")
	public List<EquipoModel> primerosDelGrupo(@PathVariable ("id") Long id){
		return this.grupoService.mejoresPuntajes(id);
	}
	
	
	
	
	@GetMapping( path = "/{id}/simularPartidosGp")
	
	public ResponseEntity< List<PartidoModel> >simularPartidosGrupo(@RequestBody @PathVariable ("id") Long id){

		return this.grupoService.partidosDeGrupo(id);
	}
	
	@DeleteMapping( path = "/{id}")
	public String eliminarPorId(@PathVariable ("id") Long id) {
		boolean ok = this.grupoService.eliminarGrupo(id);
		if(ok) {
			return "Se ha eliminado con éxito el grupo con id: " + id;
		
		}else {
			return "No se pudo eliminar el grupo con id: " + id;
		}
	}
		
	@PutMapping( path = "/{id}")
	public ResponseEntity<Optional<GrupoModel>>actualizarGrupo(@PathVariable ("id") Long id,@RequestBody GrupoModel grupo) {
		this.grupoService.actualizarGrupo(id);
	
		Optional<GrupoModel>group = this.grupoService.obtenerPorId(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(group);	 
	}
	
}
