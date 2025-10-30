package com.projects;
import java.util.Scanner;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class Main {

    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = Integer.parseInt(input.nextLine( )); 
            switch (opcao) {
                case 0 -> inserirProduto( );
                case 1 -> buscarTodosProdutos( );
                case 2 -> buscarProdutoPorId( );
                case 3 -> atualizarProduto( );
                case 4 -> excluirProduto( );
                case 5 -> excluirTodosProdutos( );
                case 6 -> System.exit(0);
                default -> System.out.println("Opção inválida!");
            }
        } while (true);
        
    }

    private static void exibirMenu() {
        System.out.println("\n### Menu de Operações ###");
        System.out.println("0. Inserir novo produto");
        System.out.println("1. Buscar todos produtos");
        System.out.println("2. Buscar produto por ID");
        System.out.println("3. Atualizar produto");
        System.out.println("4. Excluir produto");
        System.out.println("5. Excluir todos os produtos");
        System.out.println("6. Sair do programa");
        System.out.print("Escolha uma opção: ");
    }

    private static void inserirProduto( ){
        System.out.println("### Inserir novo produto ###");
        
        System.out.print("Digite o nome do produto a ser inserido: ");
        String nome = input.nextLine( );
        try{
            System.out.print("Digite a quantidade do produto: ");
            int quantidade = Integer.parseInt(input.nextLine( )); //validar e verificar
            System.out.print("Digite o valor do produto: ");
            double valor = Double.parseDouble(input.nextLine( )); //validar e verificar 
            
            if(quantidade > 0 && valor > 0){
                Product produto = new Product(nome, quantidade, valor); //cria o objeto produto a ser inserido
                ProductDAO produtoDAO = new ProductDAO( ); //cria o objeto que acessa o banco de dados
                produtoDAO.inserirProduto(produto); //insere o produto no banco de dados
            }else{
                System.out.println("Valor ou quantidade inválidos!");
                return;
            }
        } catch(SQLException e){
            System.out.println("Erro ao acessar o banco de dados!");
            e.printStackTrace( );
        }catch(NumberFormatException e){
            System.out.println("Tipo de dado inserido inválido!");
            return;
        }

        System.out.println("Produto adicionado com sucesso!");
    }

    private static void buscarTodosProdutos( ){ 
        System.out.println("### Buscar todos os produtos ###");

        ProductDAO produto = new ProductDAO( );
        try{
            List<Product> produtos = produto.buscarTodosProdutos( );
        
            if(!produtos.isEmpty()){
                for(Product produtoAtual : produtos){
                    System.out.printf("%s", produtoAtual);
                } 
            } else{
                System.out .println("Lista de produtos vazia!!!");
            }
        }catch(SQLException e){
            System.out.println("Erro ao acessar o banco de dados!");
        }    
    }

    private static void buscarProdutoPorId( ){
        System.out.println("### Buscar produto por ID ###");
        System.out.print("Digite o ID do produto a ser buscado: ");
        try{
            Long id = Long.parseLong(input.nextLine( ));

            ProductDAO produtoDAO = new ProductDAO();
            Product produtoRetornado = produtoDAO.buscarPorId(id);
            if(produtoRetornado != null)
                System.out.print(produtoRetornado);
            else
                System.out.println("Produto não encontrado!");
        } catch(SQLException e){
            System.out.println("Erro ao acessar o banco de dados!");
        } catch(NumberFormatException e){
            System.out.println("Formato de ID inválido!");
        }
    }

    private static void atualizarProduto( ){ 
        System.out.println("### Atualizar um produto ###");
        System.out.print("Digite o id do produto que deseja atualizar: ");
        try{
            Long id = Long.parseLong(input.nextLine( ));         
            ProductDAO produtoDAO = new ProductDAO( );
            Product produtoRecuperado = produtoDAO.buscarPorId(id);

            System.out.print("Novo nome (atual - " + produtoRecuperado.getNome( ) + "): ");
            String nome = input.nextLine( );
            System.out.print("Nova quantidade (atual - " + produtoRecuperado.getQuantidade( ) + "): ");
            int quantidade = Integer.parseInt(input.nextLine( ));
            System.out.print("Novo valor (atual - " + produtoRecuperado.getValor( ) + "): ");
            double valor = Double.parseDouble(input.nextLine( ));
            
            if(valor > 0 && quantidade > 0)
                produtoDAO.atualizarProduto(new Product(id, nome, quantidade, valor));
            else
                System.out.println("Valor ou quantidade inválidos!");
        } catch(SQLException e){
            System.out.println("Erro ao acessar o banco de dados!");
        }catch(NumberFormatException e){
            System.out.println("Formato  inválido!");
        }
    }

    private static void excluirProduto( ){ 
        System.out.println("### Excluir um produto ###");
        System.out.print("Digite o ID do produto que deseja excluir: ");
        try{
            Long id = Long.parseLong(input.nextLine( ));
            ProductDAO produtoDAO = new ProductDAO( );
            Product produtoExcluido = produtoDAO.buscarPorId(id);
            produtoDAO.excluirProduto(id);

            System.out.println("Produto excluido com sucesso!\nInformações dele: \n");
            System.out.println(produtoExcluido);
        } catch(SQLException e){
            System.out.println("Erro ao acessar o banco de dados!");
        } catch(NumberFormatException e){
            System.out.println("Formato inválido!");
        }
    }

    private static void excluirTodosProdutos( ){
        System.out.println("### Excluir todos os produtos ###");
        System.out.println("Tem certeza que deseja excluir todos os os produtos (1 - sim | 2 - não)?");
        try{
            int opcao =Integer.parseInt(input.nextLine( ));
            if(opcao == 1){
                ProductDAO produtoDAO = new ProductDAO( );
                produtoDAO.excluirTodosProdutos( );
            }
        } catch(SQLException e){
            System.out.println("Erro ao acessar o banco de dados!");
        }
    }
}
