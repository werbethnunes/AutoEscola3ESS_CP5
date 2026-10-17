package br.com.fiap3ess.autoescola3ess.domain;

import br.com.fiap3ess.autoescola3ess.application.core.domain.Aluno;
import br.com.fiap3ess.autoescola3ess.application.core.domain.Instrucao;
import br.com.fiap3ess.autoescola3ess.application.core.domain.Instrutor;
import br.com.fiap3ess.autoescola3ess.shared.vo.endereco.Endereco;
import br.com.fiap3ess.autoescola3ess.shared.vo.enumeration.Especialidade;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class InstrucaoTest {
    @Test
    void deveAssociarAlunoInstrutorEDataDaInstrucao() {
        Aluno aluno = new Aluno(null, "Aluno", "aluno@email.com", "11999999999", "12345678901",
                new Endereco(), true);
        Instrutor instrutor = new Instrutor(null, "Instrutor", "instrutor@email.com", "11999999999", "123456789",
                Especialidade.CARROS, new Endereco(), true);
        LocalDateTime dataHora = LocalDateTime.of(2026, 10, 10, 10, 0);

        Instrucao instrucao = new Instrucao(1L, aluno, instrutor, dataHora);

        assertThat(instrucao.getId()).isEqualTo(1L);
        assertThat(instrucao.getAluno()).isSameAs(aluno);
        assertThat(instrucao.getInstrutor()).isSameAs(instrutor);
        assertThat(instrucao.getDataHora()).isEqualTo(dataHora);
    }
}
