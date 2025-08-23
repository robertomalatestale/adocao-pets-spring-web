package com.roberto.adocao_pets_spring_web.controller;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import com.roberto.adocao_pets_spring_web.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> listarPets() {
        return petService.listarPets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPetPorId(@PathVariable Long id) {
        Pet pet = petService.buscarPetPorId(id);
        return ResponseEntity.ok(pet);
    }

    @PostMapping
    public ResponseEntity<Pet> criarPet(@RequestBody Pet pet) {
        Pet novoPet = petService.salvarPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerPet(@PathVariable Long id) {
        petService.deletarPet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/disponiveis")
    public ResponseEntity<List<Pet>> listarPetsDisponiveisParaAdocao() {
        List<Pet> petsDisponiveisParaAdocao = petService.listarPetsSemAdotantes();
        return ResponseEntity.ok(petsDisponiveisParaAdocao);
    }
}
