package br.com.fiap3ess.autoescola3ess.application.service;

import br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.instrucao.DadosCancelamentoInstrucao;
import br.com.fiap3ess.autoescola3ess.application.core.domain.Instrucao;
import br.com.fiap3ess.autoescola3ess.application.port.out.InstrucaoRepository;
import br.com.fiap3ess.autoescola3ess.exception.type.InstrucaoNotFoundException;
import br.com.fiap3ess.autoescola3ess.exception.type.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CancelamentoInstrucaoService {
    private final InstrucaoRepository repository;

    @Transactional
    public void cancelar(Long id, DadosCancelamentoInstrucao dados) {
        Instrucao instrucao = repository.findById(id).orElseThrow(() -> new InstrucaoNotFoundException("ID da instrução informado não existe!"));
        if (instrucao.isCancelada()) throw new ValidacaoException("A instrução já está cancelada!");
        if (!instrucao.getDataHora().isAfter(LocalDateTime.now().plusHours(24))) throw new ValidacaoException("A instrução somente pode ser cancelada com antecedência mínima de 24 horas!");
        instrucao.cancelar(dados.motivo());
        repository.save(instrucao);
    }
}
