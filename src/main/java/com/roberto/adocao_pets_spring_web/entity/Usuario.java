package com.roberto.adocao_pets_spring_web.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false, length = 11)
    private String cpf;
    private String nomeCompleto;
    private String email;
    private String telefoneCelular;

    @Column(nullable = false)
    private String senha;

    public Usuario() {
    }

    public Usuario(String cpf, String senha){
        this.cpf = cpf;
        this.senha = senha;
    }
}
