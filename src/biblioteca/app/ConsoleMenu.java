package biblioteca.app;

import biblioteca.application.ServicoCadastroItem;
import biblioteca.application.ServicoCadastroUsuario;
import biblioteca.application.ServicoConsultaAcervo;
import biblioteca.application.ServicoEmprestimo;
import biblioteca.domain.model.Item;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final ServicoCadastroUsuario servicoCadastroUsuario;
    private final ServicoCadastroItem servicoCadastroItem;
    private final ServicoConsultaAcervo servicoConsultaAcervo;
    private final ServicoEmprestimo servicoEmprestimo;
    private final Scanner scanner;

    public ConsoleMenu(
            ServicoCadastroUsuario servicoCadastroUsuario,
            ServicoCadastroItem servicoCadastroItem,
            ServicoConsultaAcervo servicoConsultaAcervo,
            ServicoEmprestimo servicoEmprestimo
    ) {
        this.servicoCadastroUsuario = servicoCadastroUsuario;
        this.servicoCadastroItem = servicoCadastroItem;
        this.servicoConsultaAcervo = servicoConsultaAcervo;
        this.servicoEmprestimo = servicoEmprestimo;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro();

            try {
                switch (opcao) {
                    case 1 -> cadastrarAluno();
                    case 2 -> cadastrarProfessor();
                    case 3 -> cadastrarLivro();
                    case 4 -> cadastrarPeriodico();
                    case 5 -> listarAcervo();
                    case 6 -> realizarEmprestimo();
                    case 7 -> devolverItem();
                    case 0 -> System.out.println("Encerrando sistema...");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }

            System.out.println();
        } while (opcao != 0);
    }

    private void exibirMenu() {
        System.out.println("=== SISTEMA DE BIBLIOTECA ===");
        System.out.println("1. Cadastrar aluno");
        System.out.println("2. Cadastrar professor");
        System.out.println("3. Cadastrar livro");
        System.out.println("4. Cadastrar periódico");
        System.out.println("5. Listar acervo");
        System.out.println("6. Realizar empréstimo");
        System.out.println("7. Devolver item");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void cadastrarAluno() {
        System.out.print("ID do aluno: ");
        String id = scanner.nextLine();

        System.out.print("Nome do aluno: ");
        String nome = scanner.nextLine();

        servicoCadastroUsuario.cadastrarAluno(id, nome);
        System.out.println("Aluno cadastrado com sucesso.");
    }

    private void cadastrarProfessor() {
        System.out.print("ID do professor: ");
        String id = scanner.nextLine();

        System.out.print("Nome do professor: ");
        String nome = scanner.nextLine();

        servicoCadastroUsuario.cadastrarProfessor(id, nome);
        System.out.println("Professor cadastrado com sucesso.");
    }

    private void cadastrarLivro() {
        System.out.print("ID do livro: ");
        String id = scanner.nextLine();

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Autor: ");
        String autor = scanner.nextLine();

        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();

        servicoCadastroItem.cadastrarLivro(id, titulo, autor, isbn);
        System.out.println("Livro cadastrado com sucesso.");
    }

    private void cadastrarPeriodico() {
        System.out.print("ID do periódico: ");
        String id = scanner.nextLine();

        System.out.print("Título: ");
        String titulo = scanner.nextLine();

        System.out.print("Edição: ");
        int edicao = lerInteiro();

        servicoCadastroItem.cadastrarPeriodico(id, titulo, edicao);
        System.out.println("Periódico cadastrado com sucesso.");
    }

    private void listarAcervo() {
        List<Item> itens = servicoConsultaAcervo.listarItens();

        if (itens.isEmpty()) {
            System.out.println("Acervo vazio.");
            return;
        }

        for (Item item : itens) {
            System.out.println(
                    item.getId() + " - " +
                            item.getTitulo() + " [" + item.getTipo() + "] - " +
                            (item.isDisponivel() ? "Disponível" : "Emprestado")
            );
        }
    }

    private void realizarEmprestimo() {
        System.out.print("ID do usuário: ");
        String usuarioId = scanner.nextLine();

        System.out.print("ID do item: ");
        String itemId = scanner.nextLine();

        var emprestimo = servicoEmprestimo.realizarEmprestimo(usuarioId, itemId);
        System.out.println("Empréstimo realizado com sucesso.");
        System.out.println("Devolução prevista para: " + emprestimo.getDataPrevistaDevolucao());
    }

    private void devolverItem() {
        System.out.print("ID do item: ");
        String itemId = scanner.nextLine();

        servicoEmprestimo.devolverItem(itemId);
        System.out.println("Item devolvido com sucesso.");
    }

    private int lerInteiro() {
        return Integer.parseInt(scanner.nextLine());
    }
}