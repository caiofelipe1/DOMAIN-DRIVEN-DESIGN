package controller;

import model.Usuario;
import repository.UsuarioRepository;

public class UsuarioController {
    private final UsuarioRepository repo;

    public UsuarioController() {
        this.repo = new UsuarioRepository();
    }

    public void cadastrar(String nome, String email, String senha) {
        if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
            throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
        }
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (repo.buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("Já existe um usuário com este email.");
        }
        Usuario u = new Usuario(nome, email, senha);
        repo.inserir(u);
    }

    public Usuario autenticar(String email, String senha) {
        Usuario u = repo.buscarPorEmail(email);
        if (u != null && u.getSenha().equals(senha)) {
            return u;
        }
        return null;
    }
}
