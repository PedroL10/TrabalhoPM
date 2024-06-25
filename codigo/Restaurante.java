package codigo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Restaurante {
    private final List<Mesa> mesas;
    private final List<Cliente> clientes;
    private final Cardapio cardapio;
    private final Queue<Requisicao> filaDeEspera;
    private final List<Requisicao> requisicoesAtivas;

    public Restaurante(Cardapio cardapio) {
        this.cardapio = cardapio;
        this.clientes = new ArrayList<>();
        this.mesas = new ArrayList<>();
        this.filaDeEspera = new LinkedList<>();
        this.requisicoesAtivas = new ArrayList<>();
        inicializarMesas();
    }

    private void inicializarMesas() {
        mesas.addAll(criarMesas(1, 4, 4));
        mesas.addAll(criarMesas(5, 8, 6));
        mesas.addAll(criarMesas(9, 10, 8));
    }

    private List<Mesa> criarMesas(int idInicio, int idFim, int capacidade) {
        return IntStream.rangeClosed(idInicio, idFim)
                .mapToObj(id -> new Mesa(id, capacidade))
                .collect(Collectors.toList());
    }

    public void registrarCliente(Cliente cliente) {
        this.clientes.add(cliente);
    }

    public Optional<Mesa> buscarMesaDisponivel(int capacidade) {
        return mesas.stream()
                .filter(m -> m.isDisponivel() && m.getCapacidade() >= capacidade)
                .findFirst();
    }

    public Requisicao criarRequisicao(int mesaId, int clienteId, int numeroDePessoas) {
        Optional<Mesa> mesaOpt = mesas.stream()
                .filter(m -> m.getId() == mesaId && m.isDisponivel())
                .findFirst();
        Optional<Cliente> clienteOpt = clientes.stream()
                .filter(c -> c.getId() == clienteId)
                .findFirst();

        if (mesaOpt.isPresent() && clienteOpt.isPresent()) {
            Mesa mesa = mesaOpt.get();
            Cliente cliente = clienteOpt.get();
            mesa.setDisponivel(false);
            Requisicao requisicao = new Requisicao(mesa, cliente, numeroDePessoas);
            requisicoesAtivas.add(requisicao);
            return requisicao;
        } else {
            throw new IllegalArgumentException("Mesa ou Cliente não encontrados ou indisponíveis.");
        }
    }

    public void verificarRequisicoes() {
        while (!filaDeEspera.isEmpty()) {
            Requisicao proximaRequisicao = filaDeEspera.poll();
            Optional<Mesa> mesaOpt = buscarMesaDisponivel(proximaRequisicao.getNumeroDePessoas());
            if (mesaOpt.isPresent()) {
                Mesa mesa = mesaOpt.get();
                atenderRequisicao(mesa, proximaRequisicao);
            } else {
                filaDeEspera.offer(proximaRequisicao);
                break;
            }
        }
    }

    private void atenderRequisicao(Mesa mesa, Requisicao requisicao) {
        mesa.setDisponivel(false);
        requisicao.setMesa(mesa);
        requisicoesAtivas.add(requisicao);
    }

    public void adicionarRequisicao(Requisicao requisicao) {
        filaDeEspera.offer(requisicao);
        verificarRequisicoes();
    }

    public List<Mesa> getMesas() {
        return mesas;
    }

    public void exibirCardapio() {
        cardapio.exibirItens();
    }

    public Optional<Cliente> buscarClientePorId(int clienteId) {
        return clientes.stream()
                .filter(c -> c.getId() == clienteId)
                .findFirst();
    }

    public Optional<Mesa> buscarMesaPorId(int mesaId) {
        return mesas.stream()
                .filter(m -> m.getId() == mesaId)
                .findFirst();
    }

    public void adicionarItemAoPedido(Requisicao requisicao, int itemId) {
        Optional<Item> itemOpt = cardapio.buscarItemPorId(itemId);
        if (itemOpt.isPresent()) {
            requisicao.adicionarItemAoPedido(itemOpt.get());
        } else {
            throw new IllegalArgumentException("Item não encontrado no cardápio.");
        }
    }

    public List<Requisicao> getRequisicoesEmMesas() {
        return requisicoesAtivas.stream()
                .filter(requisicao -> requisicao.getMesa() != null)
                .collect(Collectors.toList());
    }

    public void encerrarAtendimento(Requisicao requisicao) {
        requisicao.getMesa().setDisponivel(true);
        requisicao.encerrar();
        requisicoesAtivas.remove(requisicao);
        System.out.println(requisicao.gerarRelatorio());
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "mesas=" + mesas +
                ", clientes=" + clientes +
                ", cardapio=" + cardapio +
                '}';
    }
}
