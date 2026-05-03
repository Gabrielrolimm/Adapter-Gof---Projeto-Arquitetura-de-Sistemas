package biblioteca.infrastructure.adapter;

import biblioteca.domain.model.Usuario;
import biblioteca.domain.repository.RepositorioUsuario;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RepositorioUsuarioMemoriaAdapter implements RepositorioUsuario {
    private final Map<String, Usuario> usuarios = new HashMap<>();

    @Override
    public void salvar(Usuario usuario) {
        usuarios.put(usuario.getId(), usuario);
    }

    @Override
    public Optional<Usuario> buscarPorId(String id) {
        return Optional.ofNullable(usuarios.get(id));
    }
}