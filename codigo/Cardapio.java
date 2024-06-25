package codigo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cardapio {
    private final List<Item> itens;

    public Cardapio(List<Item> itens) {
        this.itens = itens;
    }

        public Cardapio() {
        this.itens = new ArrayList<>();
        this.itens.add(new Item("Moqueca de palmito", 32.00, 1));
        this.itens.add(new Item("Falafel Assado", 20.00, 2));
        this.itens.add(new Item("Salada primavera com macarrão Konjac", 25.00, 3));
        this.itens.add(new Item("Escondidinho de Inhame", 18.00, 4));
        this.itens.add(new Item("Strogonoff de cogumelos", 35.00, 5));
        this.itens.add(new Item("Caçarola de legumes", 22.00, 6));
        this.itens.add(new Item("Agua", 3.00, 7));
        this.itens.add(new Item("Copo de Suco", 7.00, 8));
        this.itens.add(new Item("Refrigerante Organico", 7.00, 9));
        this.itens.add(new Item("Cerveja Vegana", 9.00, 10));
        this.itens.add(new Item("Taça de vinho vegano", 18.00, 11));
    }

    public void exibirItens() {
        itens.forEach(System.out::println);
    }

    public Optional<Item> buscarItemPorId(int id) {
        return itens.stream()
                .filter(item -> item.getId() == id)
                .findFirst();
    }
    @Override
    public String toString() {
        return "Cardapio{" +
               "itens=" + itens +
               '}';
    }
}
