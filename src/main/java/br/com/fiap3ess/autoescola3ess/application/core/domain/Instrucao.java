package br.com.fiap3ess.autoescola3ess.application.core.domain;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import br.com.fiap3ess.autoescola3ess.shared.vo.enumeration.MotivoCancelamento;

@Entity(name = "Instrucao")
@Table(name = "instrucoes")
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Instrucao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @Column(name = "instrutor_id")
    private Long instrutorId;

    @Transient
    private Instrutor instrutor;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    private boolean cancelada;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_cancelamento")
    private MotivoCancelamento motivoCancelamento;

    public Instrucao(Long id, Aluno aluno, Instrutor instrutor, LocalDateTime dataHora) {
        this.id = id;
        this.aluno = aluno;
        this.instrutor = instrutor;
        this.instrutorId = instrutor != null ? instrutor.getId() : null;
        this.dataHora = dataHora;
    }

    public boolean isCancelada() { return cancelada; }

    public void cancelar(MotivoCancelamento motivo) {
        this.cancelada = true;
        this.motivoCancelamento = motivo;
    }
}
