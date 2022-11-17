package com.WorldCup.demo.models;


import javax.persistence.*;

@Entity
@Table(name= "equipos")
public class EquipoModel {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(unique = true, nullable = false)
	    private Long id;

	    private String pais;

		public Long getId() {
			return id;
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
	   
	
}
