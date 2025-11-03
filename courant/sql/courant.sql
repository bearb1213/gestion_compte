CREATE DATABASE IF NOT EXISTS courant;
USE courant;

-- CREATE USER 'courant'@'localhost' IDENTIFIED BY 'courant';
-- GRANT ALL PRIVILEGES ON courant.* TO 'courant'@'localhost';
-- FLUSH PRIVILEGES;


CREATE TABLE IF NOT EXISTS particulier (
   id INT AUTO_INCREMENT,
   cin VARCHAR(50),
   nom VARCHAR(50) NOT NULL,
   prenom VARCHAR(50),
   date_naissance DATE NOT NULL,
   adresse VARCHAR(50),
   telephone VARCHAR(50),
   email VARCHAR(50),
   PRIMARY KEY(id),
   UNIQUE(cin),
   UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS compte (
   id INT AUTO_INCREMENT,
   numero_compte VARCHAR(50) NOT NULL,
   date_ouverture DATE NOT NULL,
   montant_decouvert DECIMAL(15,2),
   plafond DECIMAL(15,2),
   id_particulier INT NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(numero_compte),
   FOREIGN KEY(id_particulier) REFERENCES particulier(id)
);

CREATE TABLE IF NOT EXISTS status_compte (
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS histo_status (
   id INT AUTO_INCREMENT,
   date_changement DATETIME NOT NULL,
   id_compte INT NOT NULL,
   id_status INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id),
   FOREIGN KEY(id_status) REFERENCES status_compte(id)
);


CREATE TABLE IF NOT EXISTS direction (
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   niveau INT NOT NULL,
   role INT NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS action_role (
   id INT AUTO_INCREMENT,
   nom_table VARCHAR(50) NOT NULL,
   action VARCHAR(50) NOT NULL,
   role INT NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS utilisateur (
   id INT AUTO_INCREMENT,
   username VARCHAR(50) NOT NULL,
   password VARCHAR(255) NOT NULL,
   id_direction INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_direction) REFERENCES direction(id)
);





CREATE TABLE IF NOT EXISTS mouvement (
   id INT AUTO_INCREMENT,
   montant DECIMAL(15,2) NOT NULL,
   id_compte INT NOT NULL, 
   mvnt CHAR(1) NOT NULL,
   description TEXT ,
   PRIMARY KEY(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id),
);

CREATE TABLE IF NOT EXISTS type_transaction (
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);




CREATE TABLE IF NOT EXISTS change(
   id int AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS histo_change(
   id int AUTO_INCREMENT,
   montant DECIMAL(15,2) not null,
   date_changement DATETIME not null,
   id_change int not null,
   PRIMARY KEY(id);
   FOREIGN KEY(id_change) REFERENCES change(id),
);



CREATE TABLE IF NOT EXISTS transaction (
   id INT AUTO_INCREMENT,
   id_compte_origine int NOT NULL,
   id_compte_arrive int ,
   date_effectif DATE NOT NULL,
   montant_ar DECIMAL(15,2) NOT NULL,
   montant_entrer  DECIMAL(15,2) NOT NULL,
   id_type int not null ,
   id_change int not null ,
   PRIMARY KEY(id),
   FOREIGN KEY(id_compte_origine) REFERENCES compte(id),
   FOREIGN KEY(id_compte_arrive) REFERENCES compte(id),
   FOREIGN KEY(id_change) REFERENCES histo_change(id),
   FOREIGN KEY(id_type_transaction) REFERENCES type_transaction(id)
);

CREATE TABLE IF NOT EXISTS status_transaction(
  id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS histo_status_transaction(
   id int AUTO_INCREMENT,
   id_transaction INT NOT NULL ,
   date_changement DATETIME NOT null,
   id_status int NOT null,
   PRIMARY KEY(id),
   FOREIGN KEY(id_transaction) REFERENCES transaction(id),
   FOREIGN KEY(id_status) REFERENCES status_transaction(id)
   
);





INSERT INTO direction (libelle, niveau, role) VALUES
('Admin', 10, 10),
('Directeur', 9, 9),
('Chef de service', 8, 8),
('Gestionnaire', 7, 7),
('Conseiller', 6, 6),
('Client', 1, 1),
('Auditeur', 5, 5);

INSERT INTO action_role (nom_table, action, role) VALUES
('particulier', 'CREATE', 6),
('particulier', 'READ', 6),
('particulier', 'UPDATE', 6),
('particulier', 'DELETE', 7),
('compte', 'CREATE', 7),
('compte', 'READ', 6),
('compte', 'UPDATE', 7),
('compte', 'DELETE', 8),
('transaction', 'CREATE', 6),
('transaction', 'READ', 6),
('transaction', 'UPDATE', 7),
('transaction', 'DELETE', 8);

INSERT INTO utilisateur (username, password, id_direction) VALUES
('admin', 'adminpass', 1),
('directeur', 'directeurpass', 2),
('chefservice', 'chefservicepass', 3),
('gestionnaire', 'gestionnairepass', 4),
('conseiller', 'conseillerpass', 5),
('client', 'clientpass', 6),
('auditeur', 'auditeurpass', 7);


INSERT INTO status_compte (libelle) VALUES
('Actif'),
('Inactif'),
('Bloquer');
