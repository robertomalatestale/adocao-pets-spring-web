package com.roberto.adocao_pets_spring_web.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void deveLancarExcecaoSeCPFNaoTiver11Digitos(){
        Usuario usuario = new Usuario();
        usuario.setCpf("123456789011");

        assertEquals("1234567890", usuario.getCpf());
    }
}