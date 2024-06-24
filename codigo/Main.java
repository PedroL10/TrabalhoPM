package codigo;

import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Cardapio cardapio = new Cardapio();
        Restaurante restaurante = new Restaurante(cardapio);
     
        int opcao;

        do {
            System.out.println("Menu:");
            System.out.println("1. Atender Cliente");
            System.out.println("2. Ver Fila de Espera");
            System.out.println("3. Servir Cliente");
            System.out.println("4. Encerrar Atendimento de Cliente");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome do Cliente: ");
                    String nome = scanner.nextLine();
                    System.out.print("Número de Pessoas: ");
                    int numeroPessoas = scanner.nextInt();
                    scanner.nextLine();
                    try {
                        Cliente cliente = new Cliente(nome);
                        Requisicao requisicao = new Requisicao(numeroPessoas, cliente);
                        restaurante.adicionarRequisicao(requisicao);
                        if (requisicao.getMesa() != null) {
                            System.out.println("Cliente " + cliente.getNome() + " adicionado com sucesso! Mesa: "
                                    + requisicao.getMesa().getIdMesa());
                        } else {
                            System.out.println("Cliente " + cliente.getNome() + " adicionado à fila de espera.");
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("Fila de Espera:");
                    Queue<Requisicao> filaEspera = restaurante.getFilaEspera();
                    if (filaEspera.isEmpty()) {
                        System.out.println("Nenhum cliente na fila de espera.");
                    } else {
                        for (Requisicao req : filaEspera) {
                            System.out.println("Cliente: " + req.getCliente().getNome() + ", Número de Pessoas: "
                                    + req.getQuantidadeDePessoas());
                        }
                    }
                    break;
                case 3:
                    System.out.println("Escolha uma mesa para servir o cliente:");
                    int numeroMesa = scanner.nextInt();
                    scanner.nextLine();

                    if (!restaurante.verificarMesaExistente(numeroMesa)) {
                        System.out.println("Mesa não existe.");
                        break;
                    }

                    if (!restaurante.verificarMesaOcupada(numeroMesa)) {
                        System.out.println("A mesa escolhida não está ocupada.");
                        break;
                    }

                    System.out.println("Opções do Cardápio:");
                    List<Item> itensCardapio = restaurante.obterItensCardapio();
                    for (Item item : itensCardapio) {
                        System.out
                                .println(item.getIdentificador() + ". " + item.getNome() + " - R$ " + item.getPreco());
                    }

                    Pedido pedido = new Pedido(false);
                    boolean continuarPedindo = true;
                    // tirar esse do while
                    do {
                        System.out.println("Digite o código do item desejado (0 para encerrar):");
                        int codigoItem = scanner.nextInt();
                        scanner.nextLine();

                        if (codigoItem == 0) {
                            continuarPedindo = false;
                        } else {
                            Item item = restaurante.obterItemCardapio(codigoItem);
                            if (item != null) {
                                pedido.pedirItem(item);
                                System.out.println("Item adicionado ao pedido: " + item.getNome());
                            } else {
                                System.out.println("Item não encontrado.");
                            }
                        }
                    } while (continuarPedindo);

                    restaurante.servirCliente(numeroMesa, pedido);
                    System.out.println("Pedido realizado com sucesso.");
                    break;

                case 4:
                    System.out.println("Mesas Ocupadas:");
                    List<Mesa> mesasOcupadas = restaurante.getMesasOcupadas();
                    if (mesasOcupadas.isEmpty()) {
                        System.out.println("Nenhuma mesa ocupada.");
                    } else {
                        for (int i = 0; i < mesasOcupadas.size(); i++) {
                            Mesa mesaOcupada = mesasOcupadas.get(i);
                            Requisicao req = mesaOcupada.getRequisicaoAtual();
                            System.out.println((i + 1) + ". Mesa para " + mesaOcupada.getQuantidadeDeCadeiras()
                                    + " pessoas ocupada por " + req.getCliente().getNome());
                        }
                        System.out.print("Escolha uma mesa para liberar (1-" + mesasOcupadas.size() + "): ");
                        int indiceMesa = scanner.nextInt();
                        scanner.nextLine();
                        if (indiceMesa > 0 && indiceMesa <= mesasOcupadas.size()) {
                            Mesa mesaEscolhida = mesasOcupadas.get(indiceMesa - 1);
                            Requisicao req = mesaEscolhida.getRequisicaoAtual();
                            if (req != null) {
                                restaurante.liberarMesa(mesaEscolhida);
                                System.out.println("Mesa liberada com sucesso!");
                                System.out.println("Relatório de Atendimento:");
                                System.out.println(req.relatorioAtendimento());
                                List<Pedido> pedidos = req.getPedidos();
                                for (Pedido p : pedidos) {
                                    System.out.println("Pedido:");
                                    for (Item item : p.getItemsEscolhidos()) {
                                        System.out.println(" - " + item.getNome() + ", Preço: R$" + item.getPreco());
                                    }
                                    System.out.println("Menu fechado: " + p.isMenuFechado());
                                }
                            } else {
                                System.out.println("Nenhuma requisição encontrada para esta mesa.");
                            }
                        } else {
                            System.out.println("Opção inválida.");
                        }
                    }
                    break;
                case 5:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 5);

        scanner.close();
    }
}