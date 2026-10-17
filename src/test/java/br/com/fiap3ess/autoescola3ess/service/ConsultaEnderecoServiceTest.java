package br.com.fiap3ess.autoescola3ess.service;

import br.com.fiap3ess.autoescola3ess.application.port.out.ConsultaEnderecoPorCep;
import br.com.fiap3ess.autoescola3ess.application.service.ConsultaEnderecoService;
import br.com.fiap3ess.autoescola3ess.exception.type.ValidacaoException;
import br.com.fiap3ess.autoescola3ess.shared.vo.endereco.Endereco;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConsultaEnderecoServiceTest {
    @Test
    void deveNormalizarCepAntesDaConsulta() {
        ConsultaEnderecoPorCep port = cep -> new Endereco(
                "Praça da Sé", null, null, "Sé", "São Paulo", "SP", "01001-000");
        ConsultaEnderecoService service = new ConsultaEnderecoService(port);

        var endereco = service.consultarPorCep("01001-000");

        assertThat(endereco.cep()).isEqualTo("01001-000");
    }

    @Test
    void deveRejeitarCepInvalido() {
        ConsultaEnderecoService service = new ConsultaEnderecoService(cep -> null);

        assertThatThrownBy(() -> service.consultarPorCep("1234"))
                .isInstanceOf(ValidacaoException.class);

        assertThatThrownBy(() -> service.consultarPorCep("abc01001000"))
                .isInstanceOf(ValidacaoException.class);
    }
}
