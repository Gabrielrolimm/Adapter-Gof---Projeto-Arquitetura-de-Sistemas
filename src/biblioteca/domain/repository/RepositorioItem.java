package biblioteca.domain.repository;

import biblioteca.domain.model.Item;

import java.util.List;
import java.util.Optional;

public interface RepositorioItem {
    void salvar(Item item);
    Optional<Item> buscarPorId(String id);
    List<Item> listarTodos();
    void restaurarDisponibilidadeDeTodos();
}