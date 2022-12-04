package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.models.PartidoModel;
import com.WorldCup.demo.repositories.EquipoRepository;
import com.WorldCup.demo.repositories.PartidoRepository;



@Service
public class PartidoService {
	
	@Autowired
	PartidoRepository partidoRepository;
	@Autowired
	EquipoRepository equipoRepository;
	@Autowired
	EquipoService equipoService;
	
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
		PartidoModel encuentro= partidoRepository.findById(id).get();
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
		PartidoModel match= partidoRepository.findById(id).get();
		Optional<EquipoModel> equipoUno= equipoService.obtenerPorId(match.getEquipo1_id());
		Optional<EquipoModel> equipoDos= equipoService.obtenerPorId(match.getEquipo2_id());
		EquipoModel equipo1 = equipoUno.get();
		EquipoModel equipo2 = equipoDos.get();
		String equipo1Pais = equipoUno.get().getPais();
		String equipo2Pais = equipoDos.get().getPais();
		int puntaje1=0;
		int puntaje2=0;
		String	resul="";
		
			
		int goles_equipo1= (int)(Math.random()*5);
		int goles_equipo2= (int)(Math.random()*5);
		
		if (goles_equipo2==goles_equipo1){
				puntaje1+=1;
				puntaje2+=1;
				resul = "Empate";
				
		}
		else if (goles_equipo1>goles_equipo2) {
			puntaje1+=3;
			
			resul = "Gana " + equipo1Pais;
			
		}else {
			puntaje2+=3;
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
		System.out.println("Resultado:  "+ resul);
		System.out.println("Puntos equipo 1:  "+ puntaje1);
		System.out.println("Puntos equipo 2:  "+ puntaje2);
		
		return match;
			
	}
		
	}
		

	
	