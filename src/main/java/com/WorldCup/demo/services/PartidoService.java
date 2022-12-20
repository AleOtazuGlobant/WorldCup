package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.models.GrupoModel;
import com.WorldCup.demo.models.PartidoModel;
import com.WorldCup.demo.repositories.EquipoRepository;
import com.WorldCup.demo.repositories.GrupoRepository;
import com.WorldCup.demo.repositories.PartidoRepository;

@Service
public class PartidoService {
	
	@Autowired
	PartidoRepository partidoRepository;
	@Autowired
	EquipoRepository equipoRepository;
	
	@Autowired
	EquipoService equipoService;
	
	@Autowired
	GrupoService grupoService;
	
	@Autowired
	GrupoRepository grupoRepository;
	
	public ArrayList<PartidoModel> obtenerPartidos(){
		
		return (ArrayList<PartidoModel>) partidoRepository.findAll();
			
	}
	
	public PartidoModel guardarPartido(PartidoModel partido) {
		return partidoRepository.save(partido);
	}
	
	public Optional<PartidoModel>obtenerPorId(Long id){
		return partidoRepository.findById(id);
	}
	
	public void actualizarPartido (Long id, PartidoModel partido) {
		PartidoModel encuentro = partidoRepository.findById(id).get();
		
		encuentro.setNombre_equipo1(partido.getNombre_equipo1());
		encuentro.setNombre_equipo2(partido.getNombre_equipo2());
		encuentro.setEquipo1_id(partido.getEquipo1_id());
		encuentro.setEquipo2_id(partido.getEquipo2_id());
		encuentro.setGoles_equipo1(partido.getGoles_equipo1());
		encuentro.setGoles_equipo2(partido.getGoles_equipo2());
		encuentro.setResultado(partido.getResultado());
		partidoRepository.save(encuentro);
		
	}
	
	public boolean eliminarPartido (Long id) {
		try {
			partidoRepository.deleteById(id);
			return true;
			} catch(Exception err) {
				System.out.println(err.toString());
				return false;
			}

	}

	public PartidoModel obtenerResultadoPartido(String resultado) {
		return partidoRepository.findByResultado(resultado);
	}
	
	public PartidoModel simularPartido(Long id) {
		PartidoModel match = partidoRepository.findById(id).get();	
		String fasePartido = match.getFase();
		Optional<EquipoModel> equipoUno = equipoService.obtenerPorId(match.getEquipo1_id());
		Optional<EquipoModel> equipoDos = equipoService.obtenerPorId(match.getEquipo2_id());
		Long ganadorUno = equipoUno.get().getId(); 
		Long ganadorDos = equipoDos.get().getId(); 
		EquipoModel equipo1 = equipoUno.get();
		EquipoModel equipo2 = equipoDos.get();
		String equipo1Pais = equipoUno.get().getPais();
		String equipo2Pais = equipoDos.get().getPais();
		int puntaje1 = 0;
		int puntaje2 = 0;
		String	resul = "";
					
		int goles_equipo1 = (int)(Math.random()*5);
		int goles_equipo2 = (int)(Math.random()*5);
		
		//compruebo la fase partido
		//si es null estoy en fase grupos necesito poner puntos
		
		if (fasePartido == null) {
			
			if (goles_equipo2 == goles_equipo1){
					puntaje1 += 1;
					puntaje2 += 1;	 
					resul = "Empate";
			}
			
			if (goles_equipo1 > goles_equipo2) {
				
				puntaje1 += 3;				
				resul = "Gana " + equipo1Pais;		
				
			}else {
				
				puntaje2 += 3;
				resul = "Gana " + equipo2Pais;
				
		    } 
			
	   //fase distinta de null tengo que poner en la tabla el id del ganador 
			
		}else {
			
			if (goles_equipo2 == goles_equipo1){
											
					match.setEquipo_ganador_id(ganadorUno);	
					resul = "Gana " + equipo1Pais;		
				}
			
			}if (goles_equipo1 > goles_equipo2) {
				
					match.setEquipo_ganador_id(ganadorUno);	
					resul = "Gana " + equipo1Pais;		
				
			}else {
				
				match.setEquipo_ganador_id(ganadorDos);		
				resul = "Gana " + equipo2Pais;
			
			}					
		 
		match.setGoles_equipo1(goles_equipo1);
		match.setGoles_equipo2(goles_equipo2);
		match.setNombre_equipo1(equipoUno.get().getPais());
		match.setNombre_equipo2(equipoDos.get().getPais());
		match.setResultado(resul);
		partidoRepository.save(match);
		
		equipo1.setPuntos(equipo1.getPuntos()+puntaje1);
		equipo2.setPuntos(equipo2.getPuntos()+puntaje2);
		equipoRepository.save(equipo1);
		equipoRepository.save(equipo2);
		
		return match;
			
	}
		
