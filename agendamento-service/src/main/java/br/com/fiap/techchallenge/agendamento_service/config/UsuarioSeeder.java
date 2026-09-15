package br.com.fiap.techchallenge.agendamento_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fiap.techchallenge.agendamento_service.usuario.Perfil;
import br.com.fiap.techchallenge.agendamento_service.usuario.Usuario;
import br.com.fiap.techchallenge.agendamento_service.usuario.UsuarioRepository;

@Component
@Order(1)
public class UsuarioSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioSeeder(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        criarUsuario("medico", Perfil.MEDICO);
        criarUsuario("enfermeiro", Perfil.ENFERMEIRO);
        criarUsuario("paciente", Perfil.PACIENTE);
        criarUsuario("paciente2", Perfil.PACIENTE);
    }

    private void criarUsuario(String username, Perfil perfil) {
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode("123456"));
        usuario.setPerfil(perfil);
        usuarioRepository.save(usuario);
    }
}