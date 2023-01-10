package com.WorldCup.demo.services;

import com.WorldCup.demo.models.EquipoModel;
import com.WorldCup.demo.models.GrupoModel;
import com.WorldCup.demo.models.JugadorModel;
import com.WorldCup.demo.repositories.EquipoRepository;
import com.WorldCup.demo.repositories.GrupoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.dao.OptimisticLockingFailureException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EquipoServiceTest {

    @InjectMocks
    private EquipoService equipoServiceUnderTest;

    @BeforeEach
    void setUp() {
        equipoServiceUnderTest = new EquipoService();
        equipoServiceUnderTest.equipoRepository = mock(EquipoRepository.class);
        equipoServiceUnderTest.grupoRepository = mock(GrupoRepository.class);
        equipoServiceUnderTest.jugadorService = mock(JugadorService.class);
    }


    @Test
    void testObtenerEquipos() {
        // Arrange
        // Configure EquipoRepository.findAll(...).
        final EquipoModel equipo = new EquipoModel(0L, "pais", 0,
                List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")),
                new GrupoModel(0L, "nombre", false, List.of()));

        final ArrayList<EquipoModel> equipoModels = new ArrayList<>();
        equipoModels.add(equipo);

        when(equipoServiceUnderTest.equipoRepository.findAll()).thenReturn(equipoModels);
        //Act
        final ArrayList<EquipoModel> result = (ArrayList<EquipoModel>) equipoServiceUnderTest.obtenerEquipos();

        // Assert
        assertThat(result).hasSize(1);
    }


    @Test
    void testGuardarEquipo() {
        // Arrange
        // setup EquipoRepository.save(...).
        final EquipoModel equipoModel = new EquipoModel(1L, "Argentina", 7,
                List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")),
                new GrupoModel(0L, "nombre", false, List.of()));

        when(equipoServiceUnderTest.equipoRepository.save(any(EquipoModel.class))).thenReturn(equipoModel);

        // Act
        final EquipoModel result = equipoServiceUnderTest.guardarEquipo(equipoModel);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getPais()).isEqualTo("Argentina");

    }

    @Test
    void testGuardarEquipo_EquipoRepositoryThrowsOptimisticLockingFailureException() {
        // Arrange
        // Setup
        final EquipoModel equipo = new EquipoModel(0L, "pais", 0,
                List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")),
                new GrupoModel(0L, "nombre", false, List.of()));
        when(equipoServiceUnderTest.equipoRepository.save(any(EquipoModel.class)))
                .thenThrow(OptimisticLockingFailureException.class);

        // Run the test
        assertThatThrownBy(() -> equipoServiceUnderTest.guardarEquipo(equipo))
                .isInstanceOf(OptimisticLockingFailureException.class);
    }

    @Test
    void testObtenerPorId() {
        // Arrange
        // Configure EquipoRepository.findById(...).
        final Optional<EquipoModel> equipoModel = Optional.of(new EquipoModel(0L, "pais", 0,
                List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")),
                new GrupoModel(0L, "nombre", false, List.of())));
        when(equipoServiceUnderTest.equipoRepository.findById(0L)).thenReturn(equipoModel);

        // Run the test
        @SuppressWarnings("unused")
		final Optional<EquipoModel> result = equipoServiceUnderTest.obtenerPorId(0L);

        // Verify the results
    }

    @Test
    void testObtenerPorId_EquipoRepositoryReturnsAbsent() {
        // Setup
        when(equipoServiceUnderTest.equipoRepository.findById(0L)).thenReturn(Optional.empty());

        // Act
        final Optional<EquipoModel> result = equipoServiceUnderTest.obtenerPorId(0L);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testActualizarEquipo() {
        // Arrange
        final EquipoModel equipo = new EquipoModel(0L, "pais", 0,
                List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")),
                new GrupoModel(0L, "nombre", false, List.of()));

        // Configure EquipoRepository.findById(...).
        final Optional<EquipoModel> equipoModel = Optional.of(new EquipoModel(0L, "pais", 0,
                List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")),
                new GrupoModel(0L, "nombre", false, List.of())));
        when(equipoServiceUnderTest.equipoRepository.findById(0L)).thenReturn(equipoModel);

        // Configure GrupoRepository.findById(...).
        final Optional<GrupoModel> grupoModel = Optional.of(new GrupoModel(0L, "nombre", false,
                List.of(new EquipoModel(0L, "pais", 0,
                        List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")), null))));
        when(equipoServiceUnderTest.grupoRepository.findById(0L)).thenReturn(grupoModel);

        // Configure EquipoRepository.save(...).
        final EquipoModel equipoModel1 = new EquipoModel(0L, "pais", 0,
                List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")),
                new GrupoModel(0L, "nombre", false, List.of()));
        when(equipoServiceUnderTest.equipoRepository.save(any(EquipoModel.class))).thenReturn(equipoModel1);

        // Run the test
        equipoServiceUnderTest.actualizarEquipo(0L, equipo);

        // Verify the results
        verify(equipoServiceUnderTest.equipoRepository).save(any(EquipoModel.class));
    }



    @Test
    void testObtenerEquipoPorPais() {
        // Setup
        // Configure EquipoRepository.findByPais(...).
        final EquipoModel equipoModel = new EquipoModel(0L, "Uruguay", 0,
                List.of(new JugadorModel(null, 0L, "nombre", "apellido", "pais", "pasaporte")),
                new GrupoModel(0L, "nombre", false, List.of()));
        when(equipoServiceUnderTest.equipoRepository.findByPais("pais")).thenReturn(equipoModel);

        // Run the test
        final EquipoModel result = equipoServiceUnderTest.obtenerEquipoPorPais("pais");

        // Verify the results
        assertThat(result.getPais()).isEqualTo("Uruguay");
    }


    @Test
    void testExisteEquipo() {
        // Arrange
        when(equipoServiceUnderTest.equipoRepository.existsByPais("pais")).thenReturn(false);

        // Run the test
        final boolean result = equipoServiceUnderTest.existeEquipo("pais");

        // Verify the results
        assertThat(result).isFalse();
    }

    @Test
    void testExisteEquipoPorId() {
        // Arrange
        when(equipoServiceUnderTest.equipoRepository.existsById(0L)).thenReturn(false);

        // Run the test
        final boolean result = equipoServiceUnderTest.existeEquipoPorId(0L);

        // Verify the results
        assertThat(result).isFalse();
    }
}
