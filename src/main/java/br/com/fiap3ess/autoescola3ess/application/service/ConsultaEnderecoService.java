package br.com.fiap3ess.autoescola3ess.application.service;

import br.com.fiap3ess.autoescola3ess.application.port.out.ConsultaEnderecoPorCep;
import br.com.fiap3ess.autoescola3ess.exception.type.ValidacaoException;
import br.com.fiap3ess.autoescola3ess.shared.vo.endereco.Endereco;
import br.com.fiap3ess.autoescola3ess.shared.vo.endereco.dto.DadosEndereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultaEnderecoService {
    private final ConsultaEnderecoPorCep consultaEnderecoPorCep;

    public DadosEndereco consultarPorCep(String cep) {
        String cepNormalizado = normalizarCep(cep);
        Endereco endereco = consultaEnderecoPorCep.consultar(cepNormalizado);
        return new DadosEndereco(endereco);
    }

    private String normalizarCep(String cep) {
        if (cep == null) {
            throw new ValidacaoException("CEP é obrigatório!");
        }

        String cepInformado = cep.trim();
        if (!cepInformado.matches("\\d{8}|\\d{5}-\\d{3}")) {
            throw new ValidacaoException("CEP deve conter 8 dígitos!");
        }
        return cepInformado.replace("-", "");
    }
}
