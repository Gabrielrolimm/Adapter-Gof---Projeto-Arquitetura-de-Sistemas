package biblioteca.application;

import biblioteca.domain.model.Emprestimo;
import biblioteca.domain.model.Item;
import biblioteca.domain.model.Usuario;
import biblioteca.domain.policy.PoliticaEmprestimo;
import biblioteca.domain.repository.RepositorioEmprestimo;
import biblioteca.domain.repository.RepositorioItem;
import biblioteca.domain.repository.RepositorioUsuario;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ServicoEmprestimo {
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioItem repositorioItem;
    private final RepositorioEmprestimo repositorioEmprestimo;

    public ServicoEmprestimo(
            RepositorioUsuario repositorioUsuario,
            RepositorioItem repositorioItem,
            RepositorioEmprestimo repositorioEmprestimo
    ) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioItem = repositorioItem;
        this.repositorioEmprestimo = repositorioEmprestimo;
    }

    public Emprestimo realizarEmprestimo(String usuarioId, String itemId) {
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        Item item = repositorioItem.buscarPorId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado."));

        if (!item.isDisponivel()) {
            throw new IllegalStateException("Item indisponível para empréstimo.");
        }

        List<Emprestimo> ativos = repositorioEmprestimo.listarAtivosPorUsuarioId(usuarioId);
        PoliticaEmprestimo politica = usuario.getPoliticaEmprestimo();

        if (!politica.podeEmprestar(ativos.size(), usuario)) {
            throw new IllegalStateException("Usuário atingiu o limite de empréstimos.");
        }

        LocalDate hoje = LocalDate.now();
        LocalDate devolucaoPrevista = hoje.plusDays(politica.getPrazoEmDias());

        item.marcarComoEmprestado();
        repositorioItem.salvar(item);

        Emprestimo emprestimo = new Emprestimo(
                UUID.randomUUID().toString(),
                usuario,
                item,
                hoje,
                devolucaoPrevista
        );

        repositorioEmprestimo.salvar(emprestimo);
        return emprestimo;
    }

    public void devolverItem(String itemId) {
        Emprestimo emprestimo = repositorioEmprestimo.buscarAtivoPorItemId(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Nenhum empréstimo ativo encontrado para o item."));

        emprestimo.registrarDevolucao(LocalDate.now());
        emprestimo.getItem().marcarComoDisponivel();

        repositorioEmprestimo.salvar(emprestimo);
        repositorioItem.salvar(emprestimo.getItem());
    }
}