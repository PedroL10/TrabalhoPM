package codigo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

class Requisicao {
    private LocalDateTime chegada;
    private LocalDateTime saida;
    private int quantidadeDePessoas;
    private Cliente cliente;
    private Mesa mesa;

    private List<Pedido> pedidos;

    // como validar qtd de pessoas?
    public Requisicao(int quantidadeDePessoas, Cliente cliente) {
        if (quantidadeDePessoas > 8) {
            throw new IllegalArgumentException("A quantidade de pessoas não pode ser maior que 8.");
        }
        this.quantidadeDePessoas = quantidadeDePessoas;
        this.chegada = LocalDateTime.now();
        this.cliente = cliente;
        this.pedidos = new ArrayList<>();
    }

    public int getQuantidadeDePessoas() {
        return quantidadeDePessoas;
    }

    public void setQuantidadeDePessoas(int quantidadeDePessoas) {
        this.quantidadeDePessoas = quantidadeDePessoas;
    }

    public LocalDateTime getChegada() {
        return chegada;
    }

    public void setChegada(LocalDateTime chegada) {
        this.chegada = chegada;
    }

    public LocalDateTime getSaida() {
        return saida;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public void setSaida(LocalDateTime saida) {
        this.saida = saida;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void adicionarPedido(Pedido pedido) {
        pedidos.add(pedido);
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void encerrarRequisicao() {
        saida = LocalDateTime.now();
    }

    public String relatorioAtendimento() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("Horário de Chegada: ").append(chegada.format(formatter)).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append("\n");
        sb.append("Horário de Saída: ").append(saida.format(formatter)).append("\n");

        if (mesa != null && mesa.getPedido() != null) {
            Pedido pedido = mesa.getPedido();
            List<Item> itens = pedido.getItemsEscolhidos();
            double total = pedido.valorAPagar();
            double totalPorPessoa = pedido.calcularValorPorPessoa(quantidadeDePessoas);

            sb.append("Itens do Pedido:\n");
            for (Item item : itens) {
                sb.append("- ").append(item.getNome()).append(" - R$ ").append(item.getPreco()).append("\n");
            }

            sb.append("Total do Pedido: R$ ").append(total).append("\n");
            sb.append("Total por Pessoa: R$ ").append(String.format("%.2f", totalPorPessoa)).append("\n");
        }

        return sb.toString();
    }

}