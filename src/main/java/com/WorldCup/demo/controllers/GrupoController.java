package com.WorldCup.demo.controllers;

import java.util.ArrayList;
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
import com.WorldCup.demo.services.EquipoService;
import com.WorldCup.demo.services.GrupoService;
import com.WorldCup.demo.services.PartidoService;


@RestController
@RequestMapping("/grupos")
public class GrupoController {
	
	
	@Autowired
	GrupoService grupoService;
	
	@Autowired
	EquipoService equipoService;
	
	@Autowired
	PartidoService partidoService;
	
	@GetMapping()
	public  ArrayList<GrupoModel> obtenerGrupos(){
		return grupoService.obtenerGrupos();
	}
	
		
	@PostMapping()
	public ResponseEntity<GrupoModel> guardarGrupo(@RequestBody @Valid GrupoModel grupo) {
		
		if (this.grupoService.existeGrupo(grupo.getNombre())) {
	    	System.out.println ("El grupo que desea cargar ya se encuentra registrado");
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    } else {
		    
		   GrupoModel grupoNuevo = this.grupoService.guardarGrupo(grupo);
		   
	    	return ResponseEntity.status(HttpStatus.CREATED).body(
	    			grupoNuevo
	    	);
	    			    	
	    }
		 
	}
	
	
	@GetMapping( path = "/{id}")
	public Optional<GrupoModel>obtenerGrupoPorId(@PathVariable ("id") Long id){
		return this.grupoService.obtenerPorId(id);
	}
	
	@GetMapping( path = "/{id}/simularPartidosGp")
	
	public ResponseEntity< List<PartidoModel> >simularPartidosGrupo(@RequestBody @PathVariable ("id") Long id){
		Optional<GrupoModel> grupo= this.grupoService.obtenerPorId(id);
		boolean played = grupo.get().isJugado(); 

		List<PartidoModel> partidosSimulados = new ArrayList<PartidoModel>();
		
		if (played) {

	    	System.out.println ("Los partidos de este grupo ya fueron cargados");
	    	
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
				
		List<EquipoModel> equipos= grupo.get().getEquipos();
				
		EquipoModel equipo1 = equipos.get(0);
		EquipoModel equipo2 = equipos.get(1);
		EquipoModel equipo3 = equipos.get(2);
		EquipoModel equipo4 = equipos.get(3);
			
//		Generar objeto de partido y guardar en la Bd
		PartidoModel partido1 = new PartidoModel();
		partido1.setEquipo1_id(equipo1.getId());
		partido1.setEquipo2_id(equipo2.getId());
		partido1 = partidoService.guardarPartido(partido1);
	
		PartidoModel partido2 = new PartidoModel();
		partido2.setEquipo1_id(equipo1.getId());
		partido2.setEquipo2_id(equipo3.getId());
		partido2 = partidoService.guardarPartido(partido2);
		
		PartidoModel partido3 = new PartidoModel();
		partido3.setEquipo1_id(equipo1.getId());
		partido3.setEquipo2_id(equipo4.getId());
		partido3 = partidoService.guardarPartido(partido3);
		
		PartidoModel partido4 = new PartidoModel();
		partido4.setEquipo1_id(equipo2.getId());
		partido4.setEquipo2_id(equipo3.getId());
		partido4 = partidoService.guardarPartido(partido4);
		
		PartidoModel partido5 = new PartidoModel();
		partido5.setEquipo1_id(equipo2.getId());
		partido5.setEquipo2_id(equipo4.getId());
		partido5 = partidoService.guardarPartido(partido5);
		
		PartidoModel partido6 = new PartidoModel();
		partido6.setEquipo1_id(equipo3.getId());
		partido6.setEquipo2_id(equipo4.getId());
		partido6 = partidoService.guardarPartido(partido6);
		
//		SIMULAR PARTIDO GUARDADO EN base A SU id
		PartidoModel partido1Simulado = this.partidoService.simularPartido(partido1.getId());
		PartidoModel partido2Simulado = this.partidoService.simularPartido(partido2.getId());
		PartidoModel partido3Simulado = this.partidoService.simularPartido(partido3.getId());
		PartidoModel partido4Simulado = this.partidoService.simularPartido(partido4.getId());
		PartidoModel partido5Simulado = this.partidoService.simularPartido(partido5.getId());
     	PartidoModel partido6Simulado = this.partidoService.simularPartido(partido6.getId());
     	
     	partidosSimulados.add(partido1Simulado);
     	partidosSimulados.add(partido2Simulado);
     	partidosSimulados.add(partido3Simulado);
     	partidosSimulados.add(partido4Simulado);
     	partidosSimulados.add(partido5Simulado);     	
     	partidosSimulados.add(partido6Simulado);
     
     	grupo.get().setJugado(true);
     	GrupoModel grup = grupo.get();
     	grupoService.guardarGrupo(grup);
  	
     	//return partidosSimulados;
     	return ResponseEntity.status(HttpStatus.CREATED).body(partidosSimulados);
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
		this.grupoService.actualizarGrupo(id,grupo);
	
		Optional<GrupoModel>group = this.grupoService.obtenerPorId(id);
		
		return ResponseEntity.status(HttpStatus.OK).body(group);	 
	}
	
}
