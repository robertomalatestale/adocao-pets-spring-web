package com.roberto.adocao_pets_spring_web.entity;

import com.roberto.adocao_pets_spring_web.enums.TipoPet;
import com.roberto.adocao_pets_spring_web.enums.Sexo;
import com.roberto.adocao_pets_spring_web.exceptions.InvalidNameException;
import com.roberto.adocao_pets_spring_web.exceptions.InvalidPetAgeException;
import com.roberto.adocao_pets_spring_web.exceptions.InvalidPetWeightException;
import com.roberto.adocao_pets_spring_web.model.Endereco;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String nomeCompleto;
    @Enumerated(EnumType.STRING)
    private TipoPet tipoPet;
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    @Embedded
    private Endereco endereco;
    private double idade;
    private double peso;
    private String raca;
    private String urlFoto;
    private boolean adotado;

    protected Pet() {

    }

    public Pet(String nomeCompleto, TipoPet tipoPet, Sexo sexo, double idade, double peso, String raca){
        setNomeCompleto(nomeCompleto);
        setTipoPet(tipoPet);
        setSexo(sexo);
        setIdade(idade);
        setPeso(peso);
        setRaca(raca);
    }

    public void setNomeCompleto(String nomeCompleto) {
        if(nomeCompleto == null || nomeCompleto.trim().isEmpty()){
            throw new InvalidNameException("Deve cadastrar um nome para o pet");
        }
        for(int i = 0; i<nomeCompleto.length(); i++){
            if(!Character.isLetter(nomeCompleto.charAt(i)) && !Character.isWhitespace(nomeCompleto.charAt(i))){
                throw new InvalidNameException("Nome deve conter somente letras");
            }
        }
        int auxiliary = 0;
        for(int i =0; i<nomeCompleto.length(); i++){
            if(Character.isWhitespace(nomeCompleto.charAt(i))){
                auxiliary = i;
                break;
            }
        }
        if(auxiliary == 0){
            throw new InvalidNameException("Pet deve ter um sobrenome");
        }
        this.nomeCompleto = nomeCompleto;
    }

    public void setIdade(double idade) {
        if(idade>20){
            throw new InvalidPetAgeException("Pet não pode ter mais que 20 anos");
        }
        if(idade<=0){
            throw new InvalidPetAgeException("Pet não pode ter idade nula ou negativa");
        }
        if(idade<1){
            this.idade = (double) Math.round(idade * 10)/10.0;
        } else {
            this.idade = (double) Math.round(idade);}
    }

    public void setPeso(double peso) {
        if(peso>60 || peso<0.5){
            throw new InvalidPetWeightException("Pet deve ter entre 0,5 e 60 quilos");
        }
        this.peso = peso;
    }

    public void setRaca(String raca) {
        if(raca == null || raca.trim().isEmpty()){
            this.raca = "Não informado";
            return;
        }
        for(int i = 0; i<raca.length(); i++){
            if(!Character.isLetter(raca.charAt(i)) && !Character.isWhitespace(raca.charAt(i))){
                throw new InvalidNameException("Raça deve conter somente letras");
            }
        }
        this.raca = raca;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
