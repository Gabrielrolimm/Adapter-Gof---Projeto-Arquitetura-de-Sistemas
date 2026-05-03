package biblioteca.domain.policy;

import biblioteca.domain.model.Usuario;

public class PoliticaEmprestimoProfessor implements PoliticaEmprestimo {
    private static final int PRAZO_DIAS = 14;
    private static final int LIMITE_ITENS = 5;

    @Override
    public int getPrazoEmDias() {
        return PRAZO_DIAS;
    }

    @Override
    public int getLimiteItens() {
        return LIMITE_ITENS;
    }

    @Override
    public boolean podeEmprestar(int quantidadeEmprestimosAtivos, Usuario usuario) {
        return quantidadeEmprestimosAtivos < LIMITE_ITENS;
    }
}