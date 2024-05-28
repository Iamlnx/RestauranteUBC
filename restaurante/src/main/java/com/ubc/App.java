package com.ubc;

import java.util.Scanner;

import com.ubc.views.GerenciamentoClientes;
import com.ubc.views.GerenciamentoFuncionario;
import com.ubc.views.GerenciamentoItemDoPedido;
import com.ubc.views.GerenciamentoPedido;
import com.ubc.views.GerenciamentoPrato;

public class App 
{
    public static void main( String[] args ) throws Exception
    {
        Scanner sc = new Scanner(System.in);
        Integer item_menu;
        
        do{
            sc = new Scanner(System.in);
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.println("//                                                                      //");
            System.out.println("//                  Oque deseja gerênciar?                              //");
            System.out.println("//                                                                      //");
            System.out.println("//           (1) -  Gerenciamento de Clientes                           //");
            System.out.println("//           (2) -  Gerenciamento de Funcionarios                       //");
            System.out.println("//           (3) -  Gerenciamento de Pedidos                            //");
            System.out.println("//           (4) -  Gerenciamento de itens do pedido                    //");
            System.out.println("//           (5) -  Gerenciamento de Pratos                             //");
            System.out.println("//           (0) -  Sair                                                //");
            System.out.println("//                                                                      //");
            System.out.println("//                                                                      //");
            System.out.println("//////////////////////////////////////////////////////////////////////////");
            System.out.print("Qual Opção Deseja: ");
            item_menu = sc.nextInt();

            sc.nextLine();

            switch (item_menu) {
                case 1:
                    GerenciamentoClientes opcao1 = new GerenciamentoClientes();
                    opcao1.gerenciarClientes();
                    break;
                case 2:
                    GerenciamentoFuncionario opcao2 = new GerenciamentoFuncionario();
                    opcao2.gerenciarFuncionarios();
                    break;
                case 3:
                    GerenciamentoPedido opcao3 = new GerenciamentoPedido();
                    opcao3.gerenciarPedidos();
                    break;
                case 4:
                    GerenciamentoItemDoPedido opcao4 = new GerenciamentoItemDoPedido();
                    opcao4.gerenciarItensDoPedido();
                    break;
                case 5:
                    GerenciamentoPrato opcao5 = new GerenciamentoPrato();
                    opcao5.gerenciarPratos();
                    break;
                case 0:
                    System.out.println("Muito obrigado por utilizar nossa aplicação!");
                    System.out.println("Saindo....");
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }

            
        } while(item_menu != 0);

        sc.close();

    }
}
