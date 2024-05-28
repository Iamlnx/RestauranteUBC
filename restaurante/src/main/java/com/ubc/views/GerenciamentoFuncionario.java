package com.ubc.views;

import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ubc.models.Funcionario;

public class GerenciamentoFuncionario {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Scanner sc;

    public GerenciamentoFuncionario() {
        emf = Persistence.createEntityManagerFactory("RestauranteUBC");
        em = emf.createEntityManager();
        sc = new Scanner(System.in);
    }

    private void listarFuncionarios() {
        em.getTransaction().begin();

        List<Funcionario> funcionarios = em.createQuery("SELECT f FROM Funcionario f", Funcionario.class)
                                .getResultList();

        em.getTransaction().commit();

        System.out.println("Listagem de Funcionários:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("---------------------------------------");
            System.out.println("ID: " + funcionario.getFuncionarioID());
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Cargo: " + funcionario.getCargo());
            System.out.println("---------------------------------------");
        }
    }

    private void criarNovoFuncionario() {
        sc.nextLine();
        System.out.println("Digite o nome do funcionário: ");
        String nome = sc.nextLine();
        System.out.println("Digite o cargo do funcionário: ");
        String cargo = sc.nextLine();

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(nome);
        funcionario.setCargo(cargo);

        em.getTransaction().begin();
        em.persist(funcionario);
        em.getTransaction().commit();
    }

    private void atualizarFuncionario() {
        System.out.print("Digite o ID do funcionário a ser atualizado: ");
        int funcionarioID = sc.nextInt();
    
        Funcionario funcionario = em.find(Funcionario.class, funcionarioID);
    
        if (funcionario == null) {
            System.out.println("Funcionário com ID " + funcionarioID + " não encontrado.");
            return;
        }

        sc.nextLine();

        System.out.print("Digite o novo nome do funcionário: ");
        String novoNome = sc.nextLine();
        
        System.out.print("Digite o novo cargo do funcionário: ");
        String novoCargo = sc.nextLine();
    
        funcionario.setNome(novoNome);
        funcionario.setCargo(novoCargo);
    
        em.getTransaction().begin();
        em.merge(funcionario);
        em.getTransaction().commit();
    
        System.out.println("Funcionário com ID " + funcionarioID + " atualizado com sucesso.");
    }

    private void deletarFuncionario() {
        System.out.print("Digite o ID do funcionário a ser deletado: ");
        int funcionarioID = sc.nextInt();

        Funcionario funcionario = em.find(Funcionario.class, funcionarioID);

        if (funcionario == null) {
            System.out.println("Funcionário com ID " + funcionarioID + " não encontrado.");
            return;
        }

        em.getTransaction().begin();
        em.remove(funcionario);
        em.getTransaction().commit();
    
        System.out.println("Funcionário com ID " + funcionarioID + " deletado com sucesso.");
    }

    public void gerenciarFuncionarios() {
        Integer opcao;

        do {
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.println("//                                                                      //");
            System.out.println("//                  O que deseja fazer?                                  //");
            System.out.println("//                                                                      //");
            System.out.println("//           (1) - Listar Funcionários                                   //");
            System.out.println("//           (2) - Criar Novo Funcionário                                //");
            System.out.println("//           (3) - Atualizar Funcionário                                 //");
            System.out.println("//           (4) - Deletar Funcionário                                   //");
            System.out.println("//           (0) - Voltar                                                //");
            System.out.println("//                                                                      //");
            System.out.println("//                                                                      //");
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.print("Qual opção deseja executar: ");
            opcao = sc.nextInt();

            sc.nextLine();

            switch (opcao) {
                case 1:
                    listarFuncionarios();
                    break;
                case 2:
                    criarNovoFuncionario();
                    break;
                case 3:
                    atualizarFuncionario();
                    break;
                case 4:
                    deletarFuncionario();
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
