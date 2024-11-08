create table genero(
	id int not null auto_increment primary key,
	nome varchar (100)
);
create table autor(
	id int not null auto_increment primary key,
	nome varchar (100)
);
create table livro(
	id int not null auto_increment primary key,
	titulo varchar (100),
	isbn varchar (100),
	ano_publicacao int,
	id_genero int,
	id_autor int,
	foreign key (id_genero) references genero (id),
	foreign key (id_autor) references autor (id)
);
create table pessoa(
	id int not null auto_increment primary key,
	nome varchar (100),
	email varchar (100),
	telefone varchar (15)
);
create table emprestimo(
	id int not null auto_increment primary key,
	data_emprestimo varchar (10),
	data_devolucao varchar (10),
	id_livro int,
	id_pessoa int,
	foreign key (id_livro) references livro (id),
	foreign key (id_pessoa) references pessoa (id)
);
create table reserva(
	id int not null auto_increment primary key,
	data_reserva varchar (10),
	data_validade varchar (10),
	id_livro int,
	id_pessoa int,
	foreign key (id_livro) references livro (id),
	foreign key (id_pessoa) references pessoa (id)
);