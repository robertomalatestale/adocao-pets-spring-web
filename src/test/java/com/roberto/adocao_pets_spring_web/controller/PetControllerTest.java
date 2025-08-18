package com.roberto.adocao_pets_spring_web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roberto.adocao_pets_spring_web.entity.Pet;
import com.roberto.adocao_pets_spring_web.enums.Sexo;
import com.roberto.adocao_pets_spring_web.enums.TipoPet;
import com.roberto.adocao_pets_spring_web.service.PetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PetService petService;


    @Test
    @DisplayName("Deve listar todos os pets atraves de um GET, esperando código 200") //@DisplayName é algo que estou descobrindo ainda. É provável que idealmente tenha que usar para todos os testes, vou estudar sobre, caso seja futuramente eu refatoro.
    void deveListarTodosPets() throws Exception {
        when(petService.listarPets()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/pets"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(petService,times(1)).listarPets();
    }

    @Test
    void deveBuscarPetPorId() throws Exception {
        Pet pet = new Pet("Rex da Silva", TipoPet.CACHORRO, Sexo.MACHO,15,10.50,"Lhasa Apso");
        pet.setId(1L);

        when(petService.buscarPetPorId(eq(1L))).thenReturn(Optional.of(pet));

        mockMvc.perform(get("/api/pets/" + pet.getId())
                .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeCompleto").value("Rex da Silva"));

        verify(petService,times(1)).buscarPetPorId(eq(1L));
    }

    @Test
    void deveCriarNovoPet() throws Exception {

        Pet pet = new Pet("Rex da Silva", TipoPet.CACHORRO, Sexo.MACHO,15,10.50,"Lhasa Apso");
        String jsonPet = objectMapper.writeValueAsString(pet);

        Pet petRetornado = new Pet("Rex da Silva", TipoPet.CACHORRO, Sexo.MACHO,15,10.50,"Lhasa Apso");
        petRetornado.setId(1L);

        when(petService.salvarPet(any(Pet.class))).thenReturn(petRetornado);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/pets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonPet)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nomeCompleto").value("Rex da Silva"));

        verify(petService, times(1)).salvarPet(pet);
    }

    @Test
    void deveDeletarPetPorId() throws Exception {
        Long idExistente = 1L;

        doNothing().when(petService).deletarPet(eq(idExistente));
        mockMvc.perform(delete("/api/pets/" + idExistente))
                .andExpect(status().isNoContent());

        verify(petService, times(1)).deletarPet(idExistente);
    }

}