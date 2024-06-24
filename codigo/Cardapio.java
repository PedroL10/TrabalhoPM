package codigo;

import java.util.ArrayList;
import java.util.List;

public class Cardapio {

    private List<Item> itens = new ArrayList<>();
    
    public Cardapio() {        
        itens.add(new Item("Moqueca de palmito", 32.00, 1));
        itens.add(new Item("Falafel Assado", 20.00, 2));
        itens.add(new Item("Salada primavera com macarrão Konjac", 25.00, 3));
        itens.add(new Item("Escondidinho de Inhame", 18.00, 4));
        itens.add(new Item("Strogonoff de cogumelos", 35.00, 5));
        itens.add(new Item("Caçarola de legumes", 22.00, 6));

        itens.add(new Item("Agua ", 3.00, 7));
        itens.add(new Item("Copo de Suco ", 7.00, 8));
        itens.add(new Item("Refrigerante Organico ", 7.00, 9));
        itens.add(new Item("Cerveja Vegana ", 9.00, 10));
        itens.add(new Item("Taça de vinho vegano ", 18.00, 11));
    }

    public Item getItem(int codigo) {
        for (Item item : itens) {
            if (item.getIdentificador() == codigo) {
                return item;
            }
        }
        return null;
    }

    public List<Item> getItems() {
        return itens;
    }

    public void exibirMenu() {
        System.out.println("Opções do Cardápio:");
        for (Item item : itens) {
            System.out.println(item.getIdentificador() + ". " + item.getNome() + " - R$ " + item.getPreco());
        }
    }

    
}