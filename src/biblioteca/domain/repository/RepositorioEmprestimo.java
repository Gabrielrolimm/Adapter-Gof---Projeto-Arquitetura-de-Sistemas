package biblioteca.domain.repository;

import biblioteca.domain.model.Emprestimo;

import java.util.List;
import java.util.Optional;

public interface RepositorioEmprestimo {
    void salvar(Emprestimo emprestimo);
    Optional<Emprestimo> buscarAtivoPorItemId(String itemId);
    List<Emprestimo> listarAtivosPorUsuarioId(String usuarioId);
}