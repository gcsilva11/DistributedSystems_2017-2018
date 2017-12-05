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
CREATE PROCEDURE add_eleicao (IN electionid int(11), title varchar(1024), description varchar(1024), type int(11), closed tinyint(1), startdate datetime, enddate datetime)
BEGIN
	INSERT INTO eleicao VALUES (electionid,title,description,type,closed,startdate,enddate);
	IF type = 1 THEN 
		INSERT INTO mesa_de_voto((null),SELECT depid FROM departamento);
		INSERT INTO eleicao_mesa_de_voto(electionid,SELECT depid FROM departamento);
	END IF;
END //

DELIMITER ;