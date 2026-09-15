package br.com.fiap.techchallenge.agendamento_service.consulta;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConsultaEventoPublisher {

    public static final String TOPICO_CONSULTAS_EVENTOS = "consultas.eventos";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ConsultaEventoPublisher(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publicar(Consulta consulta, TipoEvento tipoEvento) {
        ConsultaEvento evento = new ConsultaEvento(
                tipoEvento,
                consulta.getId(),
                consulta.getPaciente().getId(),
                consulta.getDataHora(),
                consulta.getStatus()
        );

        String chave = consulta.getPaciente().getId().toString();
        kafkaTemplate.send(TOPICO_CONSULTAS_EVENTOS, chave, evento);
    }
}
