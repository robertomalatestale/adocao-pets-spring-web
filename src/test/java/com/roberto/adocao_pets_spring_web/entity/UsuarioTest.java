package com.roberto.adocao_pets_spring_web.entity;

import com.roberto.adocao_pets_spring_web.exceptions.InvalidNameException;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class UsuarioTest {
    Usuario usuario;
    @BeforeEach
    void setUp() {
        usuario = new Usuario();
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazio(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> usuario.setNomeCompleto("   "));
        assertEquals("Nome inválido: Deve cadastrar um nome", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> usuario.setNomeCompleto(null));
        assertEquals("Nome inválido: Deve cadastrar um nome", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNomeConterNumeros(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> usuario.setNomeCompleto("123456789"));
        assertEquals("Nome inválido: Nome deve conter somente letras", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNomeConterCaracterEspecial(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> usuario.setNomeCompleto("Jo@o Silva"));
        assertEquals("Nome inválido: Nome deve conter somente letras", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeUsuarioNaoTiverSobrenome(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> usuario.setNomeCompleto("João"));
        assertEquals("Nome inválido: Sobrenome obrigatório", exception.getMessage());
    }

    @Test
    void deveSettarNomeCompletoCorretamente(){
        usuario.setNomeCompleto("João da Silva");
        assertEquals("João da Silva", usuario.getNomeCompleto());
    }

}