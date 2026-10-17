package br.com.fiap3ess.autoescola3ess.adapter.in.controller.request.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosAlteracaoSenha(@NotBlank String senhaAtual, @NotBlank String novaSenha) {
}
