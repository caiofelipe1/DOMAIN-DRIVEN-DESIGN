package repository;

import model.Docente;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DocenteRepository {

    private static final String DATA_DIR = "data";
    private static final String FOTOS_DIR = DATA_DIR + File.separator + "fotos";
    private static final String DB_FILE = DATA_DIR + File.separator + "docentes.dat";

    private final List<Docente> cache = new ArrayList<>();

    public DocenteRepository() {
        try {
            Files.createDirectories(Paths.get(FOTOS_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar diretórios de dados.", e);
        }
        carregar();
    }

    @SuppressWarnings("unchecked")
    private void carregar() {
        cache.clear();
        File f = new File(DB_FILE);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                cache.addAll((List<Docente>) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DB_FILE))) {
            oos.writeObject(cache);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar base de dados.", e);
        }
    }

    // CRUD
    public List<Docente> listar() {
        return new ArrayList<>(cache);
    }

    public void inserir(Docente d) {
        cache.add(d);
        salvar();
    }

    public void atualizar(Docente d) {
        Optional<Docente> opt = cache.stream().filter(x -> x.getId().equals(d.getId())).findFirst();
        if (opt.isPresent()) {
            Docente ref = opt.get();
            ref.setNome(d.getNome());
            ref.setEmail(d.getEmail());
            ref.setCargo(d.getCargo());
            ref.setCaminhoFoto(d.getCaminhoFoto());
            salvar();
        } else {
            throw new IllegalArgumentException("Docente não encontrado: " + d.getId());
        }
    }

    public void excluir(String id) {
        cache.removeIf(x -> x.getId().equals(id));
        salvar();
    }

    // Utilitários para fotos
    public String getFotosDir() { return FOTOS_DIR; }
}
