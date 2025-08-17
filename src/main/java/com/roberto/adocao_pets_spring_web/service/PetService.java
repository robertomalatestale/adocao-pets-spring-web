package com.roberto.adocao_pets_spring_web.service;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import com.roberto.adocao_pets_spring_web.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> listarPets() {
        return petRepository.findAll();
    }

    public Optional<Pet> buscarPetPorId(Long id) {
        return petRepository.findById(id);
    }

    public Pet salvarPet(Pet pet) {
        return petRepository.save(pet);
    }

    public void deletarPet(Long id) {
        petRepository.deleteById(id);
    }
}
