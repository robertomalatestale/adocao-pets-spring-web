package com.roberto.adocao_pets_spring_web.repository;

import com.roberto.adocao_pets_spring_web.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {

}
