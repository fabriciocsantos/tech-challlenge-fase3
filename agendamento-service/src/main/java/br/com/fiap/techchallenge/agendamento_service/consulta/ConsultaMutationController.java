package br.com.fiap.techchallenge.agendamento_service.consulta;

import java.time.LocalDateTime;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import br.com.fiap.techchallenge.agendamento_service.usuario.Perfil;
import br.com.fiap.techchallenge.agendamento_service.usuario.Usuario;
import br.com.fiap.techchallenge.agendamento_service.usuario.UsuarioRepository;

@Controller
public class ConsultaMutationController {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ConsultaEventoPublisher consultaEventPublisher;

    public ConsultaMutationController(ConsultaRepository consultaRepository, UsuarioRepository usuarioRepository,
                                       ConsultaEventoPublisher consultaEventPublisher) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
        this.consultaEventPublisher = consultaEventPublisher;
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public Consulta registrarConsulta(@Argument Long pacienteId, @Argument Long medicoId,
                                       @Argument String dataHora, @Argument String observacoes) {
        Usuario paciente = buscarUsuarioComPerfil(pacienteId, Perfil.PACIENTE);
        Usuario medico = buscarUsuarioComPerfil(medicoId, Perfil.MEDICO);

        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(LocalDateTime.parse(dataHora));
        consulta.setObservacoes(observacoes);

        Consulta salva = consultaRepository.save(consulta);
        consultaEventPublisher.publicar(salva, TipoEvento.CRIADA);

        return salva;
    }

    @MutationMapping
    @PreAuthorize("hasAnyRole('MEDICO', 'ENFERMEIRO')")
    public Consulta editarConsulta(@Argument Long id, @Argument String dataHora,
                                    @Argument StatusConsulta status, @Argument String observacoes) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada: " + id));

        if (dataHora != null) {
            consulta.setDataHora(LocalDateTime.parse(dataHora));
        }
        if (status != null) {
            consulta.setStatus(status);
        }
        if (observacoes != null) {
            consulta.setObservacoes(observacoes);
        }

        Consulta salva = consultaRepository.save(consulta);
        consultaEventPublisher.publicar(salva, TipoEvento.EDITADA);

        return salva;
    }

    private Usuario buscarUsuarioComPerfil(Long id, Perfil perfilEsperado) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));

        if (usuario.getPerfil() != perfilEsperado) {
            throw new IllegalArgumentException(
                    "Usuário " + id + " não tem perfil " + perfilEsperado);
        }

        return usuario;
    }
}
