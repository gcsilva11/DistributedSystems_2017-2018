# USER /-------------------------/
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
DROP PROCEDURE IF EXISTS delete_user //
CREATE PROCEDURE delete_user (IN ID int(11))
BEGIN
	DELETE FROM estudante WHERE user_numberid = ID;
	DELETE FROM professor WHERE user_numberid = ID;
	DELETE FROM funcionario WHERE user_numberid = ID;

	DELETE FROM user_faculdade WHERE user_numberid = ID;

	DELETE FROM eleicao_user WHERE user_numberid = ID;
	DELETE FROM lista_candidata_user WHERE user_numberid = ID;
	DELETE FROM mesa_de_voto_user WHERE user_numberid = ID;

	DELETE FROM user WHERE numberid = ID;
END //
DELIMITER ;

# FACULDADES E DEPARTAMENTOS /-------------------------/
DELIMITER //
DROP PROCEDURE IF EXISTS add_faculdade //
CREATE PROCEDURE add_faculdade (IN name varchar(1024))
BEGIN
	INSERT INTO faculdade VALUES((null),name);
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS edit_faculdade //
CREATE PROCEDURE edit_faculdade (IN ID int(11), IN facname varchar(1024))
BEGIN
	UPDATE faculdade SET name = facname WHERE facid = ID;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS delete_faculdade //
CREATE PROCEDURE delete_faculdade (IN ID int(11))
BEGIN
	DELETE FROM unidade_organica WHERE faculdade_facid = ID;
	DELETE FROM departamento WHERE faculdade_facid = ID;

	DELETE FROM mesa_de_voto WHERE faculdade_facid = ID;
	DELETE FROM nucleos WHERE faculdade_facid = ID;

	DELETE FROM user_faculdade WHERE faculdade_facid = ID;
	
	DELETE FROM faculdade WHERE facid = ID;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS add_departamento //
CREATE PROCEDURE add_departamento (IN name varchar(1024), IN faculdadeID int(11))
BEGIN
	INSERT INTO departamento VALUES((null),faculdadeID,name);
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS edit_departamento //
CREATE PROCEDURE edit_departamento (IN ID int(11), IN depname varchar(1024))
BEGIN
	UPDATE departamento SET name = depname WHERE depid = ID;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS delete_departamento //
CREATE PROCEDURE delete_departamento (IN ID int(11))
BEGIN
	DELETE FROM departamento WHERE depid = ID;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS add_unit //
CREATE PROCEDURE add_unit (IN faculdadeID int(11))
BEGIN
	INSERT INTO unidade_organica VALUES(faculdadeID);
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS delete_unit //
CREATE PROCEDURE delete_unit (IN ID int(11))
BEGIN
	DELETE FROM unidade_organica WHERE faculdade_facid = ID;
END //
DELIMITER ;


# ELEICOES /-------------------------/
DELIMITER //
DROP PROCEDURE IF EXISTS add_eleicao //
CREATE PROCEDURE add_eleicao (IN electionid int(11),IN title varchar(1024),IN descp varchar(1024),IN type int(11),IN startdate datetime,IN enddate datetime, IN faculdadeID int)
BEGIN
	INSERT INTO eleicao VALUES (electionid,title,descp,type,STR_TO_DATE(startdate,"%Y-%m-%d %H:%i:%s"),STR_TO_DATE(enddate,"%Y-%m-%d %H:%i:%s"));
	IF type = 1 THEN
		INSERT INTO conselho_geral VALUES (electionid);
		INSERT INTO mesa_de_voto SELECT facid,electionid FROM faculdade;
	ELSEIF type = 2 THEN
		INSERT INTO nucleos VALUES (faculdadeID,electionid);
		INSERT INTO mesa_de_voto SELECT facid,electionid FROM faculdade WHERE facid = faculdadeID;
	END IF;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS edit_eleicao_text //
