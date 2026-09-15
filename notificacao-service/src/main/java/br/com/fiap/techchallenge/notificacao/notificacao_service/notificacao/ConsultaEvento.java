package br.com.fiap.techchallenge.notificacao.notificacao_service.notificacao;

import java.time.LocalDateTime;

/**
 * Formato do evento publicado pelo agendamento-service no tópico consultas.eventos.
 * Não é a mesma classe Java do outro serviço — cada serviço tem a sua, combinando
 * só o formato JSON entre eles, sem compartilhar código.
 */
public record ConsultaEvento(
        String tipoEvento,
        Long consultaId,
        Long pacienteId,
        LocalDateTime dataHoraConsulta,
        StatusConsultaEvento status
) {
}
