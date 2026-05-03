package biblioteca.domain.model;

public class Periodico extends Item {
    private final int edicao;

    public Periodico(String id, String titulo, int edicao) {
        super(id, titulo);

        if (edicao <= 0) {
            throw new IllegalArgumentException("Edição deve ser maior que zero.");
        }

        this.edicao = edicao;
    }

    public int getEdicao() {
        return edicao;
    }

    @Override
    public String getTipo() {
        return "PERIODICO";
    }
}