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

    @Override
    public String toString() {
        return "Pedido{" +
               "itens=" + itens +
               '}';
    }
}
