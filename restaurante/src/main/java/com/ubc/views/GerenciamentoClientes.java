package com.ubc.views;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ubc.models.Cliente;

public class GerenciamentoClientes {
    private EntityManagerFactory emf;
    private EntityManager em;
    private Scanner sc;
    private final String chaveCriptografia = "qwertyuiopasdfghjklzxcvbnm123456";

    public GerenciamentoClientes() {
        emf = Persistence.createEntityManagerFactory("RestauranteUBC");
        em = emf.createEntityManager();
        sc = new Scanner(System.in);
    }

    private String criptografar(String texto) throws Exception {
        Key chave = new SecretKeySpec(chaveCriptografia.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, chave);
        byte[] encrypted = cipher.doFinal(texto.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    private String descriptografar(String textoCriptografado) throws Exception {
        Key chave = new SecretKeySpec(chaveCriptografia.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, chave);
        byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(textoCriptografado));
        return new String(decrypted);
    }

    private void listarClientes() throws Exception {
        em.getTransaction().begin();
        List<Cliente> clientes = em.createQuery("SELECT c FROM Cliente c", Cliente.class)
                                .getResultList();
        em.getTransaction().commit();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Listagem de Clientes:");
        for (Cliente cliente : clientes) {
            System.out.println("---------------------------------------");
            System.out.println("ID: " + cliente.getClienteID());
            System.out.println("Nome: " + descriptografar(cliente.getNome()));
            System.out.println("Data de Cadastro: " + cliente.getDataDeCadastro().format(formatter));
            System.out.println("---------------------------------------");
        }
    }

    private void criarNovosClientes() throws Exception {
        sc.nextLine();
        System.out.println("Digite o nome do cliente: ");
        String nome = criptografar(sc.nextLine());
        System.out.println("Digite a data do cadastro (no formato DD-MM-YYYY): ");
        String dataCadastroStr = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataCadastro = LocalDate.parse(dataCadastroStr, formatter);

        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setDataDeCadastro(dataCadastro);

        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();
    }

    private void atulizarCliente() throws Exception {
        System.out.print("Digite o ID do cliente a ser atualizado: ");
        int clienteID = sc.nextInt();
        sc.nextLine(); // Consumir a quebra de linha
    
        Cliente cliente = em.find(Cliente.class, clienteID);
    
        if (cliente == null) {
            System.out.println("Cliente com ID " + clienteID + " não encontrado.");
            return;
        }
    
        System.out.print("Digite o novo nome do cliente: ");
        String novoNome = criptografar(sc.nextLine());
        
        System.out.print("Digite a nova data de cadastro (no formato DD/MM/YYYY): ");
        String novaDataDeCadastroStr = sc.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate novaDataDeCadastro = LocalDate.parse(novaDataDeCadastroStr, formatter);
    
        cliente.setNome(novoNome);
        cliente.setDataDeCadastro(novaDataDeCadastro);
    
        em.getTransaction().begin();
    
        em.merge(cliente);
    
        em.getTransaction().commit();
    
        System.out.println("Cliente com ID " + clienteID + " atualizado com sucesso.");
    }
    
    private void deletarCliente() {
        System.out.print("Digite o ID do cliente a ser deletado: ");
        int clienteID = sc.nextInt();
    
        Cliente cliente = em.find(Cliente.class, clienteID);
    
        if (cliente == null) {
            System.out.println("Cliente com ID " + clienteID + " não encontrado.");
            return;
        }
    
        em.getTransaction().begin();
    
        em.remove(cliente);
    
        em.getTransaction().commit();
    
        System.out.println("Cliente com ID " + clienteID + " deletado com sucesso.");
    }
    

    public void gerenciarClientes() {
        Integer item_cliente;

        do {
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.println("//                                                                      //");
            System.out.println("//                  O que deseja Fazer?                                  //");
            System.out.println("//                                                                      //");
            System.out.println("//           (1) -  Listar Clientes                                     //");
            System.out.println("//           (2) -  Criar Novos Clientes                                //");
            System.out.println("//           (3) -  Atualizar Clientes                                   //");
            System.out.println("//           (4) -  Deletar Clientes                                    //");
            System.out.println("//           (0) -  Voltar                                              //");
            System.out.println("//                                                                      //");
            System.out.println("//                                                                      //");
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.print("Qual opção Deseja Executar: ");
            item_cliente = sc.nextInt();

            try {
                switch (item_cliente) {
                    case 1:
                        listarClientes();
                        break;
                    case 2:
                        criarNovosClientes();
                        break;
                    case 3:
                        atulizarCliente();
                        break;
                    case 4:
                        deletarCliente();
                        break;
                    case 0:
                        System.out.println("Voltando....");
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Erro ao processar a operação: " + e.getMessage());
            }
        } while (item_cliente != 0);
    }
}
