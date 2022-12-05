package com.WorldCup.demo.models;




import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "grupos")
public class GrupoModel {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(unique = true, nullable = false)
	    private Long id;
	 	
	 	private String nombre;
	 	
		private boolean jugado;
		
	 	@JsonIgnore
	 
	 	@OneToMany(mappedBy ="grupo", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	 	private List<EquipoModel> equipos;
	 	
		public boolean isJugado() {
			return jugado;
		}

		public void setJugado(boolean jugado) {
			this.jugado = jugado;
		}

		public GrupoModel(Long id, String nombre, boolean jugado, List<EquipoModel> equipos) {
			super();
			this.id = id;
			this.nombre = nombre;
			this.jugado = jugado;
			this.equipos = equipos;
		}

		public GrupoModel() {
			super();
		}

	 	public List<EquipoModel> getEquipos() {
			return equipos;
		}
		
		public void setEquipos(List<EquipoModel> equipos) {
			this.equipos = equipos;
		}
		

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}	 	
		
	
}
