package br.com.fiap3ess.autoescola3ess.domain;

import br.com.fiap3ess.autoescola3ess.application.core.domain.Aluno;
import br.com.fiap3ess.autoescola3ess.shared.vo.endereco.Endereco;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlunoTest {
    @Test
    void deveCriarAlunoComSeusDados() {
        Endereco endereco = new Endereco(
                "Rua Teste", "10", "Apto 2", "Centro", "Sao Paulo", "SP", "01001-000");

        Aluno aluno = new Aluno(
                1L, "Aluno Teste", "aluno@email.com", "11999999999", "12345678901", endereco, true);

        assertThat(aluno.getId()).isEqualTo(1L);
        assertThat(aluno.getNome()).isEqualTo("Aluno Teste");
        assertThat(aluno.getEmail()).isEqualTo("aluno@email.com");
        assertThat(aluno.getTelefone()).isEqualTo("11999999999");
        assertThat(aluno.getCpf()).isEqualTo("12345678901");
        assertThat(aluno.getEndereco()).isEqualTo(endereco);
        assertThat(aluno.isAtivo()).isTrue();
    }
}
