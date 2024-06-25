package codigo;

import java.time.LocalDateTime;

public class Requisicao {
    private Mesa mesa;
    private final Cliente cliente;
    private final int numeroDePessoas;
    private final Pedido pedido;
    private final LocalDateTime chegada;
    private LocalDateTime saida;

    public Requisicao(Mesa mesa, Cliente cliente, int numeroDePessoas) {
        this.mesa = mesa;
        this.cliente = cliente;
        this.numeroDePessoas = numeroDePessoas;
        this.pedido = new Pedido();
        this.chegada = LocalDateTime.now();
        this.saida = null;
    }

    // Adicione este método
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getNumeroDePessoas() {
        return numeroDePessoas;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public LocalDateTime getChegada() {
        return chegada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public void adicionarItemAoPedido(Item item) {
        pedido.adicionarItem(item);
    }

    @Override
    public String toString() {
        return "Requisicao{" +
                "mesa=" + mesa +
                ", cliente=" + cliente +
                ", numeroDePessoas=" + numeroDePessoas +
                ", pedido=" + pedido +
                ", chegada=" + chegada +
                ", saida=" + saida +
                '}';
    }
}
