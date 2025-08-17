package com.roberto.adocao_pets_spring_web.controller;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import com.roberto.adocao_pets_spring_web.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Pet> buscarPetPorId(@PathVariable Long id) {
        Optional<Pet> petOptional = petService.buscarPetPorId(id);
        return petOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
}
