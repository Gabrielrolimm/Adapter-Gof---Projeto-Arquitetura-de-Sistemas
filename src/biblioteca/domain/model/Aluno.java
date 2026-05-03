package biblioteca.domain.model;

import biblioteca.domain.policy.PoliticaEmprestimo;
import biblioteca.domain.policy.PoliticaEmprestimoAluno;

public class Aluno extends Usuario {
    private static final PoliticaEmprestimo POLITICA = new PoliticaEmprestimoAluno();

    public Aluno(String id, String nome) {
        super(id, nome);
    }

    @Override
    public PoliticaEmprestimo getPoliticaEmprestimo() {
        return POLITICA;
    }

    @Override
    public String getTipo() {
        return "ALUNO";
    }
}