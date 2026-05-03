package biblioteca.domain.policy;

import biblioteca.domain.model.Usuario;

public class PoliticaEmprestimoAluno implements PoliticaEmprestimo {
    private static final int PRAZO_DIAS = 7;
    private static final int LIMITE_ITENS = 3;

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