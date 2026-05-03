package biblioteca.application;

import biblioteca.domain.model.Item;
import biblioteca.domain.model.Livro;
import biblioteca.domain.model.Periodico;
import biblioteca.domain.repository.RepositorioItem;

public class ServicoCadastroItem {
    private final RepositorioItem repositorioItem;

    public ServicoCadastroItem(RepositorioItem repositorioItem) {
        this.repositorioItem = repositorioItem;
    }

    public Item cadastrarLivro(String id, String titulo, String autor, String isbn) {
        if (repositorioItem.buscarPorId(id).isPresent()) {
            throw new IllegalArgumentException("Já existe um item com esse ID.");
        }

        Item item = new Livro(id, titulo, autor, isbn);
        repositorioItem.salvar(item);
        return item;
    }

    public Item cadastrarPeriodico(String id, String titulo, int edicao) {
        if (repositorioItem.buscarPorId(id).isPresent()) {
            throw new IllegalArgumentException("Já existe um item com esse ID.");
        }

        Item item = new Periodico(id, titulo, edicao);
        repositorioItem.salvar(item);
        return item;
    }
}