CREATE PROCEDURE edit_eleicao_text (IN eleicaoID int(11), IN title varchar(1024),IN descp varchar(1024))
BEGIN
	UPDATE eleicao SET title = title WHERE electionid = eleicaoID;
	UPDATE eleicao SET description = descp WHERE electionid = eleicaoID;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS edit_eleicao_date //
CREATE PROCEDURE edit_eleicao_date (IN eleicaoID int(11), IN stdate varchar(1024),IN edate varchar(1024))
BEGIN
	UPDATE eleicao SET startdate = STR_TO_DATE(stdate,"%Y-%m-%d %H:%i:%s") WHERE electionid = eleicaoID;
	UPDATE eleicao SET enddate = STR_TO_DATE(edate,"%Y-%m-%d %H:%i:%s")  WHERE electionid = eleicaoID;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS delete_eleicao //
CREATE PROCEDURE delete_eleicao (IN eleicaoID int(11))
BEGIN
	delete from lista_candidata where eleicao_electionid = eleicaoID;
	DELETE FROM mesa_de_voto WHERE eleicao_electionid = eleicaoID;
	DELETE FROM conselho_geral WHERE eleicao_electionid = eleicaoID;
	DELETE FROM nucleos WHERE eleicao_electionid = eleicaoID;
	DELETE FROM eleicao WHERE electionid = eleicaoID;
END //
DELIMITER ;


# LISTAS /-------------------------/
DELIMITER //
DROP PROCEDURE IF EXISTS add_lista_candidata //
CREATE PROCEDURE add_lista_candidata (IN name varchar(1024),IN type int(11),IN numvotes int(11),IN eleicao_electionid int(11))
BEGIN
	INSERT INTO lista_candidata VALUES ((null),name,type,numvotes,eleicao_electionid);
END//
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS add_lista_candidata_user //
CREATE PROCEDURE add_lista_candidata_user (IN listid int(11),IN userid int(11))
BEGIN
	INSERT INTO lista_candidata_user VALUES (listid,userid);
END//
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS delete_lista_candidata //
CREATE PROCEDURE delete_lista_candidata (IN ID int(11))
BEGIN
	DELETE FROM lista_candidata_user WHERE lista_candidata_listid = ID;
	DELETE FROM lista_candidata WHERE listid = ID;
END //
DELIMITER ;


# MESAS DE VOTO /-------------------------/

DELIMITER //
DROP PROCEDURE IF EXISTS add_mesa_de_voto //
CREATE PROCEDURE add_mesa_de_voto (IN faculdadeID int(11),IN eleicaoID int(11))
BEGIN
	INSERT INTO mesa_de_voto SELECT f.facid,e.electionid FROM eleicao e, faculdade f WHERE e.electionid = eleicaoID AND f.facid = faculdadeID; 
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS delete_mesa_de_voto //
CREATE PROCEDURE delete_mesa_de_voto (IN faculdadeID int(11),IN eleicaoID int(11))
BEGIN
	DELETE FROM mesa_de_voto WHERE faculdade_facid = faculdadeID AND eleicao_electionid = eleicaoID;
END //
DELIMITER ;


# VOTING /-------------------------/
DELIMITER //
DROP PROCEDURE IF EXISTS vote //
CREATE PROCEDURE vote (IN uID int(11),IN eID int(11),IN lID int(11),IN fID int(11))
BEGIN
	INSERT INTO eleicao_user SELECT e.electionid,u.numberid,f.facid FROM eleicao e, user u, faculdade f WHERE e.electionid = eID AND u.numberid = uID AND f.facid = fID; 
	UPDATE lista_candidata SET numvotes = numvotes + 1 WHERE listid = lID;
END //
DELIMITER ;

DELIMITER //
DROP PROCEDURE IF EXISTS check_date //
CREATE PROCEDURE check_date (IN eleicaoID int(11))
BEGIN
	SELECT electionid FROM eleicao WHERE (startdate < CURRENT_TIMESTAMP) AND (enddate > CURRENT_TIMESTAMP) AND electionid = eleicaoID;
END //
DELIMITER ;