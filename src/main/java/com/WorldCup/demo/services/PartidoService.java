package com.WorldCup.demo.services;

import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.WorldCup.demo.models.PartidoModel;
import com.WorldCup.demo.repositories.EquipoRepository;
import com.WorldCup.demo.repositories.PartidoRepository;



@Service
public class PartidoService {
	
	@Autowired
	PartidoRepository partidoRepository;
	@Autowired
	EquipoRepository equipoRepository;
	
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
	
	public String simularPartido(String resultado) {
		
		int puntaje1=0;
		int puntaje2=0;
		String	resul="";
		String puntos= "";
			
		int goles_equipo1= (int)(Math.random()*5);
		int goles_equipo2= (int)(Math.random()*5);
		
		if (goles_equipo2==goles_equipo1){
				puntaje1+=1;
				puntaje2+=1;
				resul= " Empate";
				puntos= " Ambos equipos suman " +String.valueOf(puntaje1)+ " punto" ;
		}
		else if (goles_equipo1>goles_equipo2) {
			puntaje1+=3;
			
			resul= " Gana el equipo 1";
			puntos= " Sumando "+String.valueOf(puntaje1)+" puntos";
		}else {
			puntaje2+=3;
			resul= " Gana el equipo 2";
			puntos= " Sumando "+String.valueOf(puntaje2)+" puntos";
		}
		
		resultado = (String.valueOf(goles_equipo1)+" - "+String.valueOf(goles_equipo2)+resul+ puntos
		);
		return resultado;
			
	}
		
	}
		

	
	