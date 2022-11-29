DROP SCHEMA IF EXISTS webtodolist;
CREATE SCHEMA webtodolist;
USE webtodolist;

CREATE TABLE users (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    username VARCHAR(20) NOT NULL,
    password VARCHAR(256) NOT NULL,
    role ENUM("instructor","student")
);

CREATE TABLE todos (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    description VARCHAR(256) NOT NULL
);