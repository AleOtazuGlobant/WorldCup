package com.WorldCup.demo.services;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.repositories.EquipoRepository;
import com.WorldCup.demo.repositories.GrupoRepository;


@SuppressWarnings("unused")
public class EquipoServiceTest {
		
		@InjectMocks
		private EquipoService equipoService;
		
		@Mock
		private EquipoRepository equipoRepository;
		
		@Mock
		private GrupoRepository grupoRepository;
		
		@Mock
		private JugadorService jugadorService;
		//fail("Not yet implemented");
		
		@BeforeEach
		public void  iniciar () {
			
			MockitoAnnotations.openMocks(this);
		}
		
		@Test
		public void  testCrearEquipo() {
			
			EquipoModel equipoModel = new EquipoModel();
			
			//Mockito.when(EquipoService.existeEquipo(Mockito.anyString())).thenReturn(New EquipoModel());
		};
	}


