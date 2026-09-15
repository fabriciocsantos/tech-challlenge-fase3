package br.com.fiap.techchallenge.agendamento_service.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import br.com.fiap.techchallenge.agendamento_service.consulta.Consulta;
import br.com.fiap.techchallenge.agendamento_service.consulta.ConsultaRepository;
import br.com.fiap.techchallenge.agendamento_service.consulta.StatusConsulta;
import br.com.fiap.techchallenge.agendamento_service.usuario.Usuario;
import br.com.fiap.techchallenge.agendamento_service.usuario.UsuarioRepository;

@Component
@Order(2)
public class ConsultaSeeder implements CommandLineRunner {

    private final ConsultaRepository consultaRepository;
    private final UsuarioRepository usuarioRepository;

    public ConsultaSeeder(ConsultaRepository consultaRepository, UsuarioRepository usuarioRepository) {
        this.consultaRepository = consultaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {
        Usuario medico = buscarUsuario("medico");
        Usuario paciente1 = buscarUsuario("paciente");
        Usuario paciente2 = buscarUsuario("paciente2");

        criarHistoricoDoPaciente(paciente1, medico);
        criarHistoricoDoPaciente(paciente2, medico);
    }

    private void criarHistoricoDoPaciente(Usuario paciente, Usuario medico) {
        LocalDateTime agora = LocalDateTime.now();

        criarConsulta(paciente, medico, agora.minusDays(10), StatusConsulta.REALIZADA, "Consulta de rotina");
        criarConsulta(paciente, medico, agora.minusDays(3), StatusConsulta.REALIZADA, "Retorno de exames");
        criarConsulta(paciente, medico, agora.plusDays(5), StatusConsulta.AGENDADA, "Consulta de acompanhamento");
        criarConsulta(paciente, medico, agora.plusDays(15), StatusConsulta.AGENDADA, "Avaliação semestral");
    }

    private void criarConsulta(Usuario paciente, Usuario medico, LocalDateTime dataHora, StatusConsulta status,
            String observacoes) {
        Consulta consulta = new Consulta();
        consulta.setPaciente(paciente);
        consulta.setMedico(medico);
        consulta.setDataHora(dataHora);
        consulta.setStatus(status);
        consulta.setObservacoes(observacoes);
        consultaRepository.save(consulta);
    }

    private Usuario buscarUsuario(String username) {
        return usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Usuário não encontrado: " + username));
    }
}
