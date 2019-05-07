
drop database sys;
create database sys;
use sys ;

CREATE TABLE IF NOT EXISTS tbl_utilisateur(
id_utilisateur int UNIQUE PRIMARY KEY NOT NULL AUTO_INCREMENT,
nom VARCHAR(200) NOT NULL,
mots_de_passe VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_projet (
id_projet int PRIMARY KEY NOT NULL AUTO_INCREMENT,
nom_projet VARCHAR(200),
id_utilisateur int ,
color_project VARCHAR(200),
date_projet_ouvert datetime ,
CONSTRAINT FK_id_utilisateur FOREIGN KEY (id_utilisateur)
REFERENCES tbl_utilisateur(id_utilisateur)
ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tbl_groupe (
id_groupe int PRIMARY KEY NOT NULL AUTO_INCREMENT,
nom_groupe VARCHAR(200),
id_projet int ,
order_in_project int,
completion bool,
CONSTRAINT FK_id_projet FOREIGN KEY  (id_projet)
REFERENCES tbl_projet(id_projet)
ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS tbl_carte (
id_carte int PRIMARY KEY NOT NULL AUTO_INCREMENT,
nom VARCHAR(500) NOT NULL,
description VARCHAR(500) NOT NULL,
ordre_de_priorite int NOT NULL,
complete bool,
id_groupe int ,
CONSTRAINT FK_id_groupe FOREIGN KEY (id_groupe)
REFERENCES tbl_groupe(id_groupe)
ON DELETE CASCADE
);
create table if not exists tbl_depandance(
id_carte_depandante int not null,
id_carte_de_depandance int not null,
terminer bool
);
CREATE TABLE IF NOT EXISTS tbl_permission(
id_permission int PRIMARY KEY NOT NULL AUTO_INCREMENT,
id_projet int NOT NULL,
id_utilisateur int NOT NULL,
permission VARCHAR(500),
CONSTRAINT FK_id_projet1 FOREIGN KEY (id_projet) REFERENCES tbl_projet(id_projet),
CONSTRAINT FK_id_utilisateur1 FOREIGN KEY (id_utilisateur) REFERENCES tbl_utilisateur(id_utilisateur)
);

/*-----------------------------ADD DATA------------------------------------*/

/*Utilisateur*/
INSERT INTO tbl_utilisateur VALUES (1,"aaa@aa.com","123456");
INSERT INTO tbl_utilisateur VALUES (2,"bbb@bb.com","123456");
INSERT INTO tbl_utilisateur VALUES (3,"ccc@cc.com","123456");
INSERT INTO tbl_utilisateur VALUES (4,"a@a.com","1");

/*Projet*/
INSERT INTO tbl_projet(nom_projet,id_utilisateur,color_project,date_projet_ouvert) VALUES ("Projet Antoine", 1,"#FFFFFF", now());
INSERT INTO tbl_projet(nom_projet,id_utilisateur,color_project,date_projet_ouvert) VALUES ("Projet Antoine2", 1,"#FFFFFF", now()+1);
INSERT INTO tbl_projet(nom_projet,id_utilisateur,color_project,date_projet_ouvert) VALUES ("Projet Antoine3", 1,"#FFFFFF", now()+2);
INSERT INTO tbl_projet(nom_projet,id_utilisateur,color_project,date_projet_ouvert) VALUES ("Projet Dave", 2,"#FFFFFF", now()+3);
INSERT INTO tbl_projet(nom_projet,id_utilisateur,color_project,date_projet_ouvert) VALUES ("Projet Thomas", 3,"#FFFFFF", now()+4);

/*Group*/
INSERT INTO tbl_groupe VALUES (1,"Group 1", 4,1,false);
INSERT INTO tbl_groupe VALUES (2,"Group 2", 4,2,false);
INSERT INTO tbl_groupe VALUES (3,"Group 3", 1,1,false);
INSERT INTO tbl_groupe VALUES (4,"Group 4", 1,2,false);

/*Carte*/
INSERT INTO tbl_carte(nom,description,ordre_de_priorite,complete,id_groupe) VALUES ("Carte1","Une desc",1,false,2);
INSERT INTO tbl_carte(nom,description,ordre_de_priorite,complete,id_groupe) VALUES ("Carte2","Une desc",1,false,3);

/*Permission*/
INSERT INTO tbl_permission (id_projet,id_utilisateur,permission) VALUES(1,2,"READ");
INSERT INTO tbl_permission (id_projet,id_utilisateur,permission) VALUES(1,3,"WRITE");
INSERT INTO tbl_permission (id_projet,id_utilisateur,permission) VALUES(2,3,"READ");

/*Depandance*
