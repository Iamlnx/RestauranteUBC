package com.ubc.views;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ubc.models.ItemDoPedido;
import com.ubc.models.Pedidos;
import com.ubc.models.Prato;

public class GerenciamentoItemDoPedido {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Scanner sc;

    public GerenciamentoItemDoPedido() {
        emf = Persistence.createEntityManagerFactory("RestauranteUBC");
        em = emf.createEntityManager();
        sc = new Scanner(System.in);
    }

    private void listarItensDoPedido() {
        em.getTransaction().begin();

        List<ItemDoPedido> itensDoPedido = em.createQuery("SELECT i FROM ItemDoPedido i", ItemDoPedido.class)
                                .getResultList();

        em.getTransaction().commit();

        System.out.println("Listagem de Itens do Pedido:");
        for (ItemDoPedido item : itensDoPedido) {
            System.out.println("---------------------------------------");
            System.out.println("ID do Item: " + item.getItemDoPedidoID());
            System.out.println("ID do Pedido: " + item.getPedido().getPedidoID());
            System.out.println("Prato: " + item.getPrato().getNome());
            System.out.println("Quantidade: " + item.getQuantidade());
            System.out.println("Preço Total: R$" + item.getPrecoTotal());
            System.out.println("---------------------------------------");
        }
    }

    private void adicionarNovoItemAoPedido() {
        sc.nextLine();
        System.out.println("Digite o ID do Pedido: ");
        int pedidoID = sc.nextInt();
        System.out.println("Digite o ID do Prato: ");
        int pratoID = sc.nextInt();
        System.out.println("Digite a quantidade: ");
        int quantidade = sc.nextInt();
        BigDecimal precoPrato = em.find(Prato.class, pratoID).getPreco();
        BigDecimal precoTotal = precoPrato.multiply(BigDecimal.valueOf(quantidade));
        

        Pedidos novoPedido = em.find(Pedidos.class, pedidoID);
        Prato novoPrato = em.find(Prato.class, pratoID);

        if (novoPedido == null) {
            System.out.println("Pedido com ID " + pedidoID + " não encontrado.");
            return;
        }

        if (novoPrato == null) {
            System.out.println("Prato com ID " + pratoID + " não encontrado.");
            return;
        }

        ItemDoPedido item = new ItemDoPedido();

        item.setPedido(novoPedido);
        item.setPrato(novoPrato);
        item.setQuantidade(quantidade);
        item.setPrecoTotal(precoTotal);

        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();

        System.out.println("Item do Pedido com ID " + item.getItemDoPedidoID() + " criado com sucesso.");

    }

    private void atualizarItemDoPedido() {
        System.out.print("Digite o ID do Item do Pedido a ser atualizado: ");
        int itemID = sc.nextInt();
    
        ItemDoPedido item = em.find(ItemDoPedido.class, itemID);
    
        if (item == null) {
            System.out.println("Item do Pedido com ID " + itemID + " não encontrado.");
            return;
        }

        sc.nextLine();

        System.out.print("Digite o novo ID do Pedido: ");
        int novoPedidoID = sc.nextInt();
        
        System.out.print("Digite o novo ID do Prato: ");
        int novoPratoID = sc.nextInt();
        
        System.out.print("Digite a nova quantidade: ");
        int novaQuantidade = sc.nextInt();
        
        BigDecimal precoPrato = em.find(Prato.class, novoPratoID).getPreco();
        BigDecimal novoPrecoTotal = precoPrato.multiply(BigDecimal.valueOf(novaQuantidade));

        Pedidos novoPedido = em.find(Pedidos.class, novoPedidoID);
        Prato novoPrato = em.find(Prato.class, novoPratoID);

        if (novoPedido == null) {
            System.out.println("Pedido com ID " + novoPedidoID + " não encontrado.");
            return;
        }

        if (novoPrato == null) {
            System.out.println("Prato com ID " + novoPratoID + " não encontrado.");
            return;
        }

        item.setPedido(novoPedido);
        item.setPrato(novoPrato);
        item.setQuantidade(novaQuantidade);
        item.setPrecoTotal(novoPrecoTotal);

        em.getTransaction().begin();
        em.merge(item);
        em.getTransaction().commit();

        System.out.println("Item do Pedido com ID " + itemID + " atualizado com sucesso.");
    }

    private void deletarItemDoPedido() {
        System.out.print("Digite o ID do Item do Pedido a ser deletado: ");
        int itemID = sc.nextInt();

        ItemDoPedido item = em.find(ItemDoPedido.class, itemID);

        if (item == null) {
            System.out.println("Item do Pedido com ID " + itemID + " não encontrado.");
            return;
        }

        em.getTransaction().begin();
        em.remove(item);
        em.getTransaction().commit();
    
        System.out.println("Item do Pedido com ID " + itemID + " deletado com sucesso.");
    }

    public void gerenciarItensDoPedido() {
        Integer opcao;

        do {
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.println("//                                                                      //");
            System.out.println("//                  O que deseja fazer?                                  //");
            System.out.println("//                                                                      //");
            System.out.println("//           (1) - Listar Itens do Pedido                                //");
            System.out.println("//           (2) - Adicionar Novo Item ao Pedido                         //");
            System.out.println("//           (3) - Atualizar Item do Pedido                              //");
            System.out.println("//           (4) - Deletar Item do Pedido                                //");
            System.out.println("//           (0) - Voltar                                                //");
            System.out.println("//                                                                      //");
            System.out.println("//                                                                      //");
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.print("Qual opção deseja executar: ");
            opcao = sc.nextInt();

            sc.nextLine();

            switch (opcao) {
                case 1:
                    listarItensDoPedido();
                    break;
                case 2:
                    adicionarNovoItemAoPedido();
                    break;
                case 3:
                    atualizarItemDoPedido();
                    break;
                case 4:
                    deletarItemDoPedido();
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
