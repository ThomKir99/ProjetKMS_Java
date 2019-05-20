
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
*/