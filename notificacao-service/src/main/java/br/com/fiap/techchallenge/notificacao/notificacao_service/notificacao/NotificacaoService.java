package br.com.fiap.techchallenge.notificacao.notificacao_service.notificacao;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService {

    private static final Logger log = LoggerFactory.getLogger(NotificacaoService.class);

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    public Notificacao enviarLembrete(Long pacienteId, Long consultaId, LocalDateTime dataHoraConsulta) {
        Notificacao notificacao = new Notificacao();
        notificacao.setPacienteId(pacienteId);
        notificacao.setConsultaId(consultaId);
        notificacao.setDataHoraConsulta(dataHoraConsulta);
        notificacao.setMomentoEnvio(LocalDateTime.now());
        notificacao.setStatus(StatusNotificacao.ENVIADA);

        Notificacao salva = notificacaoRepository.save(notificacao);

        log.info("Lembrete enviado ao paciente {} sobre a consulta {} marcada para {}",
                pacienteId, consultaId, dataHoraConsulta);

        return salva;
    }
}
