package com.roberto.adocao_pets_spring_web.entity;

import com.roberto.adocao_pets_spring_web.exceptions.InvalidNameException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;

    @Column(unique = true, nullable = false, length = 11)
    @NotBlank
    @Size(min = 11, max = 11, message = "CPF deve ter exatamente 11 números")
    @Pattern(regexp = "^[0-9]*$", message = "CPF deve conter somente números")
    private String cpf;
    @Column(unique = true, nullable = false)
    @NotBlank
    @Size(min = 3, max = 50)
    private String username;
    private String nomeCompleto;
    @Email(message = "Formato de email inválido")
    private String email;
    private String telefoneCelular;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres")
    private String senha;

    protected Usuario() {
    }

    public Usuario(String cpf, String username, String senha){
        setCpf(cpf);
        setUsername(username);
        setSenha(senha);
    }

    public void setNomeCompleto(String nomeCompleto) {
        if(nomeCompleto == null || nomeCompleto.trim().isEmpty()){
            throw new InvalidNameException("Deve cadastrar um nome");
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
            throw new InvalidNameException("Sobrenome obrigatório");
        }
        this.nomeCompleto = nomeCompleto;
    }


}