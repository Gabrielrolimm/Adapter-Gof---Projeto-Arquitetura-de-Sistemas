package biblioteca.infrastructure.adapter;

import biblioteca.domain.model.Item;
import biblioteca.domain.repository.RepositorioItem;
import biblioteca.infrastructure.persistence.ItemCsvMapper;
import biblioteca.infrastructure.persistence.ItemRegistroCsv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RepositorioItemArquivoAdapter implements RepositorioItem {
    private final Path caminhoArquivo;
    private final ItemCsvMapper mapper;

    public RepositorioItemArquivoAdapter(String caminhoArquivo) {
        this.caminhoArquivo = Path.of(caminhoArquivo);
        this.mapper = new ItemCsvMapper();
    }


    @Override
    public Optional<Item> buscarPorId(String id) {
        return listarTodos().stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Item> listarTodos() {
        if (!Files.exists(caminhoArquivo)) {
            return new ArrayList<>();
        }

        try {
            List<String> linhas = Files.readAllLines(caminhoArquivo);
            List<Item> itens = new ArrayList<>();

            for (String linha : linhas) {
                if (linha.isBlank()) {
                    continue;
                }

                ItemRegistroCsv registro = mapper.deLinha(linha);
                Item item = mapper.paraItem(registro);
                itens.add(item);
            }

            return itens;
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao ler arquivo de itens.", e);
        }
    }

    @Override
    public void salvar(Item item) {
        List<Item> itens = listarTodos();
        List<Item> atualizados = new ArrayList<>();
        boolean substituido = false;

        for (Item existente : itens) {
            if (existente.getId().equals(item.getId())) {
                atualizados.add(item);
                substituido = true;
            } else {
                atualizados.add(existente);
            }
        }

        if (!substituido) {
            atualizados.add(item);
        }

        persistir(atualizados);
    }

    @Override
    public void restaurarDisponibilidadeDeTodos() {
        List<Item> itens = listarTodos();

        for (Item item : itens) {
            item.marcarComoDisponivel();
        }

        persistir(itens);
    }

    private void persistir(List<Item> itens) {
        List<String> linhas = new ArrayList<>();

        for (Item item : itens) {
            ItemRegistroCsv registro = mapper.deItem(item);
            String linha = mapper.paraLinha(registro);
            linhas.add(linha);
        }

        try {
            Files.write(caminhoArquivo, linhas);
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao salvar arquivo de itens.", e);
        }
    }
}