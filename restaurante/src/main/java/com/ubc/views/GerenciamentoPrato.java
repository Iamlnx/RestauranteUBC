package com.ubc.views;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.ubc.models.Prato;

public class GerenciamentoPrato {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Scanner sc;

    public GerenciamentoPrato() {
        emf = Persistence.createEntityManagerFactory("RestauranteUBC");
        em = emf.createEntityManager();
        sc = new Scanner(System.in);
    }

    private void listarPratos() {
        em.getTransaction().begin();

        List<Prato> pratos = em.createQuery("SELECT p FROM Prato p", Prato.class)
                                .getResultList();

        em.getTransaction().commit();

        System.out.println("Listagem de Pratos:");
        for (Prato prato : pratos) {
            System.out.println("---------------------------------------");
            System.out.println("ID do Prato: " + prato.getPratoID());
            System.out.println("Nome: " + prato.getNome());
            System.out.println("Preço: R$" + prato.getPreco());
            System.out.println("Descrição: " + prato.getDescricao());
            System.out.println("---------------------------------------");
        }
    }

    private void adicionarNovoPrato() {
        sc.nextLine();
        System.out.println("Digite o nome do prato: ");
        String nome = sc.nextLine();
        System.out.println("Digite o preço do prato: ");
        BigDecimal preco = sc.nextBigDecimal();
        sc.nextLine();
        System.out.println("Digite a descrição do prato: ");
        String descricao = sc.nextLine();

        Prato prato = new Prato();
        prato.setNome(nome);
        prato.setPreco(preco);
        prato.setDescricao(descricao);

        em.getTransaction().begin();
        em.persist(prato);
        em.getTransaction().commit();
    }

    private void atualizarPrato() {
        System.out.print("Digite o ID do Prato a ser atualizado: ");
        int pratoID = sc.nextInt();

        Prato prato = em.find(Prato.class, pratoID);

        if (prato == null) {
            System.out.println("Prato com ID " + pratoID + " não encontrado.");
            return;
        }

        sc.nextLine();

        System.out.print("Digite o novo nome do prato: ");
        String novoNome = sc.nextLine();
        System.out.print("Digite o novo preço do prato: ");
        BigDecimal novoPreco = sc.nextBigDecimal();
        sc.nextLine();
        System.out.print("Digite a nova descrição do prato: ");
        String novaDescricao = sc.nextLine();

        prato.setNome(novoNome);
        prato.setPreco(novoPreco);
        prato.setDescricao(novaDescricao);

        em.getTransaction().begin();
        em.merge(prato);
        em.getTransaction().commit();

        System.out.println("Prato com ID " + pratoID + " atualizado com sucesso.");
    }

    private void excluirPrato() {
        System.out.print("Digite o ID do Prato a ser excluído: ");
        int pratoID = sc.nextInt();

        Prato prato = em.find(Prato.class, pratoID);

        if (prato == null) {
            System.out.println("Prato com ID " + pratoID + " não encontrado.");
            return;
        }

        em.getTransaction().begin();
        em.remove(prato);
        em.getTransaction().commit();

        System.out.println("Prato com ID " + pratoID + " excluído com sucesso.");
    }

    public void gerenciarPratos() {
        Integer opcao;

        do {
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.println("//                                                                      //");
            System.out.println("//                  O que deseja fazer?                                 //");
            System.out.println("//                                                                      //");
            System.out.println("//           (1) - Listar Pratos                                        //");
            System.out.println("//           (2) - Adicionar Novo Prato                                 //");
            System.out.println("//           (3) - Atualizar Prato                                      //");
            System.out.println("//           (4) - Excluir Prato                                        //");
            System.out.println("//           (0) - Voltar                                               //");
            System.out.println("//                                                                      //");
            System.out.println("//                                                                      //");
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.print("Qual opção deseja executar: ");
            opcao = sc.nextInt();

            sc.nextLine();

            switch (opcao) {
                case 1:
                    listarPratos();
                    break;
                case 2:
                    adicionarNovoPrato();
                    break;
                case 3:
                    atualizarPrato();
                    break;
                case 4:
                    excluirPrato();
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