	private GrupoModel obtenerPorNombre(String string) {
		
		return grupoRepository.findByNombre(string);
	}
	
	//PARTIDOS OCTAVOS DE FINAL 
	
		public List<PartidoModel>octavos(){
						
			//ArrayList<GrupoModel> grupos = this.obtenerGrupos();
			List<PartidoModel> partidosOctavosSimulados = new ArrayList<PartidoModel>();
			
			GrupoModel grupoA = this.obtenerPorNombre("A");
			List<EquipoModel> octavosA = this.grupoService.mejoresPuntajes(grupoA.getId());
			EquipoModel primerEquipoA = octavosA.get(0);
			EquipoModel segundoEquipoA = octavosA.get(1);
			
			GrupoModel grupoB = this.obtenerPorNombre("B");
			List<EquipoModel> octavosB = this.grupoService.mejoresPuntajes(grupoB.getId());
			EquipoModel primerEquipoB =  octavosB.get(0);
			EquipoModel segundoEquipoB =  octavosB.get(1);
			
			GrupoModel grupoC = this.obtenerPorNombre("C");
			List<EquipoModel> octavosC = this.grupoService.mejoresPuntajes(grupoC.getId());
			EquipoModel primerEquipoC =  octavosC.get(0);
			EquipoModel segundoEquipoC =  octavosC.get(1);			
			
			GrupoModel grupoD = this.obtenerPorNombre("D");
			List<EquipoModel> octavosD = this.grupoService.mejoresPuntajes(grupoD.getId());
			EquipoModel primerEquipoD =  octavosD.get(0);
			EquipoModel segundoEquipoD =  octavosD.get(1);
			
			GrupoModel grupoE = this.obtenerPorNombre("E");
			List<EquipoModel> octavosE = this.grupoService.mejoresPuntajes(grupoE.getId());
			EquipoModel primerEquipoE =  octavosE.get(0);
			EquipoModel segundoEquipoE =  octavosE.get(1);
			
			GrupoModel grupoF = this.obtenerPorNombre("F");
			List<EquipoModel> octavosF = this.grupoService.mejoresPuntajes(grupoF.getId());
			EquipoModel primerEquipoF =  octavosF.get(0);
			EquipoModel segundoEquipoF =  octavosF.get(1);
			
			GrupoModel grupoG = this.obtenerPorNombre("G");
			List<EquipoModel> octavosG = this.grupoService.mejoresPuntajes(grupoG.getId());
			EquipoModel primerEquipoG =  octavosG.get(0);
			EquipoModel segundoEquipoG =  octavosG.get(1);
			
			GrupoModel grupoH = this.obtenerPorNombre("H");
			List<EquipoModel> octavosH = this.grupoService.mejoresPuntajes(grupoH.getId());
			EquipoModel primerEquipoH =  octavosH.get(0);
			EquipoModel segundoEquipoH =  octavosH.get(1);				
					
		  //crear partidos de octavos
	     //	guardar partidos en la Bd
		//linkear los octavos quienes es llave con quien
			Long llave1 = (long) 1;
			Long llave2 = (long) 2;
			Long llave3 = (long) 3;
			Long llave4 = (long) 4;
			
			//Guardar los partidos una vez linkeados
			PartidoModel partido1 = new PartidoModel();
			partido1.setEquipo1_id(primerEquipoA.getId());
			partido1.setEquipo2_id(segundoEquipoB.getId());
			partido1.setFase("octavos");
			partido1.setLlaveId(llave1);
			partido1 =this.guardarPartido(partido1);
		
			PartidoModel partido2 = new PartidoModel();
			partido2.setEquipo1_id(primerEquipoB.getId());
			partido2.setEquipo2_id(segundoEquipoA.getId());
			partido2.setFase("octavos");
			partido2.setLlaveId(llave3);
			partido2 = this.guardarPartido(partido2);
			
			PartidoModel partido3 = new PartidoModel();
			partido3.setEquipo1_id(primerEquipoD.getId());
			partido3.setEquipo2_id(segundoEquipoC.getId());
			partido3.setFase("octavos");
			partido3.setLlaveId(llave3);
			partido3 = this.guardarPartido(partido3);
			
			PartidoModel partido4 = new PartidoModel();
			partido4.setEquipo1_id(primerEquipoC.getId());
			partido4.setEquipo2_id(segundoEquipoD.getId());
			partido4.setFase("octavos");
			partido4.setLlaveId(llave1);
			partido4 = this.guardarPartido(partido4);						
			
			PartidoModel partido5 = new PartidoModel();
			partido5.setEquipo1_id(primerEquipoE.getId());
			partido5.setEquipo2_id(segundoEquipoF.getId());
			partido5.setFase("octavos");
			partido5.setLlaveId(llave2);
			partido5 = this.guardarPartido(partido5);
			
			PartidoModel partido6 = new PartidoModel();
			partido6.setEquipo1_id(primerEquipoF.getId());
			partido6.setEquipo2_id(segundoEquipoE.getId());
			partido6.setFase("octavos");
			partido6.setLlaveId(llave4);
			partido6 = this.guardarPartido(partido6);
			
			PartidoModel partido7 = new PartidoModel();
			partido7.setEquipo1_id(primerEquipoG.getId());
			partido7.setEquipo2_id(segundoEquipoH.getId());
			partido7.setFase("octavos");
			partido7.setLlaveId(llave2);
			partido7 = this.guardarPartido(partido7);
			
			PartidoModel partido8 = new PartidoModel();
			partido8.setEquipo1_id(primerEquipoH.getId());
			partido8.setEquipo2_id(segundoEquipoG.getId());
			partido8.setFase("octavos");
			partido8.setLlaveId(llave4);
			partido8 = this.guardarPartido(partido8);		
			
			//simular los partidos
			
//			SIMULAR PARTIDO GUARDADO EN base A SU id
			PartidoModel partido1SimuladoOc = this.simularPartido(partido1.getId());
			PartidoModel partido2SimuladoOc = this.simularPartido(partido2.getId());
			PartidoModel partido3SimuladoOc = this.simularPartido(partido3.getId());
			PartidoModel partido4SimuladoOc = this.simularPartido(partido4.getId());
			PartidoModel partido5SimuladoOc = this.simularPartido(partido5.getId());
	     	PartidoModel partido6SimuladoOc = this.simularPartido(partido6.getId());
	     	PartidoModel partido7SimuladoOc = this.simularPartido(partido7.getId());
	     	PartidoModel partido8SimuladoOc = this.simularPartido(partido8.getId());
	     	
	     	partidosOctavosSimulados.add(partido1SimuladoOc);
	     	partidosOctavosSimulados.add(partido2SimuladoOc);
	     	partidosOctavosSimulados.add(partido3SimuladoOc);
	     	partidosOctavosSimulados.add(partido4SimuladoOc);
	     	partidosOctavosSimulados.add(partido5SimuladoOc);     	
	     	partidosOctavosSimulados.add(partido6SimuladoOc);
	     	partidosOctavosSimulados.add(partido7SimuladoOc);     	
	     	partidosOctavosSimulados.add(partido8SimuladoOc);	     
			
			//crear partidos de cuartos en base a los ganadores (otra funcion)						
						
			return partidosOctavosSimulados;
		}
				
