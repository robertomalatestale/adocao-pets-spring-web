package com.roberto.adocao_pets_spring_web.service;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import com.roberto.adocao_pets_spring_web.entity.Usuario;
import com.roberto.adocao_pets_spring_web.exceptions.RecursoNaoEncontradoException;
import com.roberto.adocao_pets_spring_web.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    private final UsuarioService usuarioService;

    public PetService(PetRepository petRepository, UsuarioService usuarioService) {
        this.petRepository = petRepository;
        this.usuarioService = usuarioService;
    }

    public List<Pet> listarPets() {
        return petRepository.findAll();
    }

    public Pet buscarPetPorId(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Pet com ID " + id + " não encontrado"));
    }

    public Pet salvarPet(Pet pet) {
        return petRepository.save(pet);
    }

    public void deletarPet(Long id) {
        if(!petRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Pet com ID " + id + " não encontrado");
        }
        petRepository.deleteById(id);
    }

    public Pet adotarPet(Long petId, Long usuarioId) {
        Pet pet = buscarPetPorId(petId);
        if(pet.getAdotante() != null){
            throw new IllegalArgumentException("O pet já está adotado");
        }
        Usuario usuario = usuarioService.buscarUsuarioPorId(usuarioId);
        pet.setAdotante(usuario);
        pet.setAdotado(true);
        return petRepository.save(pet);
    }

}
