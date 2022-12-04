package com.WorldCup.demo.models;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;






@Entity
@Table(name= "equipos")
public class EquipoModel {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(unique = true, nullable = false)
	    private Long id;
	 	
	 	@NotEmpty(message = "El campo pais no puede estar vacio")
	    private String pais;
	 	
	    private int puntos;
	    
	    @OneToMany(mappedBy="equipo", cascade = CascadeType.ALL)
	    private List<JugadorModel> jugadores;
	    
	    @OneToOne(cascade = CascadeType.ALL, optional = true)
	    @JoinColumn(name= "grupo_id")
	    private GrupoModel grupo;
	   	    	   
			   
		public EquipoModel() {
			super();
		}

		public EquipoModel(Long id, @NotEmpty(message = "El campo pais no puede estar vacio") String pais, int puntos,
				List<JugadorModel> jugadores, GrupoModel grupo) {
			super();
			this.id = id;
			this.pais = pais;
			this.puntos = puntos;
			this.jugadores = jugadores;
			this.grupo = grupo;
		}

		public Long getId() {
			return id;
		}


		public GrupoModel getGrupo() {
			return grupo;
		}

		public void setGrupo(GrupoModel grupo) {
			this.grupo = grupo;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getPais() {
			return pais;
		}

		public void setPais(String pais) {
			this.pais = pais;
		}
  
		public void setJugadores(List<JugadorModel> jugPorPais) {
			// TODO Auto-generated method stub
			this.jugadores= jugPorPais;
			for(JugadorModel jugador: jugPorPais) {
				jugador.setEquipo(this);
			}
		}

		public List<JugadorModel> getJugadores() {
			return jugadores;
		}
	   public int getCantJugadores() {
		  return jugadores.size();
		   
	   }

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
}
