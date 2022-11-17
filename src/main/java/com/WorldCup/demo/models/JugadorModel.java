package com.WorldCup.demo.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name= "jugadores")
public class JugadorModel {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	@NotEmpty(message = "El campo nombre no puede estar vacio")
	private String nombre;
	
	@NotEmpty (message = "El campo apellido no puede estar vacio")
	private String apellido;
	
	@NotEmpty(message = "El campo pais no puede estar vacio")
	private String pais;
	
	@NotEmpty
	@Size(min = 9, message = "Pasaporte debe tener al menos 9 caracteres")
	private String pasaporte;
	 
	 
	public JugadorModel(Long id, @NotEmpty(message = "El campo nombre no puede estar vacio") String nombre,
			@NotEmpty(message = "El campo apellido no puede estar vacio") String apellido,
			@NotEmpty(message = "El campo pais no puede estar vacio") String pais,
			@NotEmpty @Size(min = 9, message = "Pasaporte debe tener al menos 9 caracteres") String pasaporte) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.pais = pais;
		this.pasaporte = pasaporte;
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
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getPasaporte() {
		return pasaporte;
	}
	public void setPasaporte(String pasaporte) {
		this.pasaporte = pasaporte;
	}
 
 
}
