Correr executáveis do projeto:
	- O projeto utiliza uma base de dados MySQL, cujos respectivos ficheiros de criação de utilizador, base de dados, stored procederes e triggers, se encontram na pasta SQL. A ordem de execução destes scripts é a seguinte: (root user) - root.sql; (bd_user) dbcreation.sql, triggers.sql, procedures.sql
	- Na pasta de cada meta existe uma pasta com os respectivos executáveis de todas as aplicações necessárias ao funcionamento do projeto, nos quais já estão compiladas todas as dependências necessárias.
	- Ficheiros jar: 'java -jar ficheiro_jar.jar'
	  Ficheiros war: Fazer deploy em servidor, por exemplo Tomcat
	- Web application irá correr por default em localhost:8080