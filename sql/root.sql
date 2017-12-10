CREATE USER 'bd_user'@'localhost' IDENTIFIED BY 'password';
CREATE DATABASE sdProjectDatabase;
USE sdProjectDatabase
GRANT ALL PRIVILEGES ON sdProjectDatabase.* TO 'bd_user'@'localhost' WITH GRANT OPTION;
