package br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.instrucao;

import br.com.fiap3ess.autoescola3ess.shared.vo.enumeration.MotivoCancelamento;
import jakarta.validation.constraints.NotNull;

public record DadosCancelamentoInstrucao(@NotNull MotivoCancelamento motivo) {
}
