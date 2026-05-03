package biblioteca.domain.model;

public abstract class Item {
    private final String id;
    private final String titulo;
    private boolean disponivel;

    protected Item(String id, String titulo) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID do item é obrigatório.");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("Título do item é obrigatório.");
        }

        this.id = id;
        this.titulo = titulo;
        this.disponivel = true;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void marcarComoEmprestado() {
        if (!disponivel) {
            throw new IllegalStateException("Item já está emprestado.");
        }
        this.disponivel = false;
    }

    public void marcarComoDisponivel() {
        this.disponivel = true;
    }

    public abstract String getTipo();
}