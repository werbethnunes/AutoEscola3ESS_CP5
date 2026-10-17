package br.com.fiap3ess.autoescola3ess.application.port.out;

import br.com.fiap3ess.autoescola3ess.application.core.domain.Instrucao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface InstrucaoRepository extends JpaRepository<Instrucao, Long> {
    boolean existsByInstrutorIdAndDataHoraAndCanceladaFalse(Long idInstrutor, LocalDateTime dataHora);
    long countByAlunoIdAndDataHoraBetweenAndCanceladaFalse(Long idAluno, LocalDateTime inicio, LocalDateTime fim);
}
