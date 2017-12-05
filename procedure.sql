DELIMITER //
DROP PROCEDURE IF EXISTS add_user //
CREATE PROCEDURE add_user (IN numberid int(11), IN name varchar(1024), password varchar(1024), phone varchar(1024), expdate datetime, profession int)
BEGIN
	INSERT INTO user VALUES (numberid,name,password,phone,STR_TO_DATE(expdate,"%Y-%m-%d %H:%i:%s"));
	IF profession = 1 THEN INSERT INTO estudante VALUES (numberid);
	ELSEIF profession = 2 THEN INSERT INTO professor VALUES (numberid);
	ELSEIF profession = 3 THEN INSERT INTO funcionario VALUES (numberid);
	END IF;
END //
DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS add_user_faculdade //
CREATE PROCEDURE add_user_faculdade (IN numberid int(11), IN faculdadeid int)
BEGIN
	INSERT INTO user_faculdade VALUES(numberid,faculdadeid);
END //
DELIMITER ;


DELIMITER //
DROP PROCEDURE IF EXISTS add_eleicao //
CREATE PROCEDURE add_eleicao (IN electionid int(11),IN title varchar(1024),IN description varchar(1024),IN type int(11),IN closed tinyint(1),IN startdate datetime,IN enddate datetime)
BEGIN
	INSERT INTO eleicao VALUES (electionid,title,description,type,0,STR_TO_DATE(startdate,"%Y-%m-%d %H:%i:%s"),STR_TO_DATE(enddate,"%Y-%m-%d %H:%i:%s"));
		INSERT INTO mesa_de_voto SELECT (null),facid FROM faculdade;

		INSERT INTO eleicao_mesa_de_voto SELECT electionid,facid FROM faculdade;
END //

DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS add_lista_candidata //
CREATE PROCEDURE add_lista_candidata (IN name varchar(1024),IN type int(11),IN numvotes int(11),IN eleicao_electionid int(11))
BEGIN
	INSERT INTO lista_candidata VALUES ((null),name,type,numvotes,eleicao_electionid);
	INSERT INTO eleicao_lista_candidatat SELECT eleicao_electionid,max(listid) FROM lista_candidata;
END//
DELIMITER ;

