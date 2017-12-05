CREATE TABLE user(
	numberid	int,
	name	 varchar(1024) UNIQUE NOT NULL,
	password	 varchar(1024) NOT NULL,
	phone	 varchar(1024),
	expdate	 datetime NOT NULL,
	PRIMARY KEY(numberid)
);

CREATE TABLE professor(
	user_numberid int,
	PRIMARY KEY(user_numberid)
);

CREATE TABLE estudante(
	user_numberid int,
	PRIMARY KEY(user_numberid)
);

CREATE TABLE funcionario(
	user_numberid int,
	PRIMARY KEY(user_numberid)
);


CREATE TABLE eleicao(
	electionid	 int AUTO_INCREMENT,
	title	 varchar(1024) UNIQUE NOT NULL,
	description varchar(1024) NOT NULL,
	type	 int NOT NULL,
	closed	 bool NOT NULL DEFAULT false,
	startdate	 datetime NOT NULL,
	enddate	 datetime NOT NULL,
	PRIMARY KEY(electionid)
);

CREATE TABLE nucleos(
	faculdade_facid		 int NOT NULL,
	eleicao_electionid int AUTO_INCREMENT,
	PRIMARY KEY(eleicao_electionid)
);

CREATE TABLE conselho_geral(
	eleicao_electionid int AUTO_INCREMENT,
	PRIMARY KEY(eleicao_electionid)
);


CREATE TABLE faculdade(
	facid	 int AUTO_INCREMENT,
	name varchar(1024) UNIQUE NOT NULL,
	PRIMARY KEY(facid)
);

CREATE TABLE unidade_organica(
	faculdade_facid int,
	PRIMARY KEY(faculdade_facid)
);

CREATE TABLE departamento(
	depid int AUTO_INCREMENT,
	faculdade_facid int,
	name varchar(1024) UNIQUE NOT NULL,
	PRIMARY KEY(depid)
);


CREATE TABLE lista_candidata(
	listid		 int AUTO_INCREMENT,
	name		 varchar(1024) UNIQUE NOT NULL,
	type		 int NOT NULL,
	numvotes		 int NOT NULL DEFAULT 0,
	eleicao_electionid int NOT NULL,
	PRIMARY KEY(listid)
);


CREATE TABLE mesa_de_voto(
	boothid		 int UNIQUE NOT NULL,
	faculdade_facid	 int,
	PRIMARY KEY(faculdade_facid)
);


CREATE TABLE eleicao_mesa_de_voto(
	eleicao_electionid int NOT NULL,
	mesa_de_voto_faculdade_facid	 int,
	PRIMARY KEY(eleicao_electionid,mesa_de_voto_faculdade_facid)
);

CREATE TABLE eleicao_user(
	eleicao_electionid int NOT NULL,
	user_numberid	 int,
	PRIMARY KEY(eleicao_electionid,user_numberid)
);

CREATE TABLE lista_candidata_user(
	lista_candidata_listid int NOT NULL,
	user_numberid		 int,
	PRIMARY KEY(lista_candidata_listid,user_numberid)
);

CREATE TABLE eleicao_lista_candidata(
	eleicao_electionid int NOT NULL,
	lista_candidata_listid int NOT NULL,
	PRIMARY KEY(eleicao_electionid,lista_candidata_listid)
);

CREATE TABLE user_faculdade(
	user_numberid	 int,
	faculdade_facid int,
	PRIMARY KEY(user_numberid,faculdade_facid)
);

CREATE TABLE mesa_de_voto_user(
	user_numberid int,
	mesa_de_voto_boothid int,
	PRIMARY KEY(user_numberid,mesa_de_voto_boothid)
);


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


ALTER TABLE eleicao_mesa_de_voto ADD CONSTRAINT eleicao_mesa_de_voto_eleicao_electionid FOREIGN KEY (eleicao_electionid) REFERENCES eleicao(electionid);
ALTER TABLE eleicao_mesa_de_voto ADD CONSTRAINT eleicao_mesa_de_voto_mesa_de_voto_faculdade_facid FOREIGN KEY (mesa_de_voto_faculdade_facid) REFERENCES mesa_de_voto(faculdade_facid);

ALTER TABLE eleicao_user ADD CONSTRAINT eleicao_user_eleicao_electionid FOREIGN KEY (eleicao_electionid) REFERENCES eleicao(electionid);
ALTER TABLE eleicao_user ADD CONSTRAINT eleicao_user_user_numberid FOREIGN KEY (user_numberid) REFERENCES user(numberid);

ALTER TABLE lista_candidata_user ADD CONSTRAINT lista_candidata_user_lista_candidata_listid FOREIGN KEY (lista_candidata_listid) REFERENCES lista_candidata(listid);
ALTER TABLE lista_candidata_user ADD CONSTRAINT lista_candidata_user_user_numberid FOREIGN KEY (user_numberid) REFERENCES user(numberid);

ALTER TABLE eleicao_lista_candidata ADD CONSTRAINT eleicao_lista_candidata_eleicao_electionid FOREIGN KEY (eleicao_electionid) REFERENCES eleicao(electionid);
ALTER TABLE eleicao_lista_candidata ADD CONSTRAINT eleicao_lista_candidata_lista_candidata_listid FOREIGN KEY (lista_candidata_listid) REFERENCES lista_candidata(listid);

ALTER TABLE user_faculdade ADD CONSTRAINT user_faculdade_fk1 FOREIGN KEY (user_numberid) REFERENCES user(numberid);
ALTER TABLE user_faculdade ADD CONSTRAINT user_faculdade_fk2 FOREIGN KEY (faculdade_facid) REFERENCES faculdade(facid);

ALTER TABLE mesa_de_voto_user ADD CONSTRAINT mesa_de_voto_user_fk1 FOREIGN KEY (user_numberid) REFERENCES user(numberid);
ALTER TABLE mesa_de_voto_user ADD CONSTRAINT mesa_de_voto_user_fk2 FOREIGN KEY (mesa_de_voto_boothid) REFERENCES mesa_de_voto(boothid);
