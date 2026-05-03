package biblioteca.application;

import biblioteca.domain.model.Aluno;
import biblioteca.domain.model.Professor;
import biblioteca.domain.model.Usuario;
import biblioteca.domain.repository.RepositorioUsuario;

public class ServicoCadastroUsuario {
    private final RepositorioUsuario repositorioUsuario;

    public ServicoCadastroUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    public Usuario cadastrarAluno(String id, String nome) {
        if (repositorioUsuario.buscarPorId(id).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com esse ID.");
        }

        Usuario usuario = new Aluno(id, nome);
        repositorioUsuario.salvar(usuario);
        return usuario;
    }

    public Usuario cadastrarProfessor(String id, String nome) {
        if (repositorioUsuario.buscarPorId(id).isPresent()) {
            throw new IllegalArgumentException("Já existe um usuário com esse ID.");
        }

        Usuario usuario = new Professor(id, nome);
        repositorioUsuario.salvar(usuario);
        return usuario;
    }
}