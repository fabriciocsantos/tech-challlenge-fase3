package br.com.fiap.techchallenge.notificacao.notificacao_service.notificacao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsultaEventoListener {

    private static final Logger log = LoggerFactory.getLogger(ConsultaEventoListener.class);

    private final NotificacaoService notificacaoService;

    public ConsultaEventoListener(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @KafkaListener(topics = "consultas.eventos", groupId = "notificacao-service")
    public void receber(ConsultaEvento evento) {
        log.info("Evento recebido: {} para a consulta {} do paciente {}",
                evento.tipoEvento(), evento.consultaId(), evento.pacienteId());

        if (evento.status() == StatusConsultaEvento.CANCELADA) {
            log.info("Consulta {} está cancelada, lembrete não será enviado", evento.consultaId());
            return;
        }

        notificacaoService.enviarLembrete(evento.pacienteId(), evento.consultaId(), evento.dataHoraConsulta());
    }
}
