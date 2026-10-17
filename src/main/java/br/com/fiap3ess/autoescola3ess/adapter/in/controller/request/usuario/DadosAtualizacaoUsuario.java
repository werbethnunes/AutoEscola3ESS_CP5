package br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.usuario;

import br.com.fiap3ess.autoescola3ess.shared.vo.enumeration.Role;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoUsuario(@NotNull Long id, @NotNull Role perfil) {
}
