package biblioteca.infrastructure.adapter;

import biblioteca.domain.model.Emprestimo;
import biblioteca.domain.repository.RepositorioEmprestimo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioEmprestimoMemoriaAdapter implements RepositorioEmprestimo {
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    @Override
    public void salvar(Emprestimo emprestimo) {
        emprestimos.removeIf(e -> e.getId().equals(emprestimo.getId()));
        emprestimos.add(emprestimo);
    }

    @Override
    public Optional<Emprestimo> buscarAtivoPorItemId(String itemId) {
        return emprestimos.stream()
                .filter(Emprestimo::estaAtivo)
                .filter(e -> e.getItem().getId().equals(itemId))
                .findFirst();
    }

    @Override
    public List<Emprestimo> listarAtivosPorUsuarioId(String usuarioId) {
        return emprestimos.stream()
                .filter(Emprestimo::estaAtivo)
                .filter(e -> e.getUsuario().getId().equals(usuarioId))
                .toList();
    }
}