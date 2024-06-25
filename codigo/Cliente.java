package codigo;

public class Cliente {
    private final int id;
    private final String nome;

    public Cliente(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Cliente{" +
               "id=" + id +
               ", nome='" + nome + '\'' +
               '}';
    }
}
