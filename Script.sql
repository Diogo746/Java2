-- Criação do banco de dados
CREATE DATABASE app_simples;


-- Seleciona o banco de dados
USE app_simples;


-- Criação da tabela usuario
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL,
    senha VARCHAR(50) NOT NULL
);



SELECT * FROM usuario;

DELETE FROM usuario WHERE id = 1;

DELETE FROM usuario;
