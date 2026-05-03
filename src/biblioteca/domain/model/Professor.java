package biblioteca.domain.model;

import biblioteca.domain.policy.PoliticaEmprestimo;
import biblioteca.domain.policy.PoliticaEmprestimoProfessor;

public class Professor extends Usuario {
    private static final PoliticaEmprestimo POLITICA = new PoliticaEmprestimoProfessor();

    public Professor(String id, String nome) {
        super(id, nome);
    }

    @Override
    public PoliticaEmprestimo getPoliticaEmprestimo() {
        return POLITICA;
    }

    @Override
    public String getTipo() {
        return "PROFESSOR";
    }
}