package biblioteca.domain.model;

import biblioteca.domain.policy.PoliticaEmprestimo;

public abstract class Usuario {
    private final String id;
    private final String nome;

    protected Usuario(String id, String nome) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID do usuário é obrigatório.");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do usuário é obrigatório.");
        }

        this.id = id;
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public abstract PoliticaEmprestimo getPoliticaEmprestimo();

    public abstract String getTipo();
}