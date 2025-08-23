package com.roberto.adocao_pets_spring_web.entity;

import com.roberto.adocao_pets_spring_web.exceptions.InvalidNameException;
import com.roberto.adocao_pets_spring_web.exceptions.InvalidPetAgeException;
import com.roberto.adocao_pets_spring_web.exceptions.InvalidPetWeightException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PetTest {
    Pet pet;
    @BeforeEach
    void setUp() {
        pet = new Pet();
    }

    @Test
    void deveLancarExcecaoQuandoNomeForVazio(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> pet.setNomeCompleto("   "));
        assertEquals("Nome inválido: Deve cadastrar um nome para o pet", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoNomeForNulo(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> pet.setNomeCompleto(null));
        assertEquals("Nome inválido: Deve cadastrar um nome para o pet", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNomeConterNumeros(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> pet.setNomeCompleto("123456789"));
        assertEquals("Nome inválido: Nome deve conter somente letras", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeNomeConterCaracterEspecial(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> pet.setNomeCompleto("Rex Jo@o"));
        assertEquals("Nome inválido: Nome deve conter somente letras", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSePetNaoTiverSobrenome(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> pet.setNomeCompleto("Rex"));
        assertEquals("Nome inválido: Pet deve ter um sobrenome", exception.getMessage());
    }

    @Test
    void deveSettarNomeCompletoCorretamente(){
        pet.setNomeCompleto("Rex da Silva");
        assertEquals("Rex da Silva", pet.getNomeCompleto());
    }

    @Test
    void deveLancarExcecaoSeIdadeDoPetForSuperiorA20Anos(){
        InvalidPetAgeException exception = assertThrows(InvalidPetAgeException.class, () -> pet.setIdade(20.01));
        assertEquals("Idade inválida: Pet não pode ter mais que 20 anos", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeIdadeDoPetForZeroOuNegativa(){
        InvalidPetAgeException exception = assertThrows(InvalidPetAgeException.class, () -> pet.setIdade(0));
        assertEquals("Idade inválida: Pet não pode ter idade nula ou negativa", exception.getMessage());
    }

    @Test
    void deveArredondarIdadeCorretamenteSePetComIdadeInferiorA1Ano(){
        pet.setIdade(0.88);
        assertEquals(0.9, pet.getIdade());
    }

    @Test
    void deveArredondarESettarIdadeDoPetCorretamente(){
        pet.setIdade(1.001);
        assertEquals(1, pet.getIdade());
    }

    @Test
    void deveLancarExcecaoSePesoDoPetSuperiorA60(){
        InvalidPetWeightException exception = assertThrows(InvalidPetWeightException.class, () -> pet.setPeso(60.01));
        assertEquals("Peso inválido: Pet deve ter entre 0,5 e 60 quilos",exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSePesoDoPetInferiorA05(){
        InvalidPetWeightException exception = assertThrows(InvalidPetWeightException.class, () -> pet.setPeso(0.499));
        assertEquals("Peso inválido: Pet deve ter entre 0,5 e 60 quilos",exception.getMessage());
    }

    @Test
    void deveSettarPesoDoPetCorretamente(){
        pet.setPeso(0.501);
        assertEquals(0.501, pet.getPeso());
    }

    @Test
    void deveSettarRacaDoPetComoNaoInformadoCasoNaoForPreenchido(){
        pet.setRaca("   ");
        assertEquals("Não informado", pet.getRaca());
    }

    @Test
    void deveSettarRacaDoPetComoNaoInformadoCasoForNulo(){
        pet.setRaca(null);
        assertEquals("Não informado", pet.getRaca());
    }

    @Test
    void deveLancarExcecaoSeRacaConterNumeros(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> pet.setRaca("Buldogue Fr4nces"));
        assertEquals("Nome inválido: Raça deve conter somente letras", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoSeRacaConterCaracterEspecial(){
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> pet.setRaca("Buldogue Fr@nces"));
        assertEquals("Nome inválido: Raça deve conter somente letras", exception.getMessage());
    }

    @Test
    void deveSettarRacaDoPetCorretamente(){
        pet.setRaca("Buldogue Francês");
        assertEquals("Buldogue Francês", pet.getRaca());
    }
}