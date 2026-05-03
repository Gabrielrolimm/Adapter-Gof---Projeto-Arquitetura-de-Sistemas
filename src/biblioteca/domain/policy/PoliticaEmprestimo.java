package biblioteca.domain.policy;

import biblioteca.domain.model.Usuario;

public interface PoliticaEmprestimo {
    int getPrazoEmDias();
    int getLimiteItens();
    boolean podeEmprestar(int quantidadeEmprestimosAtivos, Usuario usuario);
}