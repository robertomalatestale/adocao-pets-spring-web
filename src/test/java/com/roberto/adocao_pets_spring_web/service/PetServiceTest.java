package com.roberto.adocao_pets_spring_web.service;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import com.roberto.adocao_pets_spring_web.enums.Sexo;
import com.roberto.adocao_pets_spring_web.enums.TipoPet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PetServiceTest {

    @Autowired
    private PetService petService;


    @Test
    void deveListarTodosPetsCorretamente() {

    }


    @Test
    void deveBuscarPetPorIdCorretamente() {

    }

    @Test
    @DisplayName("Deve salvar um pet com sucesso no banco de dados") //@DisplayName é algo que estou descobrindo ainda. É provável que idealmente tenha que usar para todos os testes, vou estudar sobre, caso seja futuramente eu refatoro.
    void deveAdicionarPetCorretamente() {
        Pet pet = new Pet("Rex da Silva", TipoPet.CACHORRO, Sexo.MACHO,15,10.50,"Lhasa Apso");

        Pet petSalvoNoRepo = petService.salvarPet(pet);

        System.out.println(petSalvoNoRepo.getId());
        assertEquals(1L,petSalvoNoRepo.getId());
    }
}