package br.com.fiap3ess.autoescola3ess.config;

import br.com.fiap3ess.autoescola3ess.application.core.domain.Usuario;
import br.com.fiap3ess.autoescola3ess.application.port.out.UsuarioRepository;
import br.com.fiap3ess.autoescola3ess.shared.vo.enumeration.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// Cria o primeiro ADMIN (admin/admin) quando o banco não possui nenhum usuário ADMIN.
@Component
@RequiredArgsConstructor
public class AdminInicialRunner implements CommandLineRunner {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // login é unique: se já existir um usuário "admin" (não ADMIN), ele não é alterado
        if (repository.existsByPerfil(Role.ADMIN) || repository.existsByLogin("admin")) return;
        repository.save(new Usuario(null, "admin", passwordEncoder.encode("admin"), Role.ADMIN));
    }
}
