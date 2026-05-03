package biblioteca.domain.repository;

import biblioteca.domain.model.Usuario;

import java.util.Optional;

public interface RepositorioUsuario {
    void salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(String id);
}