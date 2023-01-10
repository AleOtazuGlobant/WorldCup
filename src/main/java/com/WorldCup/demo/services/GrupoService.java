package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.models.GrupoModel;
import com.WorldCup.demo.models.PartidoModel;
import com.WorldCup.demo.repositories.GrupoRepository;

@Service 
public class GrupoService {
	@Autowired
	GrupoRepository grupoRepository;
	
	@Autowired
	PartidoService partidoService;
	
	
		
	public List<GrupoModel>obtenerGrupos(){
		return (ArrayList<GrupoModel>)grupoRepository.findAll();
	}
	
	public GrupoModel guardarGrupo(GrupoModel grupo) {
		return grupoRepository.save(grupo);
	}
	public Optional<GrupoModel>obtenerPorId(Long id){
		return grupoRepository.findById(id);
	}
	
	
	
	public void actualizarGrupo (Long id) {
		
		Optional<GrupoModel> grupoFromDb = grupoRepository.findById(id);
		
		if (grupoFromDb.isPresent()) {
						
		 GrupoModel grup = grupoFromDb.get();
				grupoRepository.save(grup);
		}
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
	
	//////////////////////////
	//probar este con test unitarios
	//Comprobar que el grupo no esté registrado para evitar duplicaciones
	
	public ResponseEntity<GrupoModel> comprobarGrupo(@RequestBody @Valid GrupoModel grupo) {
		
		if (Boolean.TRUE.equals(this.existeGrupo(grupo.getNombre()))) {
			
	    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
	    	
	    } else {
		    
		   GrupoModel grupoNuevo = this.guardarGrupo(grupo);
		   
	    	return ResponseEntity.status(HttpStatus.CREATED).body(
	    			grupoNuevo
	    	);
	    			    	
	    }
	}
	
	//simular los partidos de grupo
	public ResponseEntity< List<PartidoModel> >partidosDeGrupo(@RequestBody @PathVariable ("id") Long id){
		
		Optional<GrupoModel> grupo= this.obtenerPorId(id);
		
				
		if(grupo.isEmpty()) {
			return null;
		}
		
		boolean played = grupo.get().isJugado(); 
		
		if (played) {
		  				    	
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
				
		}
			
		
		
		
		List<PartidoModel> partidosSimulados = new ArrayList<>();
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
     	GrupoModel grupp = grupo.get();
     	this.guardarGrupo(grupp);
			
		return ResponseEntity.status(HttpStatus.CREATED).body(partidosSimulados);
			
		}
	
	
	//SACAR MEJORES 2 DE CADA GRUPO
	public List<EquipoModel> mejoresPuntajes( Long id){

		//obtengo el grupo
		Optional<GrupoModel> grupo= this.obtenerPorId(id);
		
		if(grupo.isEmpty()) {
			return  List.of();
		}

		//obtengo los equipos  del grupo
		List<EquipoModel> equipos= grupo.get().getEquipos();

		EquipoModel equipo1 = equipos.get(0);
		EquipoModel equipo2 = equipos.get(1);
		EquipoModel equipo3 = equipos.get(2);
		EquipoModel equipo4 = equipos.get(3);
		
		//cada equipo tiene puntos 
		int puntosEq1= equipo1.getPuntos();
		int puntosEq2= equipo2.getPuntos();
		int puntosEq3= equipo3.getPuntos();
		int puntosEq4= equipo4.getPuntos();
		
		ArrayList<Integer> puntajeEquipos = new ArrayList<>();
		puntajeEquipos.add(puntosEq1);
		puntajeEquipos.add(puntosEq2);
		puntajeEquipos.add(puntosEq3);
		puntajeEquipos.add(puntosEq4);

	
		int  mayorPuntaje = puntajeEquipos.get(0);
		int posicion= 0;
        int posicion2= 0;
    	EquipoModel primeroDelGrupo; 
    	EquipoModel segundoDelGrupo; 
    	
        //busco el maximo con un loop
        for (int i = 0 ; i < 4; i++) {
        	
            if (puntajeEquipos.get(i) > mayorPuntaje) {
            	mayorPuntaje = puntajeEquipos.get(i);
            	posicion = puntajeEquipos.indexOf(mayorPuntaje);
           }
            
        }
        
        primeroDelGrupo = equipos.get(posicion);    
        puntajeEquipos.remove(posicion);
        equipos.remove(posicion);
       
       
        int  mayorPuntaje2 = puntajeEquipos.get(0);
              
        for (int i = 0 ; i < 3; i++) {
        	
            if (puntajeEquipos.get(i) > mayorPuntaje2) {
            	mayorPuntaje2 = puntajeEquipos.get(i);
            	posicion2 = puntajeEquipos.indexOf(mayorPuntaje2);
 
            }         
            
        }
        
        segundoDelGrupo =  equipos.get(posicion2);
        
        List<EquipoModel> mejoresPuntos = new ArrayList<>();
        mejoresPuntos.add(primeroDelGrupo);
        mejoresPuntos.add(segundoDelGrupo);
       
		return  mejoresPuntos;
	}
	

}
