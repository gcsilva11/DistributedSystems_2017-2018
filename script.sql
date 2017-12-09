#----------------------------------------------------------------------------#
# Main Tables

# User (pode ser estudante, professor oy funcionario)
CREATE TABLE user(
	numberid	int,
	name	 varchar(1024) UNIQUE NOT NULL,
	password	 varchar(1024) NOT NULL,
	phone	 varchar(1024),
	expdate	 datetime NOT NULL,
	morada varchar (1024),
	PRIMARY KEY(numberid)
);
# User (Estudante)
CREATE TABLE estudante(
	user_numberid int,
	PRIMARY KEY(user_numberid)
);
# User (Professor)
CREATE TABLE professor(
	user_numberid int,
	PRIMARY KEY(user_numberid)
);
# User (Funcionario)
CREATE TABLE funcionario(
	user_numberid int,
	PRIMARY KEY(user_numberid)
);

# Eleicao (pode ser para o Conselhor Geral ou para Nucleo de estudantes)
CREATE TABLE eleicao(
	electionid	 int,
	title	 varchar(1024) UNIQUE NOT NULL,
	description varchar(1024) NOT NULL,
	type	 int NOT NULL,
	startdate	 datetime NOT NULL,
	enddate	 datetime NOT NULL,
	PRIMARY KEY(electionid)
);
# Eleicao (Nucleos)
CREATE TABLE nucleos(
	faculdade_facid		 int NOT NULL,
	eleicao_electionid int,
	PRIMARY KEY(eleicao_electionid)
);
# Eleicao (Conselho Geral)
CREATE TABLE conselho_geral(
	eleicao_electionid int,
	PRIMARY KEY(eleicao_electionid)
);

# Faculdade (pode conter vários departamentos, ou ser uma Unidade Orgânica)
CREATE TABLE faculdade(
	facid	 int AUTO_INCREMENT,
	name varchar(1024) UNIQUE NOT NULL,
	PRIMARY KEY(facid)
);
# Faculdade (Unidade Organica)
CREATE TABLE unidade_organica(
	faculdade_facid int,
	PRIMARY KEY(faculdade_facid)
);
# Faculdade (Departamento)
CREATE TABLE departamento(
	depid int AUTO_INCREMENT,
	faculdade_facid int,
	name varchar(1024) UNIQUE NOT NULL,
	PRIMARY KEY(depid)
);

# Lista Candidata
CREATE TABLE lista_candidata(
	listid		 int AUTO_INCREMENT,
	name		 varchar(1024) NOT NULL,
	type		 int NOT NULL,
	numvotes		 int NOT NULL DEFAULT 0,
	eleicao_electionid int NOT NULL,
	PRIMARY KEY(listid)
);

# Mesa de Voto
CREATE TABLE mesa_de_voto(
	faculdade_facid	 int,
	eleicao_electionid int,
	PRIMARY KEY(faculdade_facid,eleicao_electionid)
);

#----------------------------------------------------------------------------#
# Intermediate Tables

# Guarda entrada se user ja votou em determinada eleicao
CREATE TABLE eleicao_user(
	eleicao_electionid int NOT NULL,
	user_numberid	 int,
	faculdade_facid int,
	PRIMARY KEY(eleicao_electionid,user_numberid)
);
# Guarda entrada se user pertence a uma lista candidata
CREATE TABLE lista_candidata_user(
	lista_candidata_listid int NOT NULL,
	user_numberid		 int,
	PRIMARY KEY(lista_candidata_listid,user_numberid)
);
# Guarda entrada e user pertence a uma faculdade
CREATE TABLE user_faculdade(
	user_numberid	 int,
	faculdade_facid int,
	PRIMARY KEY(user_numberid,faculdade_facid)
);
# 
#CREATE TABLE mesa_de_voto_user(
#	user_numberid int,
#	mesa_de_voto_faculdade_facid int,
#	PRIMARY KEY(user_numberid,mesa_de_voto_faculdade_facid)
#);

#----------------------------------------------------------------------------#
# Foreign Keys

ALTER TABLE professor ADD CONSTRAINT professor_user_numberid FOREIGN KEY (user_numberid) REFERENCES user(numberid);
ALTER TABLE estudante ADD CONSTRAINT estudante_user_numberid FOREIGN KEY (user_numberid) REFERENCES user(numberid);
ALTER TABLE funcionario ADD CONSTRAINT funcionario_user_numberid FOREIGN KEY (user_numberid) REFERENCES user(numberid);

ALTER TABLE nucleos ADD CONSTRAINT nucleos_eleicao_electionid FOREIGN KEY (eleicao_electionid) REFERENCES eleicao(electionid);
ALTER TABLE nucleos ADD CONSTRAINT nucleos_faculdade_facid FOREIGN KEY (faculdade_facid) REFERENCES faculdade(facid);
ALTER TABLE conselho_geral ADD CONSTRAINT conselho_geral_eleicao_electionid FOREIGN KEY (eleicao_electionid) REFERENCES eleicao(electionid);

ALTER TABLE unidade_organica ADD CONSTRAINT unidade_organica_faculdade_facid FOREIGN KEY (faculdade_facid) REFERENCES faculdade(facid);
ALTER TABLE departamento ADD CONSTRAINT departmento_faculdade_facid FOREIGN KEY (faculdade_facid) REFERENCES faculdade(facid);
	
ALTER TABLE lista_candidata ADD CONSTRAINT lista_candidata_eleicao_electionid FOREIGN KEY (eleicao_electionid) REFERENCES eleicao(electionid);

ALTER TABLE mesa_de_voto ADD CONSTRAINT mesa_de_voto_faculdade_facid FOREIGN KEY (faculdade_facid) REFERENCES faculdade(facid);
ALTER TABLE mesa_de_voto ADD CONSTRAINT mesa_de_voto_eleicao_electionid FOREIGN KEY (eleicao_electionid) REFERENCES eleicao(electionid);

ALTER TABLE eleicao_user ADD CONSTRAINT eleicao_user_eleicao_electionid FOREIGN KEY (eleicao_electionid) REFERENCES eleicao(electionid);
ALTER TABLE eleicao_user ADD CONSTRAINT eleicao_user_user_numberid FOREIGN KEY (user_numberid) REFERENCES user(numberid);
ALTER TABLE eleicao_user ADD CONSTRAINT eleicao_user_faculdade_facid FOREIGN KEY (faculdade_facid) REFERENCES faculdade(facid);

ALTER TABLE lista_candidata_user ADD CONSTRAINT lista_candidata_user_lista_candidata_listid FOREIGN KEY (lista_candidata_listid) REFERENCES lista_candidata(listid);
ALTER TABLE lista_candidata_user ADD CONSTRAINT lista_candidata_user_user_numberid FOREIGN KEY (user_numberid) REFERENCES user(numberid);

ALTER TABLE user_faculdade ADD CONSTRAINT user_faculdade_fk1 FOREIGN KEY (user_numberid) REFERENCES user(numberid);
ALTER TABLE user_faculdade ADD CONSTRAINT user_faculdade_fk2 FOREIGN KEY (faculdade_facid) REFERENCES faculdade(facid);

#ALTER TABLE mesa_de_voto_user ADD CONSTRAINT mesa_de_voto_user_fk1 FOREIGN KEY (user_numberid) REFERENCES user(numberid);
#ALTER TABLE mesa_de_voto_user ADD CONSTRAINT mesa_de_voto_user_fk2 FOREIGN KEY (mesa_de_voto_faculdade_facid) REFERENCES mesa_de_voto(faculdade_facid);
