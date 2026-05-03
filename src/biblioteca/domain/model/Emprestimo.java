package biblioteca.domain.model;

import java.time.LocalDate;

public class Emprestimo {
    private final String id;
    private final Usuario usuario;
    private final Item item;
    private final LocalDate dataEmprestimo;
    private final LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;

    public Emprestimo(
            String id,
            Usuario usuario,
            Item item,
            LocalDate dataEmprestimo,
            LocalDate dataPrevistaDevolucao
    ) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID do empréstimo é obrigatório.");
        }
        if (usuario == null) {
            throw new IllegalArgumentException("Usuário é obrigatório.");
        }
        if (item == null) {
            throw new IllegalArgumentException("Item é obrigatório.");
        }
        if (dataEmprestimo == null || dataPrevistaDevolucao == null) {
            throw new IllegalArgumentException("Datas do empréstimo são obrigatórias.");
        }
        if (dataPrevistaDevolucao.isBefore(dataEmprestimo)) {
            throw new IllegalArgumentException("Data prevista não pode ser anterior à data do empréstimo.");
        }

        this.id = id;
        this.usuario = usuario;
        this.item = item;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public String getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Item getItem() {
        return item;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public boolean estaAtivo() {
        return dataDevolucao == null;
    }

    public void registrarDevolucao(LocalDate dataDevolucao) {
        if (!estaAtivo()) {
            throw new IllegalStateException("Empréstimo já foi finalizado.");
        }
        if (dataDevolucao == null) {
            throw new IllegalArgumentException("Data de devolução é obrigatória.");
        }
        if (dataDevolucao.isBefore(dataEmprestimo)) {
            throw new IllegalArgumentException("Data de devolução não pode ser anterior à data do empréstimo.");
        }

        this.dataDevolucao = dataDevolucao;
    }
}