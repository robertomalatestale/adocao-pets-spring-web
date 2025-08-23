package com.roberto.adocao_pets_spring_web.service;

import com.roberto.adocao_pets_spring_web.entity.Usuario;
import com.roberto.adocao_pets_spring_web.exceptions.RecursoNaoEncontradoException;
import com.roberto.adocao_pets_spring_web.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Usuario buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado"));
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id) {
        if(!usuarioRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Usuário com ID " + id + " não encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
