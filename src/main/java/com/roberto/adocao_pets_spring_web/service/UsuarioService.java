package com.roberto.adocao_pets_spring_web.service;

import com.roberto.adocao_pets_spring_web.entity.Usuario;
import com.roberto.adocao_pets_spring_web.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario registrarUsuario(String cpf, String senha){
        String senhaCriptografada = passwordEncoder.encode(senha);
        Usuario usuario = new Usuario(cpf, senhaCriptografada);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarUsuarioPorCPF(String cpf){
        return usuarioRepository.findByCpf(cpf);
    }
}
