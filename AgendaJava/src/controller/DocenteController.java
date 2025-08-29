package controller;

import model.Docente;
import repository.DocenteRepository;
import util.ImageUtil;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class DocenteController {

    private final DocenteRepository repo;

    public DocenteController() {
        this.repo = new DocenteRepository();
    }

    public List<Docente> listar() {
        return repo.listar();
    }

    public Docente criar(String nome, String email, String cargo, File fotoOrigem) throws Exception {
        validarCampos(nome, email, cargo, fotoOrigem);

        // copia foto para /data/fotos/<uuid>.png
        String novoNome = UUID.randomUUID() + ".png";
        File destino = new File(repo.getFotosDir(), novoNome);
        ImageUtil.copiarRedimensionandoQuadrado(fotoOrigem, destino, 300);

        Docente d = new Docente(UUID.randomUUID().toString(), nome, email, cargo, destino.getAbsolutePath());
        repo.inserir(d);
        return d;
    }

    public void atualizar(String id, String nome, String email, String cargo, File novaFotoOuNull) throws Exception {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID inválido.");
        validarCampos(nome, email, cargo, (novaFotoOuNull == null) ? new File("dummy.png") : novaFotoOuNull, true);

        Docente existente = repo.listar().stream().filter(x -> x.getId().equals(id)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Docente não encontrado"));

        if (novaFotoOuNull != null) {
            File destino = new File(repo.getFotosDir(), UUID.randomUUID() + ".png");
            ImageUtil.copiarRedimensionandoQuadrado(novaFotoOuNull, destino, 300);
            existente.setCaminhoFoto(destino.getAbsolutePath());
        }
        existente.setNome(nome);
        existente.setEmail(email);
        existente.setCargo(cargo);
        repo.atualizar(existente);
    }

    public void excluir(String id) {
        repo.excluir(id);
    }

    private void validarCampos(String nome, String email, String cargo, File foto, boolean fotoOpcional) {
        if (nome == null || nome.isBlank() || email == null || email.isBlank() || cargo == null || cargo.isBlank()) {
            throw new IllegalArgumentException("Preencha todos os campos.");
        }
        if (!email.contains("@")) throw new IllegalArgumentException("Email inválido.");
        if (!fotoOpcional) {
            if (foto == null || !foto.exists()) throw new IllegalArgumentException("Selecione uma foto.");
        }
    }

    private void validarCampos(String nome, String email, String cargo, File foto) {
        validarCampos(nome, email, cargo, foto, false);
    }
}
