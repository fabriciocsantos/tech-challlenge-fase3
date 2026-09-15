package br.com.fiap.techchallenge.agendamento_service.consulta;

import org.apache.kafka.common.serialization.Serializer;

import tools.jackson.databind.json.JsonMapper;

/**
 * O JsonSerializer que vem pronto no Spring Kafka ainda depende de classes do Jackson 2
 * (com.fasterxml.jackson.core.type.TypeReference), que não existem mais no classpath
 * do projeto — o Spring Boot 4 migrou pro Jackson 3 (pacote tools.jackson.*). Por isso
 * este serializer próprio, simples, usando o Jackson 3 já presente no projeto.
 */
public class ConsultaEventoSerializer implements Serializer<Object> {

    private final JsonMapper jsonMapper = JsonMapper.builder().findAndAddModules().build();

    @Override
    public byte[] serialize(String topic, Object data) {
        if (data == null) {
            return null;
        }
        return jsonMapper.writeValueAsBytes(data);
    }
}
