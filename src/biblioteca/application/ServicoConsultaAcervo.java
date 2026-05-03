package biblioteca.application;

import biblioteca.domain.model.Item;
import biblioteca.domain.repository.RepositorioItem;

import java.util.List;

public class ServicoConsultaAcervo {
    private final RepositorioItem repositorioItem;

    public ServicoConsultaAcervo(RepositorioItem repositorioItem) {
        this.repositorioItem = repositorioItem;
    }

    public List<Item> listarItens() {
        return repositorioItem.listarTodos();
    }
}