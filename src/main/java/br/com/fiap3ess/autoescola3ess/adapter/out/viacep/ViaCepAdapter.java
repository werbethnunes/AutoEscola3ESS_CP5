package br.com.fiap3ess.autoescola3ess.adapter.out.viacep;

import br.com.fiap3ess.autoescola3ess.application.port.out.ConsultaEnderecoPorCep;
import br.com.fiap3ess.autoescola3ess.exception.type.CepNaoEncontradoException;
import br.com.fiap3ess.autoescola3ess.exception.type.ServicoExternoException;
import br.com.fiap3ess.autoescola3ess.shared.vo.endereco.Endereco;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
public class ViaCepAdapter implements ConsultaEnderecoPorCep {
    private final RestClient restClient;

    public ViaCepAdapter(
            RestClient.Builder restClientBuilder,
            @Value("${viacep.url}") String url) {
        this.restClient = restClientBuilder.baseUrl(url).build();
    }

    @Override
    public Endereco consultar(String cep) {
        try {
            ViaCepResponse resposta = restClient.get()
                    .uri("/{cep}/json/", cep)
                    .retrieve()
                    .body(ViaCepResponse.class);

            if (resposta == null || Boolean.TRUE.equals(resposta.erro())) {
                throw new CepNaoEncontradoException("CEP não encontrado na ViaCEP!");
            }

            if (resposta.logradouro() == null
                    || resposta.bairro() == null
                    || resposta.localidade() == null
                    || resposta.uf() == null) {
                throw new ServicoExternoException("Resposta inválida recebida da ViaCEP!");
            }

            return new Endereco(
                    resposta.logradouro(),
                    null,
                    resposta.complemento(),
                    resposta.bairro(),
                    resposta.localidade(),
                    resposta.uf(),
                    resposta.cep()
            );
        } catch (CepNaoEncontradoException | ServicoExternoException exception) {
            throw exception;
        } catch (RestClientException exception) {
            throw new ServicoExternoException("Não foi possível consultar a ViaCEP!", exception);
        }
    }

    private record ViaCepResponse(
            String cep,
            String logradouro,
            String complemento,
            String bairro,
            String localidade,
            String uf,
            Boolean erro) {
    }
}
