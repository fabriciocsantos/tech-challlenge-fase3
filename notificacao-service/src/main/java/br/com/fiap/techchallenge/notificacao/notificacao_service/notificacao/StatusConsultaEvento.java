package br.com.fiap.techchallenge.notificacao.notificacao_service.notificacao;

/**
 * Espelha os valores de StatusConsulta do agendamento-service. Não é a mesma
 * classe Java do outro serviço — só combina o formato JSON entre eles (o nome
 * do enum), sem compartilhar código, igual ao ConsultaEvento.
 */
public enum StatusConsultaEvento {
    AGENDADA,
    REALIZADA,
    CANCELADA
}
