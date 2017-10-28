# iVotas
Projeto Sistemas Distribuídos 2017/2018 LEI

Realizado por:
 - João Pedro Costa Ferreiro 
	2014197760
	ferreiro@student.dei.uc.pt
 - Guilherme Cardoso Gomes da Silva
 	2014226354
 	gcsilva@student.dei.uc.pt


Para correr este projeto é necessário ter um compilador de java (por exemplo: JDK 1.8);
Para executar o projeto são disponibilizados 4 ficheiros .jar;
É necessário que o primeiro a ser executado seja o "dataserver.jar", posteriormente o "console.jar" ou "server.jar";
O "client.jar" necessita que o "server.jar" esteja a ser executado;
Como alternativa os ficheiros .java podem ser compilados e executados sobre a mesma ordem;
Este projeto ainda não utiliza base de dados, guardando a informação em ficheiros de objectos.


Todos as aplicações necessitam que seja dado o hostname e IP para as ligações que efetuam:
	RMIServer - 
		RMI Hostname: [host onde RMI está alojado]; RMI Port: [porta onde vai ser criado o registo do RMI]; UPDSocket Port: [porta onde RMI's comunicam];
	Admin - 
		RMI Hostname: [host onde RMI está alojado]; RMI Port: [porta onde foi criado o registo do RMI];
	TCPServer - 
		RMI Hostname: [host onde RMI está alojado]; RMI Port: [porta onde foi criado o registo do RMI]; TCPSocket Port [porta onde TCP Server e Cliente comunicam];
	TCPClient
		TCPServer Hostname: [host onde TCPServer está alojado]; TCPSocket Port: [porta onde TCP Server e Cliente comunicam];