
package codigo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Restaurante {
    private List<Mesa> mesasDisponiveis;
    private List<Mesa> mesasOcupadas;
    private Queue<Requisicao> filaEspera;
    private Cardapio cardapio;

    public Restaurante(Cardapio cardapio) {
        mesasDisponiveis = new ArrayList<>();
        mesasOcupadas = new ArrayList<>();
        filaEspera = new LinkedList<>();
        this.cardapio = cardapio;
        inicializarMesas();
    }
    
    private void inicializarMesas() {   
        for (int i = 0; i < 4; i++) {
            mesasDisponiveis.add(new Mesa(4));
        }
        for (int i = 0; i < 4; i++) {
            mesasDisponiveis.add(new Mesa(6));
        }
        for (int i = 0; i < 2; i++) {
            mesasDisponiveis.add(new Mesa(8));
        }
    }

    public Queue<Requisicao> getFilaEspera() {
        return new LinkedList<>(filaEspera);
    }

    public List<Mesa> getMesasOcupadas() {
        return new ArrayList<>(mesasOcupadas);
    }
    
    public boolean mesaExiste(int numeroMesa) {
        for (Mesa mesa : mesasDisponiveis) {
            if (mesa.getIdMesa() == numeroMesa) {
                return true;
            }
        }
        for (Mesa mesa : mesasOcupadas) {
            if (mesa.getIdMesa() == numeroMesa) {
                return true;
            }
        }
        return false;
    }

    public void adicionarRequisicao(Requisicao requisicao) {
        Mesa mesaAdequada = encontrarMesaAdequada(requisicao.getQuantidadeDePessoas());
        if (mesaAdequada != null) {
            alocarMesa(mesaAdequada, requisicao);
        } else {
            filaEspera.offer(requisicao);
        }
    }
    
    private Mesa encontrarMesaAdequada(int numeroPessoas) {
        for (Mesa mesa : mesasDisponiveis) {
            if (mesa.getQuantidadeDeCadeiras() >= numeroPessoas) {
                return mesa;
            }
        }
        return null;
    }

    public boolean adicionarPedido(int numeroMesa, Pedido pedido) {
        for (Mesa mesa : mesasOcupadas) {
            if (mesa.getIdMesa() == numeroMesa) {
                mesa.setPedido(pedido);
                return true;
            }
        }
        return false;
    }

    private void alocarMesa(Mesa mesa, Requisicao requisicao) {
        mesa.ocuparMesa();
        mesa.setRequisicaoAtual(requisicao);
        requisicao.setMesa(mesa);
        mesasDisponiveis.remove(mesa);
        mesasOcupadas.add(mesa);
    }

    public void liberarMesa(Mesa mesa) {
        if (mesa.getRequisicaoAtual() != null) {
            mesa.getRequisicaoAtual().encerrarRequisicao();
            mesa.desocuparMesa();
            mesa.setRequisicaoAtual(null);
            mesasOcupadas.remove(mesa);
            mesasDisponiveis.add(mesa);

            Requisicao req = mesa.getRequisicaoAtual();
            if (req != null) {
                
                req.adicionarPedido(mesa.getPedido());
            }

            if (!filaEspera.isEmpty()) {
                Requisicao proximaRequisicao = filaEspera.poll();
                Mesa mesaAdequada = encontrarMesaAdequada(proximaRequisicao.getQuantidadeDePessoas());
                if (mesaAdequada != null) {
                    alocarMesa(mesaAdequada, proximaRequisicao);
                }
            }
        }
    }

    public Mesa encontrarMesaPorNumero(int numeroMesa) {
        for (Mesa mesa : mesasOcupadas) {
            if (mesa.getIdMesa() == numeroMesa) {
                return mesa;
            }
        }
        return null;
    }

    public List<Item> obterItensCardapio() {
        return cardapio.getItems();
    }

    public Item obterItemCardapio(int codigoItem) {
        return cardapio.getItem(codigoItem);
    }

    public void exibirMenu() {
        cardapio.exibirMenu();
    }

    public boolean verificarMesaExistente(int numeroMesa) {
        return mesaExiste(numeroMesa);
    }

    public boolean verificarMesaOcupada(int numeroMesa) {
        Mesa mesa = encontrarMesaPorNumero(numeroMesa);
        return mesa != null && mesa.isMesaOcupada();
    }

    public void servirCliente(int numeroMesa, Pedido pedido) {
        adicionarPedido(numeroMesa, pedido);
    }

    public void fazerPedido(int numeroMesa, Pedido pedido) {
        adicionarPedido(numeroMesa, pedido);
    }
   
    
}
