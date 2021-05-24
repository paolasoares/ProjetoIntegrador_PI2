create database loja_artigo_casa;
use loja_artigo_casa;
create table produto
(
    id int auto_increment primary key,
    nome varchar(45) not null,
    quantidade int not null,
    valor double not null
);

create table cliente
(
    id int auto_increment primary key,
    cpf varchar(14) unique not null,
    nome varchar(45) not null,
    sexo varchar(15) not null,
    email varchar(45) not null,
    telefone varchar(16) not null,
    telefone2 varchar(16),
    data_nascimento date not null,
    estado_civil varchar(15) not null,
    rua varchar(20) not null,
    numero varchar(10) not null,
    bairro varchar(45) not null,
    cidade varchar(45) not null,
    cep varchar(12) not null,
    nacionalidade varchar(20) not null,
    data_cadastro datetime not null,
    ultima_atualizacao datetime
    
);

create table venda
(
    id int auto_increment primary key ,
    data_venda date ,
    total double not null,
    id_cliente int,
    foreign key(id_cliente) references cliente(id)
);

create table venda_produto
(
    id_venda_produto int auto_increment primary key,
    quantidade_produto int,
    id_produto int,
    id_venda int,
    foreign key(id_produto) references produto(id),
    foreign key(id_venda) references venda(id)
);