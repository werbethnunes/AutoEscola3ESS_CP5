package br.com.fiap3ess.autoescola3ess.application.service;

import br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.usuario.DadosAlteracaoSenha;
import br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.usuario.DadosAtualizacaoUsuario;
import br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.usuario.DadosCadastroUsuario;
import br.com.fiap3ess.autoescola3ess.adapter.in.controller.response.usuario.DadosDetalhamentoUsuario;
import br.com.fiap3ess.autoescola3ess.application.core.domain.Usuario;
import br.com.fiap3ess.autoescola3ess.application.port.out.UsuarioRepository;
import br.com.fiap3ess.autoescola3ess.exception.type.UsuarioNotFoundException;
import br.com.fiap3ess.autoescola3ess.exception.type.ValidacaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public DadosDetalhamentoUsuario cadastrar(DadosCadastroUsuario dados) {
        if (repository.existsByLogin(dados.login())) throw new ValidacaoException("Login já cadastrado!");
        Usuario usuario = new Usuario(null, dados.login(), passwordEncoder.encode(dados.senha()), dados.perfil());
        return new DadosDetalhamentoUsuario(repository.save(usuario));
    }

    @Transactional(readOnly = true)
    public Page<DadosDetalhamentoUsuario> listar(Pageable pageable) { return repository.findAll(pageable).map(DadosDetalhamentoUsuario::new); }

    @Transactional
    public DadosDetalhamentoUsuario atualizar(DadosAtualizacaoUsuario dados) {
        Usuario usuario = repository.findById(dados.id()).orElseThrow(() -> new UsuarioNotFoundException("ID do usuário informado não existe!"));
        usuario.alterarPerfil(dados.perfil());
        return new DadosDetalhamentoUsuario(repository.save(usuario));
    }

    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) throw new UsuarioNotFoundException("ID do usuário informado não existe!");
        repository.deleteById(id);
    }

    @Transactional
    public void alterarMinhaSenha(DadosAlteracaoSenha dados) {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = repository.findByLogin(login);
        if (usuario == null) throw new UsuarioNotFoundException("Usuário não encontrado!");
        if (!passwordEncoder.matches(dados.senhaAtual(), usuario.getPassword())) throw new ValidacaoException("Senha atual inválida!");
        usuario.alterarSenha(passwordEncoder.encode(dados.novaSenha()));
        repository.save(usuario);
    }
}
