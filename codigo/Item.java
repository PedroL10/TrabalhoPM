package codigo;

public class Item {

    private String nome;
    private double preco;
    private int identificador;

    public Item(String nome, double preco, int identificador) {
        this.nome = nome;
        this.preco = preco;
        this.identificador = identificador;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getIdentificador() {
        return identificador;
    }

}