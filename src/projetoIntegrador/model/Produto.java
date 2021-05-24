/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoIntegrador.model;

import java.util.Objects;

public class Produto {

    //só para a aplicação
    private int quantidadeNaVenda;

    private int id;
    private String nome;
    private double valor;
    private int quantidade;

    public Produto() {
    }

    //construtor de salvar
    public Produto(String nome, int qtd, double valor) {
        this.nome = nome;
        this.quantidade = qtd;
        this.valor = valor;
    }

    //Construtor de atualizar o produto
    public Produto(int id, String nome, int qtd, double valor) {
        this.id = id;
        this.nome = nome;
        this.quantidade = qtd;
        this.valor = valor;
    }

    public Produto(Produto copia) {
        this.id = copia.id;
        this.nome = copia.nome;
        this.valor = copia.valor;
        this.quantidade = copia.quantidade;
        this.quantidadeNaVenda = copia.quantidadeNaVenda;
    }

    public int getQuantidadeNaVenda() {
        return quantidadeNaVenda;
    }

    public void setQuantidadeNaVenda(int quantidadeNaVenda) {
        this.quantidadeNaVenda = quantidadeNaVenda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor < 0) {
            return;
        }
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.nome);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (Objects.equals(this.nome, other.nome)) {
            return true;
        }
        return false;
    }

}
