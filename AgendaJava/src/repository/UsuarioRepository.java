package repository;

import model.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {

    private static final String DB_FILE = "data/usuarios.dat";
    private final List<Usuario> cache = new ArrayList<>();

    public UsuarioRepository() {
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
                cache.addAll((List<Usuario>) obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvar() {
        File dir = new File("data");
        if (!dir.exists()) dir.mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DB_FILE))) {
            oos.writeObject(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void inserir(Usuario u) {
        cache.add(u);
        salvar();
    }

    public List<Usuario> listar() {
        return new ArrayList<>(cache);
    }

    public Usuario buscarPorEmail(String email) {
        return cache.stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
