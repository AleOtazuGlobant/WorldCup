package com.WorldCup.demo.models;

import javax.persistence.*;

@Entity
@Table(name= "partidos")
public class PartidoModel {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	private Long equipo1_id;
	private Long equipo2_id;
	private int goles_equipo1;
	private int goles_equipo2;
	private String resultado;
	private String nombre_equipo1;
	private String nombre_equipo2;
	
	
	
	public PartidoModel() {
		super();
	}



	public PartidoModel(Long id, Long equipo1_id, Long equipo2_id, int goles_equipo1, int goles_equipo2,
			String resultado) {
		super();
		this.id = id;
		this.equipo1_id = equipo1_id;
		this.equipo2_id = equipo2_id;
		this.goles_equipo1 = goles_equipo1;
		this.goles_equipo2 = goles_equipo2;
		this.resultado = resultado;
		
	}



	public PartidoModel(String nombre_equipo1, String nombre_equipo2) {
		super();
		this.nombre_equipo1 = nombre_equipo1;
		this.nombre_equipo2 = nombre_equipo2;
	}



	public String getNombre_equipo1() {
		return nombre_equipo1;
	}



	public void setNombre_equipo1(String nombre_equipo1) {
		this.nombre_equipo1 = nombre_equipo1;
	}



	public String getNombre_equipo2() {
		return nombre_equipo2;
	}



	public void setNombre_equipo2(String nombre_equipo2) {
		this.nombre_equipo2 = nombre_equipo2;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getEquipo1_id() {
		return equipo1_id;
	}



	public void setEquipo1_id(Long equipo1_id) {
		this.equipo1_id = equipo1_id;
	}



	public Long getEquipo2_id() {
		return equipo2_id;
	}



	public void setEquipo2_id(Long equipo2_id) {
		this.equipo2_id = equipo2_id;
	}



	public int getGoles_equipo1() {
		return goles_equipo1;
	}



	public void setGoles_equipo1(int goles_equipo1) {
		this.goles_equipo1 = goles_equipo1;
	}



	public int getGoles_equipo2() {
		return goles_equipo2;
	}



	public void setGoles_equipo2(int goles_equipo2) {
		this.goles_equipo2 = goles_equipo2;
	}



	public String getResultado() {
		return resultado;
	}



	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	
}