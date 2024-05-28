package com.ubc.views;

import java.math.BigDecimal;
import java.security.Key;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ubc.models.Cliente;
import com.ubc.models.Funcionario;
import com.ubc.models.ItemDoPedido;
import com.ubc.models.Pedidos;

public class GerenciamentoPedido {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Scanner sc;
    private final String chaveCriptografia = "qwertyuiopasdfghjklzxcvbnm123456";

    private String descriptografar(String textoCriptografado) throws Exception {
        Key chave = new SecretKeySpec(chaveCriptografia.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(textoCriptografado));
        return new String(decrypted);
    }

    public GerenciamentoPedido() {
        emf = Persistence.createEntityManagerFactory("RestauranteUBC");
        em = emf.createEntityManager();
        sc = new Scanner(System.in);
    }

    private void listarPedidos() throws Exception {
        em.getTransaction().begin();

        List<Pedidos> pedidos = em.createQuery("SELECT p FROM Pedidos p", Pedidos.class)
                                .getResultList();

        em.getTransaction().commit();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Listagem de Pedidos:");
        for (Pedidos pedido : pedidos) {

            List<ItemDoPedido> itens = em.createQuery("SELECT i FROM ItemDoPedido i JOIN i.pedido p WHERE p.PedidoID = :pedidoId", ItemDoPedido.class).setParameter("pedidoId", pedido.getPedidoID()).getResultList();
            BigDecimal valorTotal = BigDecimal.valueOf(0);

            System.out.println("---------------------------------------");
            System.out.println("ID do Pedido: " + pedido.getPedidoID());
            System.out.println("Cliente: " + descriptografar(pedido.getCliente().getNome()));
            System.out.println("Funcionário: " + pedido.getFuncionario().getNome());
            System.out.println("Data do Pedido: " + pedido.getDataDoPedido().format(formatter));
            System.out.println("Status: " + pedido.getStatus());
            Integer i = 1;
            if(itens.size() > 0){
                System.out.println("--------Itens---------");
                for (ItemDoPedido item : itens){
                    System.out.println("--------Item " + i + "--------");
                    System.out.println("Prato: " + item.getPrato().getNome());
                    System.out.println("Quantidade: " + item.getQuantidade());
                    System.out.println("Valor: R$" + item.getPrecoTotal());
                    valorTotal = valorTotal.add(item.getPrecoTotal());
                    System.out.println("----------------------");
                    i++;
                }
                System.out.println("------Fim Itens------");
            } else {
                System.out.println("O pedido ainda não possui nenhum item!");
            }
            System.out.println("Valor total: R$" + valorTotal);
            System.out.println("---------------------------------------");
        }
    }

    private void adicionarNovoPedido() {
        sc.nextLine();
        System.out.println("Digite o ID do Cliente: ");
        int clienteID = sc.nextInt();
        System.out.println("Digite o ID do Funcionário: ");
        int funcionarioID = sc.nextInt();
        System.out.println("Digite a data do pedido (no formato DD/MM/YYYY): ");
        String dataPedidoStr = sc.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataPedido = LocalDate.parse(dataPedidoStr, formatter);

        Pedidos pedido = new Pedidos();
        // Recuperando o cliente e funcionário correspondentes aos IDs fornecidos
        pedido.setCliente(em.find(Cliente.class, clienteID));
        pedido.setFuncionario(em.find(Funcionario.class, funcionarioID));
        pedido.setDataDoPedido(dataPedido);
        pedido.setStatus("Novo");

        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    private void atualizarPedido() {
        System.out.print("Digite o ID do Pedido a ser atualizado: ");
        int pedidoID = sc.nextInt();

        Pedidos pedido = em.find(Pedidos.class, pedidoID);

        if (pedido == null) {
            System.out.println("Pedido com ID " + pedidoID + " não encontrado.");
            return;
        }

        sc.nextLine();

        System.out.print("Digite a nova data do pedido (no formato DD-MM-YYYY): ");
        String novaDataPedidoStr = sc.next();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate novaDataPedido = LocalDate.parse(novaDataPedidoStr, formatter);

        pedido.setDataDoPedido(novaDataPedido);

        em.getTransaction().begin();
        em.merge(pedido);
        em.getTransaction().commit();

        System.out.println("Pedido com ID " + pedidoID + " atualizado com sucesso.");
    }

    private void excluirPedido() {
        System.out.print("Digite o ID do Pedido a ser excluído: ");
        int pedidoID = sc.nextInt();

        Pedidos pedido = em.find(Pedidos.class, pedidoID);

        if (pedido == null) {
            System.out.println("Pedido com ID " + pedidoID + " não encontrado.");
            return;
        }

        em.getTransaction().begin();
        em.remove(pedido);
        em.getTransaction().commit();

        System.out.println("Pedido com ID " + pedidoID + " excluído com sucesso.");
    }

    public void gerenciarPedidos() throws Exception {
        Integer opcao;

        do {
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.println("//                                                                      //");
            System.out.println("//                  O que deseja fazer?                                  //");
            System.out.println("//                                                                      //");
            System.out.println("//           (1) - Listar Pedidos                                        //");
            System.out.println("//           (2) - Adicionar Novo Pedido                                 //");
            System.out.println("//           (3) - Atualizar Pedido                                      //");
            System.out.println("//           (4) - Excluir Pedido                                        //");
            System.out.println("//           (0) - Voltar                                                //");
            System.out.println("//                                                                      //");
            System.out.println("//                                                                      //");
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.print("Qual opção deseja executar: ");
            opcao = sc.nextInt();

            sc.nextLine();

            switch (opcao) {
                case 1:
                    listarPedidos();
                    break;
                case 2:
                    adicionarNovoPedido();
                    break;
                case 3:
                    atualizarPedido();
                    break;
                case 4:
                    excluirPedido();
                    break;
                case 0:
                    System.out.println("Voltando....");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }
        } while (opcao != 0);
    }
}
