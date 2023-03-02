create table cozinha (
  id bigint not null auto_increment,
  nome varchar(60) not null,

  primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table cidade (
	id bigint not null auto_increment,
	nome_cidade varchar(80) not null,
	nome_estado varchar(80) not null,

	primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table estado (
	id bigint not null auto_increment,
	nome varchar(80) not null,

	primary key (id)
) engine=InnoDB default charset=utf8mb4;
insert into estado (nome) select distinct nome_estado from cidade;
alter table cidade add column estado_id bigint not null;
update cidade c set c.estado_id = (select e.id from estado e where e.nome = c.nome_estado);
alter table cidade add constraint fk_cidade_estado
foreign key (estado_id) references estado (id);
alter table cidade drop column nome_estado;
alter table cidade change nome_cidade nome varchar(80) not null;


create table forma_pagamento (
	id bigint not null auto_increment,
	descricao varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table grupo (
	id bigint not null auto_increment,
	nome varchar(60) not null,
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table grupo_permissao (
	grupo_id bigint not null,
	permissao_id bigint not null,
	primary key (grupo_id, permissao_id)
) engine=InnoDB default charset=utf8mb4;

create table permissao (
	id bigint not null auto_increment,
	descricao varchar(60) not null,
	nome varchar(100) not null,
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table produto (
	id bigint not null auto_increment,
	restaurante_id bigint not null,
	nome varchar(80) not null,
	descricao text not null,
	preco decimal(10,2) not null,
	ativo tinyint(1) not null,
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table restaurante (
	id bigint not null auto_increment,
	cozinha_id bigint not null,
	nome varchar(80) not null,
	taxa_frete decimal(10,2) not null,
	data_atualizacao datetime not null,
	data_cadastro datetime not null,

	endereco_cidade_id bigint,
	endereco_cep varchar(9),
	endereco_logradouro varchar(100),
	endereco_numero varchar(20),
	endereco_complemento varchar(60),
	endereco_bairro varchar(60),

	primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table restaurante_forma_pagamento (
	restaurante_id bigint not null,
	forma_pagamento_id bigint not null,
	primary key (restaurante_id, forma_pagamento_id)
) engine=InnoDB default charset=utf8mb4;

create table usuario (
	id bigint not null auto_increment,
	nome varchar(80) not null,
	email varchar(255) not null,
	senha varchar(255) not null,
	data_cadastro datetime not null,
	primary key (id)
) engine=InnoDB default charset=utf8mb4;

create table usuario_grupo (
	usuario_id bigint not null,
	grupo_id bigint not null,
	primary key (usuario_id, grupo_id)
) engine=InnoDB default charset=utf8mb4;