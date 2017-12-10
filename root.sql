CREATE USER 'bd_user'@'localhost' IDENTIFIED BY 'password';
CREATE DATABASE sdProjectDatabase;
USE sdProjectDatabase;
GRANT ALL ON sdProjectDatabase.* TO 'bd_user'@'localhost';
