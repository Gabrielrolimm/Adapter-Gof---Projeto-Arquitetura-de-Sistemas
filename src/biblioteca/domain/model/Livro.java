package biblioteca.domain.model;

public class Livro extends Item {
    private final String autor;
    private final String isbn;

    public Livro(String id, String titulo, String autor, String isbn) {
        super(id, titulo);

        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("Autor é obrigatório.");
        }
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN é obrigatório.");
        }

        this.autor = autor;
        this.isbn = isbn;
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String getTipo() {
        return "LIVRO";
    }
}