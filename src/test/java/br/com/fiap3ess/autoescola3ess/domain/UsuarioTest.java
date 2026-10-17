package br.com.fiap3ess.autoescola3ess.domain;

import br.com.fiap3ess.autoescola3ess.application.core.domain.Usuario;
import br.com.fiap3ess.autoescola3ess.shared.vo.enumeration.Role;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UsuarioTest {
    @Test
    void deveInformarLoginSenhaEPerfilDeAcesso() {
        Usuario usuario = new Usuario(1L, "admin", "senha-hash", Role.ADMIN);

        assertThat(usuario.getUsername()).isEqualTo("admin");
        assertThat(usuario.getPassword()).isEqualTo("senha-hash");
        assertThat(usuario.getAuthorities())
                .extracting("authority")
                .containsExactly("ROLE_ADMIN");
        assertThat(usuario.isAccountNonExpired()).isTrue();
        assertThat(usuario.isAccountNonLocked()).isTrue();
        assertThat(usuario.isCredentialsNonExpired()).isTrue();
        assertThat(usuario.isEnabled()).isTrue();
    }
}
