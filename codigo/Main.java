package codigo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Cardapio cardapio = new Cardapio();
        Restaurante restaurante = new Restaurante(cardapio);
        List<Requisicao> filaDeEspera = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenu();

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o nextInt

            switch (opcao) {
                case 1:
                    atenderCliente(restaurante, filaDeEspera, scanner);
                    break;

                case 2:
                    verFilaDeEspera(filaDeEspera);
                    break;

                case 3:
                    servirCliente(restaurante, filaDeEspera, scanner);
                    break;

                case 4:
                    encerrarAtendimento(restaurante, filaDeEspera, scanner);
                    break;

                case 5:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void exibirMenu() {
        System.out.println("Menu:");
        System.out.println("1. Atender Cliente");
        System.out.println("2. Ver Fila de Espera");
        System.out.println("3. Servir Cliente");
        System.out.println("4. Encerrar Atendimento de Cliente");
        System.out.println("5. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void atenderCliente(Restaurante restaurante, List<Requisicao> filaDeEspera, Scanner scanner) {
        System.out.print("Digite o ID do cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após o nextInt

        System.out.print("Digite o nome do cliente: ");
        String nomeCliente = scanner.nextLine();

        Cliente cliente = new Cliente(clienteId, nomeCliente);
        restaurante.registrarCliente(cliente);

        System.out.print("Digite o número de pessoas: ");
        int numeroDePessoas = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após o nextInt

        Optional<Mesa> mesaOpt = restaurante.buscarMesaDisponivel(numeroDePessoas);
        if (mesaOpt.isPresent()) {
            Requisicao requisicao = restaurante.criarRequisicao(mesaOpt.get().getId(), cliente.getId(),
                    numeroDePessoas);
            restaurante.adicionarRequisicao(requisicao);
            System.out.println("Cliente atendido e sentado na mesa " + mesaOpt.get().getId());
            System.out.println(cliente.getId());
            System.out.println(requisicao.toString());
        } else {
            filaDeEspera.add(new Requisicao(null, cliente, numeroDePessoas));
            System.out.println("Nenhuma mesa disponível no momento. Cliente adicionado à fila de espera.");
        }
    }

    private static void verFilaDeEspera(List<Requisicao> filaDeEspera) {
        System.out.println("Fila de Espera:");
        filaDeEspera.forEach(System.out::println);
    }

    private static void servirCliente(Restaurante restaurante, List<Requisicao> filaDeEspera, Scanner scanner) {
        System.out.println("Cardápio:");
        restaurante.exibirCardapio();
    
        System.out.print("Digite o ID do cliente a ser servido: ");
        int clienteIdParaServir = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após o nextInt
    
        boolean clienteServido = false;
    
        // Verificar na fila de espera
        Optional<Requisicao> requisicaoOpt = filaDeEspera.stream()
                .filter(req -> req.getCliente().getId() == clienteIdParaServir)
                .findFirst();
    
        if (requisicaoOpt.isPresent()) {
            Requisicao requisicao = requisicaoOpt.get();
    
            System.out.print("Digite o ID do item a ser adicionado ao pedido: ");
            int itemId = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha após o nextInt
    
            try {
                requisicao.adicionarItem(itemId);
                System.out.println("Item adicionado ao pedido.");
                clienteServido = true;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        } else {
            // Verificar em todas as mesas do restaurante se o cliente está alocado
            Optional<Requisicao> requisicaoMesaOpt = restaurante.getRequisicoesEmMesas().stream()
                    .filter(r -> r.getCliente().getId() == clienteIdParaServir)
                    .findFirst();
    
            if (requisicaoMesaOpt.isPresent()) {
                Requisicao requisicao = requisicaoMesaOpt.get();
    
                System.out.print("Digite o ID do item a ser adicionado ao pedido: ");
                int itemId = scanner.nextInt();
                scanner.nextLine(); // Consumir a quebra de linha após o nextInt
    
                try {
                    requisicao.adicionarItem(itemId);
                    System.out.println("Item adicionado ao pedido.");
                    clienteServido = true;
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    
        if (!clienteServido) {
            System.out.println("Cliente não encontrado ou não está alocado em uma mesa.");
        }
    }
    
    private static void encerrarAtendimento(Restaurante restaurante, List<Requisicao> filaDeEspera, Scanner scanner) {
        System.out.print("Digite o ID do cliente a encerrar atendimento: ");
        int clienteIdParaEncerrar = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha após o nextInt

        Optional<Requisicao> requisicaoParaEncerrarOpt = filaDeEspera.stream()
                .filter(r -> r.getCliente().getId() == clienteIdParaEncerrar)
                .findFirst();

        if (requisicaoParaEncerrarOpt.isPresent()) {
            Requisicao requisicaoParaEncerrar = requisicaoParaEncerrarOpt.get();
            restaurante.encerrarAtendimento(requisicaoParaEncerrar);
            filaDeEspera.remove(requisicaoParaEncerrar);
            System.out.println("Atendimento encerrado para o cliente.");
        } else {
            System.out.println("Cliente não encontrado na fila de espera.");
        }
    }
}
