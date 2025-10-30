package com.projects;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ProductDAO {
    public void inserirProduto(Product produto) throws SQLException{
        String inserirSql = "insert into produto (nome, quantidade, valor) values (? , ?,  ?)" ; //as interrogacoes serao substituidas com PreparedStatement
        
        try(Connection conexao = Conection.obterConexao( ) ;
                PreparedStatement statement = conexao.prepareStatement(inserirSql)){ //cria o stament preparado
                statement.setString(1, produto.getNome( ));
                statement.setInt(2, produto.getQuantidade( ));
                statement.setDouble(3, produto.getValor( ));
                statement.executeUpdate( );

        }catch(SQLException e){
           throw new SQLException( );
        }
    }

    public List<Produto> buscarTodosProdutos( ) throws SQLException{
        String consultaSql = "select * from produto";
        List<Produto> produtos = new ArrayList<>();

        try(Connection conexao = Conexao.obterConexao( );
                PreparedStatement statement = conexao.prepareStatement(consultaSql)){
                try(ResultSet resultSet = statement.executeQuery( )){ //CONJUNTO de resultados da consulta SQL
                    while(resultSet.next( )){ //enquanto houver mais resultados para serem lidos
                        Produto produto = new Produto(resultSet.getLong("id"), resultSet.getString("nome"), 
                                                                                               resultSet.getInt("quantidade"), resultSet.getDouble("valor"));
                        produtos.add(produto);
                    }
                }
        }catch(SQLException e){
            throw new SQLException( );
        }

        return produtos;
    }

    public Produto buscarPorId(Long id) throws SQLException{
        String buscaSql = "select * from produto where id = ?";
        Produto produto = null; //inicializa o produto a ser retornado

        try(Connection conexao = Conexao.obterConexao( ) ; 
            PreparedStatement statement = conexao.prepareStatement(buscaSql)){
            statement.setLong(1, id); //configura o id passado para a consulta 

            try(ResultSet result = statement.executeQuery()){
                while(result.next( )){
                    produto = new Produto(result.getLong("id"), result.getString("nome"), 
                                                                                result.getInt("quantidade"), result.getDouble("valor"));
                }
            }
        }catch(SQLException e){
            throw new SQLException( );
        }
        return produto;
    }

    public void atualizarProduto(Produto produto) throws SQLException{
        String atualizacaoSql = "update produto set nome = ?, quantidade = ?, valor = ? where id = ?";

        try(Connection conexao = Conexao.obterConexao( );
                PreparedStatement statement = conexao.prepareStatement(atualizacaoSql)){
            statement.setString(1, produto.getNome( ));
            statement.setInt(2, produto.getQuantidade( ));
            statement.setDouble(3, produto.getValor( ));
            statement.setLong(4, produto.getId( ));
            statement.executeUpdate( );
        
        }catch(SQLException e){
            throw new SQLException( );
        }        
    }

    public void excluirProduto(Long id) throws SQLException{
        String excluirSql = "delete from produto where id = ?";

        try(Connection conexao = Conexao.obterConexao( );
                PreparedStatement statement = conexao.prepareStatement(excluirSql)){
            statement.setLong(1, id);
            statement.executeUpdate( );

        }catch(SQLException e){
            throw new SQLException( );
        }
    }

    public void excluirTodosProdutos( ) throws SQLException{
        String excluirTodos = "delete from produto";

        try(Connection conexao = Conexao.obterConexao( );
                PreparedStatement statement = conexao.prepareStatement(excluirTodos)){
            statement.executeUpdate( );

        }catch(SQLException e){
            throw new SQLException( );
        }
    }
}

