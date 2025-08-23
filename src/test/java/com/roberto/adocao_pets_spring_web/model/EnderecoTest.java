package com.roberto.adocao_pets_spring_web.model;

import com.roberto.adocao_pets_spring_web.exceptions.InvalidNameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnderecoTest {
    Endereco endereco;

    @BeforeEach
    void setUp() {
        endereco = new Endereco();
    }

    @Test
    void deveLancarExcecaoSeLogradouroVazio() {
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> endereco.setLogradouro("  "));
        assertEquals("Nome inválido: Campo logradouro não pode ficar vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeLogradouroNulo() {
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> endereco.setLogradouro(null));
        assertEquals("Nome inválido: Campo logradouro não pode ficar vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNomeCidadeVazio() {
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> endereco.setCidade("  "));
        assertEquals("Nome inválido: Campo cidade não pode ficar vazio", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNomeCidadeNulo() {
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> endereco.setCidade(null));
        assertEquals("Nome inválido: Campo cidade não pode ficar vazio", exception.getMessage());
    }


}