package biblioteca.app;

import biblioteca.application.ServicoCadastroItem;
import biblioteca.application.ServicoCadastroUsuario;
import biblioteca.application.ServicoConsultaAcervo;
import biblioteca.application.ServicoEmprestimo;
import biblioteca.infrastructure.adapter.RepositorioEmprestimoMemoriaAdapter;
import biblioteca.infrastructure.adapter.RepositorioItemArquivoAdapter;
import biblioteca.infrastructure.adapter.RepositorioUsuarioMemoriaAdapter;

public class Main {
    public static void main(String[] args) {
        var repositorioUsuario = new RepositorioUsuarioMemoriaAdapter();
        var repositorioItem = new RepositorioItemArquivoAdapter("itens.csv");
        var repositorioEmprestimo = new RepositorioEmprestimoMemoriaAdapter();

        repositorioItem.restaurarDisponibilidadeDeTodos();

        var servicoCadastroUsuario = new ServicoCadastroUsuario(repositorioUsuario);
        var servicoCadastroItem = new ServicoCadastroItem(repositorioItem);
        var servicoConsultaAcervo = new ServicoConsultaAcervo(repositorioItem);
        var servicoEmprestimo = new ServicoEmprestimo(
                repositorioUsuario,
                repositorioItem,
                repositorioEmprestimo
        );

        var menu = new ConsoleMenu(
                servicoCadastroUsuario,
                servicoCadastroItem,
                servicoConsultaAcervo,
                servicoEmprestimo
        );

        menu.iniciar();
    }
}