package br.com.fiap.techchallenge.notificacao.notificacao_service.notificacao;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notificacoes")
@Getter
@Setter
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long pacienteId;

    @Column(nullable = false)
    private Long consultaId;

    @Column(nullable = false)
    private LocalDateTime dataHoraConsulta;

    @Column(nullable = false)
    private LocalDateTime momentoEnvio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNotificacao status;
}
