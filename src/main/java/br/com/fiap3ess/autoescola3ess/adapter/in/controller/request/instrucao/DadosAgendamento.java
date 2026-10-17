package br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.instrucao;

import br.com.fiap3ess.autoescola3ess.shared.vo.enumeration.Especialidade;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DadosAgendamento(
        @NotNull
        @JsonProperty("id_aluno")
        //@JsonAlias("id_aluno") //Alternativa à @JsonProperty
        @JsonAlias("idAluno")
        @Schema(example = "2")
        Long idAluno,

        @JsonProperty("id_instrutor")
        @JsonAlias("idInstrutor")
        @Schema(example = "2")
        Long idInstrutor,

        @Schema(example = "MOTOS")
        Especialidade especialidade,

        @NotNull
        @JsonProperty("data_hora")
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm")
        @Future
        @Schema(
                implementation = String.class,
                description = "Data/hora no formato dd/MM/yyyy - HH:mm",
                pattern = "^\\d{2}/\\d{2}/\\d{4} - \\d{2}:\\d{2}$",
                example = "20/10/2026 - 10:00"
        )
        LocalDateTime dataHora) {
}
