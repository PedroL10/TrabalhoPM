package codigo;

public class Item {
    private final String descricao;
    private final double preco;
    private final int id;

    public Item(String descricao, double preco, int id) {
        if (descricao == null || descricao.isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia.");
        }
        if (preco < 0) {
            throw new IllegalArgumentException("Preço não pode ser negativo.");
        }
        this.descricao = descricao;
        this.preco = preco;
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getPreco() {
        return preco;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descricao + '\'' +
               " -  preco: R$ " + preco ;          
    }
}
