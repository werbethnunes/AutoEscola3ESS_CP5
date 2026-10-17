package br.com.fiap3ess.autoescola3ess.adapter.out.viacep;

import br.com.fiap3ess.autoescola3ess.exception.type.CepNaoEncontradoException;
import br.com.fiap3ess.autoescola3ess.exception.type.ServicoExternoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

class ViaCepAdapterTest {
    private MockRestServiceServer server;
    private ViaCepAdapter adapter;

    @BeforeEach
    void configurarAdapter() {
        RestClient.Builder builder = RestClient.builder();
        server = MockRestServiceServer.bindTo(builder).build();
        adapter = new ViaCepAdapter(builder, "http://viacep.test/ws");
    }

    @Test
    void deveConsultarCepValido() {
        server.expect(requestTo("http://viacep.test/ws/01001000/json/"))
                .andRespond(withSuccess("""
                        {
                          "cep": "01001-000",
                          "logradouro": "Praça da Sé",
                          "complemento": "lado ímpar",
                          "bairro": "Sé",
                          "localidade": "São Paulo",
                          "uf": "SP",
                          "erro": false
                        }
                        """, MediaType.APPLICATION_JSON));

        var endereco = adapter.consultar("01001000");

        assertThat(endereco.getCep()).isEqualTo("01001-000");
        assertThat(endereco.getLogradouro()).isEqualTo("Praça da Sé");
        assertThat(endereco.getCidade()).isEqualTo("São Paulo");
        assertThat(endereco.getUf()).isEqualTo("SP");
        server.verify();
    }

    @Test
    void deveInformarQuandoCepNaoForEncontrado() {
        server.expect(requestTo("http://viacep.test/ws/99999999/json/"))
                .andRespond(withSuccess("{\"erro\": true}", MediaType.APPLICATION_JSON));

        assertThatThrownBy(() -> adapter.consultar("99999999"))
                .isInstanceOf(CepNaoEncontradoException.class);
        server.verify();
    }

    @Test
    void deveInformarFalhaDeComunicacao() {
        server.expect(requestTo("http://viacep.test/ws/01001000/json/"))
                .andRespond(withServerError());

        assertThatThrownBy(() -> adapter.consultar("01001000"))
                .isInstanceOf(ServicoExternoException.class);
        server.verify();
    }
}
