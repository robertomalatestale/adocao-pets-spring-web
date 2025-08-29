package com.roberto.adocao_pets_spring_web.service;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import com.roberto.adocao_pets_spring_web.entity.Usuario;
import com.roberto.adocao_pets_spring_web.exceptions.RecursoNaoEncontradoException;
import com.roberto.adocao_pets_spring_web.repository.PetRepository;
import com.roberto.adocao_pets_spring_web.repository.UsuarioRepository;
import com.roberto.adocao_pets_spring_web.utils.PetSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    @Test
    void deveBuscarApenasPeloAtributoSeValorForNull(){
        when(petRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        try(MockedStatic<PetSpecification> petSpecificationMock = Mockito.mockStatic(PetSpecification.class)) {
            petSpecificationMock.when(() -> PetSpecification.hasOnlyAttribute("nomeCompleto"))
                    .thenReturn(null);

            petService.buscarPorUmAtributoDefinidoPeloUsuario("nomeCompleto", null);
            petSpecificationMock.verify(() -> PetSpecification.hasOnlyAttribute("nomeCompleto"), times(1));
            petSpecificationMock.verify(() -> PetSpecification.hasAttributeAndValue("nomeCompleto", null), times(0));
        }
    }

    @Test
    void deveBuscarApenasPeloAtributoSeValorForVazio(){
        when(petRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        try(MockedStatic<PetSpecification> petSpecificationMock = Mockito.mockStatic(PetSpecification.class)) {
            petSpecificationMock.when(() -> PetSpecification.hasOnlyAttribute("nomeCompleto"))
                    .thenReturn(null);

            petService.buscarPorUmAtributoDefinidoPeloUsuario("nomeCompleto", "   ");
            petSpecificationMock.verify(() -> PetSpecification.hasOnlyAttribute("nomeCompleto"), times(1));
            petSpecificationMock.verify(() -> PetSpecification.hasAttributeAndValue("nomeCompleto", "   "), times(0));
        }
    }

    @Test
    void deveBuscarPorAtributoEValorCorretamente(){
        when(petRepository.findAll(any(Specification.class))).thenReturn(Collections.emptyList());

        try(MockedStatic<PetSpecification> petSpecificationMock = Mockito.mockStatic(PetSpecification.class)) {
            petSpecificationMock.when(() -> PetSpecification.hasAttributeAndValue("nomeCompleto", "Rex"))
                    .thenReturn(null);

            petService.buscarPorUmAtributoDefinidoPeloUsuario("nomeCompleto", "Rex");
            petSpecificationMock.verify(() -> PetSpecification.hasAttributeAndValue("nomeCompleto", "Rex"), times(1));
            petSpecificationMock.verify(() -> PetSpecification.hasOnlyAttribute("nomeCompleto"), times(0));
        }
    }

}