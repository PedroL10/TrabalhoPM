package codigo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public void encerrar() {
        this.saida = LocalDateTime.now();
    }

    public void adicionarItemAoPedido(Item item) {
        pedido.adicionarItem(item);
    }

    public String gerarRelatorio() {
        double valorTotal = pedido.calcularValorTotalComTaxa();
        double valorPorPessoa = pedido.calcularValorPorPessoa(numeroDePessoas);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return "Relatório da Requisição:\n" +
               "Data de Chegada: " + chegada.format(formatter) + "\n" +
               "Cliente: " + cliente.getNome() + "\n" +
               "Quantidade de Pessoas: " + numeroDePessoas + "\n" +
               "Itens Pedidos:\n" + pedido.listarItens() +
               "Valor da Conta (com 10% de taxa): R$ " + String.format("%.2f", valorTotal) + "\n" +
               "Valor da Conta por Pessoa: R$ " + String.format("%.2f", valorPorPessoa) + "\n" +
               "Horário de Saída: " + (saida != null ? saida.format(formatter) : "N/A") + "\n";
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
