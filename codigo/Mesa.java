package codigo;

public class Mesa {
    private final int id;
    private final int capacidade;
    private boolean disponivel;

    public Mesa(int id, int capacidade) {
        this.id = id;
        this.capacidade = capacidade;
        this.disponivel = true;
    }

    public int getId() {
        return id;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    @Override
    public String toString() {
        return "Mesa{" +
               "id=" + id +
               ", capacidade=" + capacidade +
               ", disponivel=" + disponivel +
               '}';
    }
}
