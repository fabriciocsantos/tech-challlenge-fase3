package br.com.fiap.techchallenge.agendamento_service.consulta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import br.com.fiap.techchallenge.agendamento_service.usuario.Perfil;
import br.com.fiap.techchallenge.agendamento_service.usuario.Usuario;
import br.com.fiap.techchallenge.agendamento_service.usuario.UsuarioRepository;

@Controller
public class ConsultaQueryController {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;

    public ConsultaQueryController(ConsultaRepository consultaRepository, UsuarioRepository usuarioRepository) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @QueryMapping
    public List<Consulta> historicoDoPaciente(@Argument Long pacienteId) {
        Usuario paciente = buscarPaciente(pacienteId);
        validarAcessoAoPaciente(paciente);
        return consultaRepository.findByPaciente(paciente);
    }

    @QueryMapping
    public List<Consulta> consultasFuturas(@Argument Long pacienteId) {
        Usuario paciente = buscarPaciente(pacienteId);
        validarAcessoAoPaciente(paciente);
        return consultaRepository.findByPacienteAndDataHoraAfter(paciente, LocalDateTime.now());
    }

    private Usuario buscarPaciente(Long pacienteId) {
        return usuarioRepository.findById(pacienteId)
                .orElseThrow(() -> new IllegalArgumentException("Paciente não encontrado: " + pacienteId));
    }

    private void validarAcessoAoPaciente(Usuario paciente) {
        Usuario logado = buscarUsuarioLogado();
        boolean ePaciente = logado.getPerfil() == Perfil.PACIENTE;
        boolean eDonoDaConsulta = logado.getId().equals(paciente.getId());

        if (ePaciente && !eDonoDaConsulta) {
            throw new AccessDeniedException("Paciente só pode visualizar as próprias consultas");
        }
    }

    private Usuario buscarUsuarioLogado() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuário autenticado não encontrado: " + username));
    }
}
