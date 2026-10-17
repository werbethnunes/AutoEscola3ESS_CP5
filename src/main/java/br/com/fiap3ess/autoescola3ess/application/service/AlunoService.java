package br.com.fiap3ess.autoescola3ess.application.service;

import br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.aluno.DadosAtualizacaoAluno;
import br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.aluno.DadosCadastroAluno;
import br.com.fiap3ess.autoescola3ess.adapter.in.controller.response.aluno.DadosDetalhamentoAluno;
import br.com.fiap3ess.autoescola3ess.adapter.in.controller.response.aluno.DadosListagemAluno;
import br.com.fiap3ess.autoescola3ess.application.core.domain.Aluno;
import br.com.fiap3ess.autoescola3ess.application.port.out.AlunoRepository;
import br.com.fiap3ess.autoescola3ess.exception.type.AlunoNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlunoService {
    private final AlunoRepository repository;

    @Transactional
    public DadosDetalhamentoAluno cadastrar(DadosCadastroAluno dados) {
        return new DadosDetalhamentoAluno(repository.save(new Aluno(dados)));
    }

    @Transactional(readOnly = true)
    public Page<DadosListagemAluno> listar(Pageable pageable) {
        return repository.findAllByAtivoTrue(pageable).map(DadosListagemAluno::new);
    }

    @Transactional(readOnly = true)
    public DadosDetalhamentoAluno detalhar(Long id) {
        return new DadosDetalhamentoAluno(repository.findById(id).orElseThrow(() -> new AlunoNotFoundException("ID do aluno informado não existe!")));
    }

    @Transactional
    public DadosDetalhamentoAluno atualizar(DadosAtualizacaoAluno dados) {
        Aluno aluno = repository.findById(dados.id()).orElseThrow(() -> new AlunoNotFoundException("ID do aluno informado não existe!"));
        aluno.atualizarInformacoes(dados);
        return new DadosDetalhamentoAluno(repository.save(aluno));
    }

    @Transactional
    public void excluir(Long id) {
        Aluno aluno = repository.findById(id).orElseThrow(() -> new AlunoNotFoundException("ID do aluno informado não existe!"));
        aluno.excluir();
        repository.save(aluno);
    }
}
