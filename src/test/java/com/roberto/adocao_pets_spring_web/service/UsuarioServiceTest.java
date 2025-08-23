package com.roberto.adocao_pets_spring_web.service;

import com.roberto.adocao_pets_spring_web.exceptions.RecursoNaoEncontradoException;
import com.roberto.adocao_pets_spring_web.repository.UsuarioRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mock.*;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

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
    void deveLancarExcecaoSeBuscarUsuarioComIdInvalido() {
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> usuarioService.buscarUsuarioPorId(20L));
        assertEquals("Usuário com ID 20 não encontrado", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeTentarDeletarUsuarioComIdInvalido() {
        RecursoNaoEncontradoException exception = assertThrows(RecursoNaoEncontradoException.class, () -> usuarioService.deletarUsuario(20L));
        assertEquals("Usuário com ID 20 não encontrado", exception.getMessage());
    }


}