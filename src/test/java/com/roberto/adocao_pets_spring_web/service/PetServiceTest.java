package com.roberto.adocao_pets_spring_web.service;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import com.roberto.adocao_pets_spring_web.entity.Usuario;
import com.roberto.adocao_pets_spring_web.exceptions.RecursoNaoEncontradoException;
import com.roberto.adocao_pets_spring_web.repository.PetRepository;
import com.roberto.adocao_pets_spring_web.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

class PetServiceTest {
    @Mock
    private PetRepository petRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private PetService petService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void deveLancarExcecaoSeBuscarPetComIdInvalido() {
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> petService.buscarPetPorId(20L));
        assertEquals("Pet com ID 20 não encontrado", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeTentarDeletarPetComIdInvalido() {
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> petService.deletarPet(20L));
        assertEquals("Pet com ID 20 não encontrado", exception.getMessage());
    }



    @Test
    void deveAdotarPetComSucesso() {
        Pet pet = new Pet();
        Usuario usuario = new Usuario();
        ReflectionTestUtils.setField(pet, "id", 1L);
        ReflectionTestUtils.setField(usuario, "id", 2L);

        Mockito.when(petRepository.findById(eq(1L))).thenReturn(Optional.of(pet));
        Mockito.when(usuarioService.buscarUsuarioPorId(2L)).thenReturn(usuario);
        Mockito.when(petRepository.save(any(Pet.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Pet resultado = petService.adotarPet(pet.getId(),usuario.getId());

        assertEquals(pet, resultado);
        assertEquals(usuario, resultado.getAdotante());
        assertNotNull(pet.getAdotante());
        assertTrue(pet.isAdotado());

        Mockito.verify(petRepository, times(1)).save(pet);
        Mockito.verify(usuarioService, times(1)).buscarUsuarioPorId(2L);
    }

    @Test
    void deveLancarExcecaoSeTentarAdotarComPetJaAdotado() {
        Pet pet = new Pet();
        Usuario usuario = new Usuario();
        ReflectionTestUtils.setField(pet, "id", 1L);
        ReflectionTestUtils.setField(usuario, "id", 2L);
        pet.setAdotante(usuario);

        Mockito.when(petRepository.findById(eq(1L))).thenReturn(Optional.of(pet));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> petService.adotarPet(pet.getId(),usuario.getId()));

        assertEquals("O pet já está adotado", exception.getMessage());
    }



}