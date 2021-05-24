/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoIntegrador.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import projetoIntegrador.model.Cliente;
import projetoIntegrador.model.Produto;
import projetoIntegrador.model.Venda;
import projetoIntegrador.utils.Database;

/**
 *
 * @author Paola
 */
public class VendaDAO {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    private void atualizarQuantidadeDosProdutos(Venda venda, Connection conexao) {
        for (Produto produto : venda.getProdutos()) {
            produto.setQuantidade(produto.getQuantidade() - produto.getQuantidadeNaVenda());
            produtoDAO.atualizar(produto, conexao);
        }
    }

    private void atualizarProdutoVenda(Venda venda, Connection conexao) {
        PreparedStatement instrucaoSQL = null;

        try {
            for (Produto produto : venda.getProdutos()) {
                instrucaoSQL = conexao.prepareStatement("INSERT INTO venda_produto (quantidade_produto, id_produto, id_venda) VALUES(?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);

                instrucaoSQL.setInt(1, produto.getQuantidadeNaVenda());
                instrucaoSQL.setInt(2, produto.getId());
                instrucaoSQL.setInt(3, venda.getId());

                instrucaoSQL.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void adicionaClienteNaVenda(Venda venda, int clienteId, Connection conexao) {
        Cliente cliente = clienteDAO.pesquisarClientePorId(clienteId, conexao);
        venda.setCliente(cliente);
    }

    private void adicionaProdutosNaVenda(Venda venda, Connection conexao) {

        ResultSet rs = null;
        PreparedStatement instrucaoSQL = null;

        try {
            instrucaoSQL = conexao.prepareStatement("SELECT "
                    + "venda_produto.quantidade_produto as quantidade_produto_na_venda, "
                    + "produto.id as id_produto, "
                    + "produto.nome as nome_produto, "
                    + "produto.quantidade as quantidade_produto, "
                    + "produto.valor as valor_produto "
                    + "FROM venda_produto "
                    + "INNER JOIN produto ON produto.id = venda_produto.id_produto "
                    + "WHERE id_venda = ?");

            instrucaoSQL.setInt(1, venda.getId());

            rs = instrucaoSQL.executeQuery();

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getInt("id_produto"));
                produto.setNome(rs.getString("nome_produto"));
                produto.setQuantidade(rs.getInt("quantidade_produto"));
                produto.setQuantidadeNaVenda(rs.getInt("quantidade_produto_na_venda"));
                produto.setValor(rs.getDouble("valor_produto"));

                venda.addProduto(produto);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean salvarVenda(Venda venda) {
        Connection conexao = null;
        PreparedStatement instrucaoSQL = null;

        try {

            //Tenta estabeler a conexão com o SGBD e cria comando a ser executado conexão
            //Obs: A classe GerenciadorConexao já carrega o Driver e define os parâmetros de conexão
            conexao = Database.abrirConexao();

            instrucaoSQL = conexao.prepareStatement("INSERT INTO venda (data_venda, total, id_cliente) VALUES(?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);  //Caso queira retornar o ID do cliente

            //Adiciono os parâmetros ao meu comando SQL
            instrucaoSQL.setDate(1, new java.sql.Date(venda.getDataVenda().getTime()));
            instrucaoSQL.setDouble(2, venda.getTotal());
            instrucaoSQL.setInt(3, venda.getCliente().getId());

            int linhasAfetadas = instrucaoSQL.executeUpdate(); //sintetico executeQuery

            if (linhasAfetadas > 0) {

                ResultSet generatedKeys = instrucaoSQL.getGeneratedKeys(); //Recupero o ID do cliente
                if (generatedKeys.next()) {
                    venda.setId(generatedKeys.getInt(1));
                    atualizarQuantidadeDosProdutos(venda, conexao);
                    atualizarProdutoVenda(venda, conexao);
                    return true;
                } else {
                    throw new SQLException("Falha ao obter o ID do cliente.");
                }
            } else {
                return false;
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        } finally {

            //Libero os recursos da memória
            try {
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }

                Database.fecharConexao();

            } catch (SQLException ex) {
            }
        }
    }

    //aqui é busca uma arrayList do tipo Venda obtida do simulaBanco que manda para o controler passar para a view
    public List<Venda> consultarVendas(Date inicio, Date fim) {
        ResultSet rs = null;
        Connection conexao;
        PreparedStatement instrucaoSQL = null;

        ArrayList<Venda> listaVenda = new ArrayList<>();

        try {

            conexao = Database.abrirConexao();
            instrucaoSQL = conexao.prepareStatement("SELECT * FROM venda WHERE data_venda BETWEEN ? and ?;");
            instrucaoSQL.setDate(1, new java.sql.Date(inicio.getTime()));
            instrucaoSQL.setDate(2, new java.sql.Date(fim.getTime()));

            rs = instrucaoSQL.executeQuery();

            while (rs.next()) {
                Venda venda = new Venda();
                venda.setDataVenda(rs.getDate("data_venda"));
                venda.setTotal(rs.getInt("total"));
                venda.setId(rs.getInt("id"));

                adicionaClienteNaVenda(venda, rs.getInt("id_cliente"), conexao);
                adicionaProdutosNaVenda(venda, conexao);

                listaVenda.add(venda);
            }

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            //Libero os recursos da memória
            try {
                if (rs != null) {
                    rs.close();
                }
                if (instrucaoSQL != null) {
                    instrucaoSQL.close();
                }

                Database.fecharConexao();

            } catch (SQLException ex) {
            }
        }

        return listaVenda;
    }
}
