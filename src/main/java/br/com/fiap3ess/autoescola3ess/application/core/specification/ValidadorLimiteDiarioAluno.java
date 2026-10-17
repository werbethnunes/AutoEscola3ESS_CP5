package br.com.fiap3ess.autoescola3ess.application.core.specification;

import br.com.fiap3ess.autoescola3ess.application.core.specification.contract.ValidadorAgendamento;
import br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.instrucao.DadosAgendamento;
import br.com.fiap3ess.autoescola3ess.application.port.out.InstrucaoRepository;
import br.com.fiap3ess.autoescola3ess.exception.type.ValidacaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ValidadorLimiteDiarioAluno implements ValidadorAgendamento {
    @Autowired
    private InstrucaoRepository repository;

    @Override
    public void validar(DadosAgendamento dados) {
        LocalDateTime inicio = dados.dataHora().withHour(6).withMinute(0);
        LocalDateTime fim = dados.dataHora().withHour(20).withMinute(59);
        long quantidade = repository.countByAlunoIdAndDataHoraBetweenAndCanceladaFalse(dados.idAluno(), inicio, fim);
        if (quantidade >= 2) throw new ValidacaoException("O aluno não pode ter mais de duas instruções no mesmo dia!");
    }
}
