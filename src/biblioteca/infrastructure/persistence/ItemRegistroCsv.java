package biblioteca.infrastructure.persistence;

public class ItemRegistroCsv {
    private final String tipo;
    private final String id;
    private final String titulo;
    private final String campoExtra1;
    private final String campoExtra2;
    private final boolean disponivel;

    public ItemRegistroCsv(
            String tipo,
            String id,
            String titulo,
            String campoExtra1,
            String campoExtra2,
            boolean disponivel
    ) {
        this.tipo = tipo;
        this.id = id;
        this.titulo = titulo;
        this.campoExtra1 = campoExtra1;
        this.campoExtra2 = campoExtra2;
        this.disponivel = disponivel;
    }

    public String getTipo() {
        return tipo;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCampoExtra1() {
        return campoExtra1;
    }

    public String getCampoExtra2() {
        return campoExtra2;
    }

    public boolean isDisponivel() {
        return disponivel;
    }
}