		private List<PartidoModel> obtenerPorLlave(Long llaveId) {
			
			return partidoRepository.findByLlaveId(llaveId);
		}
		
		
		public List<PartidoModel>cuartos(){
			// llaves de cuartos
			Long llave1 = (long) 1;
			Long llave2 = (long) 2;
			Long llave3 = (long) 3;
			Long llave4 = (long) 4;
			// llaves de semi
			Long llave5 = (long) 5;
			Long llave6 = (long) 6;
			
			List<PartidoModel> partidosCuartos = new ArrayList<PartidoModel>();		
						
			List<PartidoModel> Grupo1Cuartos = this.obtenerPorLlave(llave1);
			Long equipoIdGanador1 = Grupo1Cuartos.get(0).getEquipo_ganador_id();
		    Long equipoIdGanador2 = Grupo1Cuartos.get(1).getEquipo_ganador_id();
		    
		    List<PartidoModel> Grupo2Cuartos = this.obtenerPorLlave(llave2);
			Long equipoIdGanador3 = Grupo2Cuartos.get(0).getEquipo_ganador_id();
		    Long equipoIdGanador4 = Grupo2Cuartos.get(1).getEquipo_ganador_id();		    
		 		  
		    List<PartidoModel> Grupo3Cuartos = this.obtenerPorLlave(llave3);
			Long equipoIdGanador5 = Grupo3Cuartos.get(0).getEquipo_ganador_id();
		    Long equipoIdGanador6 = Grupo3Cuartos.get(1).getEquipo_ganador_id();
		    
		    List<PartidoModel> Grupo4Cuartos = this.obtenerPorLlave(llave4);
			Long equipoIdGanador7 = Grupo4Cuartos.get(0).getEquipo_ganador_id();
		    Long equipoIdGanador8 = Grupo4Cuartos.get(1).getEquipo_ganador_id();					    		   
		    		    
		    // Guardar los partidos una vez linkeados
			PartidoModel partido1 = new PartidoModel();
			partido1.setEquipo1_id(equipoIdGanador1);
			partido1.setEquipo2_id(equipoIdGanador2);
			partido1.setFase("cuartos");
			partido1.setLlaveId(llave5);
			partido1 = this.guardarPartido(partido1);
									
			PartidoModel partido2 = new PartidoModel();
			partido2.setEquipo1_id(equipoIdGanador3);
			partido2.setEquipo2_id(equipoIdGanador4);
			partido2.setFase("cuartos");
			partido2.setLlaveId(llave5);
			partido2 = this.guardarPartido(partido2);
			
			PartidoModel partido3 = new PartidoModel();
			partido3.setEquipo1_id(equipoIdGanador5);
			partido3.setEquipo2_id(equipoIdGanador6);
			partido3.setFase("cuartos");
			partido3.setLlaveId(llave6);
			partido3 = this.guardarPartido(partido3);
			
			PartidoModel partido4 = new PartidoModel();
			partido4.setEquipo1_id(equipoIdGanador7);
			partido4.setEquipo2_id(equipoIdGanador8);
			partido4.setFase("cuartos");
			partido4.setLlaveId(llave6);
			partido4 = this.guardarPartido(partido4);
			
			// SIMULAR PARTIDO GUARDADO EN base A SU id
			PartidoModel partido1Cuartos = this.simularPartido(partido1.getId());
			PartidoModel partido2Cuartos = this.simularPartido(partido2.getId());
			PartidoModel partido3Cuartos = this.simularPartido(partido3.getId());
			PartidoModel partido4Cuartos = this.simularPartido(partido4.getId());
		   
			//agregar los partidos al array de cuartos de final
			partidosCuartos.add(partido1Cuartos);
			partidosCuartos.add(partido2Cuartos);
			partidosCuartos.add(partido3Cuartos);
			partidosCuartos.add(partido4Cuartos);
			
			return partidosCuartos;
		}

