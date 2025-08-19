package com.roberto.adocao_pets_spring_web.controller;

import com.roberto.adocao_pets_spring_web.entity.Usuario;
import com.roberto.adocao_pets_spring_web.security.JwtUtil;
import com.roberto.adocao_pets_spring_web.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String,String> request) {
        Usuario usuario = usuarioService.registrarUsuario(request.get("cpf"), request.get("senha"));
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> request) {
        Optional<Usuario> usuario = usuarioService.buscarUsuarioPorCPF(request.get("cpf"));
        if (usuario.isPresent() && usuario.get().getSenha().equals(request.get("senha"))) {
            String token = JwtUtil.generateToken(usuario.get().getCpf());
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }


}
