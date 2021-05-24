/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoIntegrador.model;

import java.util.Date;

/**
 *
 * @author Paola
 */
public class Cliente {

    //atributos
    private int id;
    private String cpf;
    private String nome;
    private String sexo;
    private Date dataNascimento;
    private String estadoCivil;
    private String endereco;
    private String bairro;
    private String cep;
    private String numero;
    private String cidade;
    private String nacionalidade;
    private String email;
    private String telefone;
    private String telefone2;
    private Date dataCadastro;
    private Date ultimaAtualizacao;

    public Cliente() {
    }

    public Cliente(String cpf, String nome, String sexo, Date dataNascimento, 
            String estadoCivil, String endereco, String bairro, String cep,
            String numero, String cidade, String nacionalidade, String email,
            String telefone, String telefone2, Date dataCadastro,
            Date ultimaAtualizacao) {
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.estadoCivil = estadoCivil;
        this.endereco = endereco;
        this.bairro = bairro;
        this.numero = numero;
        this.cidade = cidade;
        this.cep = cep;
        this.nacionalidade = nacionalidade;
        this.email = email;
        this.telefone = telefone;
        this.telefone2 = telefone2;
        this.dataCadastro = dataCadastro;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    //Contrutor de atualizar o cliente 
    public Cliente(int id,String cpf, String nome, String sexo, Date dataNascimento,
            String estadoCivil, String endereco, String bairro, String cep,
            String numero, String cidade, String nacionalidade, String email,
            String telefone, String telefone2, Date ultimaAtualizacao) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.sexo = sexo;
        this.dataNascimento = dataNascimento;
        this.estadoCivil = estadoCivil;
        this.endereco = endereco;
        this.bairro = bairro;
        this.cep = cep;
        this.numero = numero;
        this.cidade = cidade;
        this.nacionalidade = nacionalidade;
        this.email = email;
        this.telefone = telefone;
        this.telefone2 = telefone2;
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    // abaixo os getters e setters dos atributos Clientes
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

}
