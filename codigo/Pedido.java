package codigo;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private final List<Item> itens;

    public Pedido() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item item) {
        itens.add(item);
    }

    public double calcularValorTotal() {
        return itens.stream()
                    .mapToDouble(Item::getPreco)
                    .sum();
    }

    public double calcularValorTotalComTaxa() {
        double valorTotal = calcularValorTotal();
        return valorTotal + (valorTotal * 0.10); 
    }

    public double calcularValorPorPessoa(int numeroDePessoas) {
        return calcularValorTotalComTaxa() / numeroDePessoas;
    }

    public String listarItens() {
        StringBuilder builder = new StringBuilder();
        itens.forEach(item -> {
            builder.append(item.getDescricao()).append(" - R$ ").append(String.format("%.2f", item.getPreco())).append("\n");
        });
        return builder.toString();
    }

    @Override
    public String toString() {
        return "Pedido{" +
               "itens=" + itens +
               '}';
    }
}
