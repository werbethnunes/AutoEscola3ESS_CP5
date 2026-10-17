package br.com.fiap3ess.autoescola3ess.application.port.out;

import br.com.fiap3ess.autoescola3ess.shared.vo.endereco.Endereco;

public interface ConsultaEnderecoPorCep {
    Endereco consultar(String cep);
}
