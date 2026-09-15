package br.com.fiap.techchallenge.agendamento_service.consulta;

import java.time.LocalDateTime;

public record ConsultaEvento(
        TipoEvento tipoEvento,
        Long consultaId,
        Long pacienteId,
        LocalDateTime dataHoraConsulta,
        StatusConsulta status
) {
}
