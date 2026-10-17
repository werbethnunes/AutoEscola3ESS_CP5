package br.com.fiap3ess.autoescola3ess.domain;

import br.com.fiap3ess.autoescola3ess.application.core.domain.Instrutor;
import br.com.fiap3ess.autoescola3ess.shared.vo.endereco.Endereco;
import br.com.fiap3ess.autoescola3ess.shared.vo.enumeration.Especialidade;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstrutorTest {
    @Test
    void deveInativarInstrutor() {
        Instrutor instrutor = new Instrutor(
                1L, "Instrutor Teste", "instrutor@email.com", "11999999999", "123456789",
                Especialidade.CARROS,
                new Endereco("Rua Teste", "10", null, "Centro", "Sao Paulo", "SP", "01001-000"),
                true);

        instrutor.excluir();

        assertThat(instrutor.isAtivo()).isFalse();
    }
}
