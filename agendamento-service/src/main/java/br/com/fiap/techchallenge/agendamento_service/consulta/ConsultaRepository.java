package br.com.fiap.techchallenge.agendamento_service.consulta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.techchallenge.agendamento_service.usuario.Usuario;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByPaciente(Usuario paciente);

    List<Consulta> findByPacienteAndDataHoraAfter(Usuario paciente, LocalDateTime referencia);
}