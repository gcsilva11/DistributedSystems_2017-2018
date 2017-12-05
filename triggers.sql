DELIMITER //
DROP TRIGGER IF EXISTS add_listas //
CREATE TRIGGER add_listas
	AFTER INSERT ON eleicao
	FOR EACH ROW
BEGIN
	CALL add_lista_candidata('BLANKVOTE',0,0,new.electionid);
	CALL add_lista_candidata('NULLVOTE',0,0,new.electionid);
END //
DELIMITER ;