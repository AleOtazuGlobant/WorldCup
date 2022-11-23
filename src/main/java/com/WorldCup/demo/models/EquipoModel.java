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
	    
	 
	    @OneToMany(mappedBy="equipo", cascade = CascadeType.ALL)
	    private List<JugadorModel> jugadores;
	 		
	    
		public Long getId() {
			return id;
		}

		public EquipoModel() {
			super();
		}

		public EquipoModel(Long id, @NotEmpty(message = "El campo pais no puede estar vacio") String pais,
				List<JugadorModel> jugadores) {
			super();
			this.id = id;
			this.pais = pais;
			this.jugadores = jugadores;
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
	
}
