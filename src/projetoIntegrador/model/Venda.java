/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoIntegrador.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Paola
 */
public class Venda {

    private int id;
    private Date dataVenda;
    private double total;
    private Cliente cliente;
    private List<Produto> produtos = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;

    }

    public void addProduto(Produto produto) {
        int index = produtos.indexOf(produto);
        if (index == -1) {//Produto ainda não está na lista, então adiciona.
            produtos.add(new Produto(produto));
        } else {//Já está na lista, só atualiza a quantidade
            Produto encontrado = produtos.get(index);
            int quantidadeAtual = encontrado.getQuantidadeNaVenda();
            int quantidadeAtualizada = quantidadeAtual + produto.getQuantidadeNaVenda();
            encontrado.setQuantidadeNaVenda(quantidadeAtualizada);
        }
        total = calculaTotal();
    }

    private double calculaTotal() {
        double soma = 0;
        for (int i = 0; i < produtos.size(); i++) {
            Produto produto = produtos.get(i);
            soma = soma + (produto.getValor() * produto.getQuantidadeNaVenda());
        }
        return soma;
    }

    public void removerProduto(Produto produto) {
        produtos.remove(produto);
        total = calculaTotal();
    }

}
