package br.com.fiap.techchallenge.notificacao.notificacao_service.notificacao;

import org.apache.kafka.common.serialization.Deserializer;

import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.json.JsonMapper;

/**
 * Desserializador próprio: ignora o cabeçalho __TypeId__ que o produtor eventualmente
 * grave e declara o próprio tipo de destino (ConsultaEvento.class, deste serviço).
 * Também não depende do JsonDeserializer do Spring Kafka, que ainda usa classes do
 * Jackson 2 incompatíveis com o Jackson 3 usado no projeto (mesmo motivo do
 * ConsultaEventoSerializer no agendamento-service).
 */
public class ConsultaEventoDeserializer implements Deserializer<ConsultaEvento> {

    private final JsonMapper jsonMapper = JsonMapper.builder()
            .findAndAddModules()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .build();

    @Override
    public ConsultaEvento deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }
        return jsonMapper.readValue(data, ConsultaEvento.class);
    }
}