		public List<PartidoModel> semis() {
			// llaves de semi
			Long llave5 = (long) 5;
			Long llave6 = (long) 6;
			Long llave7 = (long) 7;
			
			List<PartidoModel> partidosSemis = new ArrayList<PartidoModel>();				
									
			List<PartidoModel> Grupo1Semis = this.obtenerPorLlave(llave5);
			Long eqIdGanador1 =Grupo1Semis.get(0).getEquipo_ganador_id();
			Long eqIdGanador2 = Grupo1Semis.get(1).getEquipo_ganador_id();
					    
			List<PartidoModel> Grupo2Semis = this.obtenerPorLlave(llave6);
			Long eqIdGanador3 = Grupo2Semis.get(0).getEquipo_ganador_id();
			Long eqIdGanador4 = Grupo2Semis.get(1).getEquipo_ganador_id();
			
			  // Guardar los partidos una vez linkeados
			PartidoModel partido1 = new PartidoModel();
			partido1.setEquipo1_id(eqIdGanador1);
			partido1.setEquipo2_id(eqIdGanador2);
			partido1.setFase("semis");
			partido1.setLlaveId(llave7);
			partido1 = this.guardarPartido(partido1);			
						
			PartidoModel partido2 = new PartidoModel();
			partido2.setEquipo1_id(eqIdGanador3);
			partido2.setEquipo2_id(eqIdGanador4);
			partido2.setFase("semis");
			partido2.setLlaveId(llave7);
			partido2 = this.guardarPartido(partido2);					

			// SIMULAR PARTIDO GUARDADO EN base A SU id
			PartidoModel partido1Semis = this.simularPartido(partido1.getId());
			PartidoModel partido2Semis = this.simularPartido(partido2.getId());			
			 
			//agregar los partidos al array de cuartos de final
			partidosSemis.add(partido1Semis);
			partidosSemis.add(partido2Semis);						
			
			return partidosSemis;
		}

		public List<PartidoModel> finales() {
			Long llave7 = (long) 7;			
			List<PartidoModel> partidosfinales = new ArrayList<PartidoModel>();													
			List<PartidoModel> Finalistas = this.obtenerPorLlave(llave7);
			Long eqIdGanador1 = Finalistas.get(0).getEquipo_ganador_id();
			Long eqIdGanador2 = Finalistas.get(1).getEquipo_ganador_id();					    
			
			  // Guardar los partidos una vez linkeados
			PartidoModel partidoFinal = new PartidoModel();
			partidoFinal.setEquipo1_id(eqIdGanador1);
			partidoFinal.setEquipo2_id(eqIdGanador2);
			partidoFinal.setFase("final");
			partidoFinal.setLlaveId(llave7);
			partidoFinal = this.guardarPartido(partidoFinal);			

			// SIMULAR PARTIDO GUARDADO EN base A SU id
			PartidoModel resultadoFinal = this.simularPartido(partidoFinal.getId());			
			 
			//agregar los partidos al array de cuartos de final
			partidosfinales.add(resultadoFinal);
					
			return partidosfinales;
		}
		
}
	
	