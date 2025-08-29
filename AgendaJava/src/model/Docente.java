package model;

import java.io.Serializable;
import java.util.Objects;

public class Docente implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;          // UUID
    private String nome;
    private String email;
    private String cargo;
    private String caminhoFoto; // caminho absoluto da foto salva em /data/fotos

    public Docente(String id, String nome, String email, String cargo, String caminhoFoto) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cargo = cargo;
        this.caminhoFoto = caminhoFoto;
    }

    public Docente() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public String getCaminhoFoto() { return caminhoFoto; }
    public void setCaminhoFoto(String caminhoFoto) { this.caminhoFoto = caminhoFoto; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Docente)) return false;
        Docente docente = (Docente) o;
        return Objects.equals(id, docente.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return nome + " - " + cargo;
    }
}
