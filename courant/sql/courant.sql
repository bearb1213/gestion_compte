CREATE DATABASE IF NOT EXISTS courant;
USE courant;

-- CREATE USER 'courant'@'localhost' IDENTIFIED BY 'courant';
-- GRANT ALL PRIVILEGES ON courant.* TO 'courant'@'localhost';
-- FLUSH PRIVILEGES;


CREATE TABLE IF NOT EXISTS particulie (
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
   id_particulier INT NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(numero_compte),
   FOREIGN KEY(id_particulier) REFERENCES particulie(id)
);

CREATE TABLE IF NOT EXISTS type_transaction (
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS status (
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
   FOREIGN KEY(id_status) REFERENCES status(id)
);

CREATE TABLE IF NOT EXISTS frais (
   id INT AUTO_INCREMENT,
   date_changement DATETIME NOT NULL,
   tenu_compte DECIMAL(15,2) NOT NULL,
   decouvert DECIMAL(15,2),
   id_compte INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id)
);

CREATE TABLE IF NOT EXISTS moyen_paiement (
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS type_revenu (
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS transaction (
   id INT AUTO_INCREMENT,
   montant DECIMAL(15,2) NOT NULL,
   date_transaction DATETIME NOT NULL,
   description TEXT,
   mouvement CHAR(1) NOT NULL,
   montant_actuel DECIMAL(18,2) NOT NULL,
   date_validation DATETIME ,
   id_moyen INT, 
   id_type_transaction INT,
   id_compte INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_moyen) REFERENCES moyen_paiement(id),
   FOREIGN KEY(id_type_transaction) REFERENCES type_transaction(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id)
);

CREATE TABLE IF NOT EXISTS revenu (
   id INT AUTO_INCREMENT,
   montant DECIMAL(15,2) NOT NULL,
   date_changement DATE NOT NULL,
   id_type INT NOT NULL,
   id_particulier INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES type_revenu(id),
   FOREIGN KEY(id_particulier) REFERENCES particulie(id)
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


INSERT INTO type_transaction (libelle) VALUES
('Dépôt'),
('Retrait'),
('Virement'),
('Paiement'),
('Frais bancaires'),
('Intérêts'),
('Versement salaire'),
('Remboursement prêt'),
('Transfert interne'),
('Achat en ligne');

INSERT INTO status (libelle) VALUES
('Actif'),
('Inactif'),
('Suspendu'),
('Fermer'),
('Bloquer');

INSERT INTO moyen_paiement (libelle) VALUES
('Espèces'),
('Chèque'),
('Carte bancaire'),
('Virement bancaire'),
('Paiement mobile'),
('Transfert électronique'),
('Chèque de banque'),
('Cryptomonnaie'),
('Ordre permanent'),
('Prélèvement automatique');

INSERT INTO type_revenu (libelle) VALUES
('Salaire'),
('Business'),
('Commerce'),
('Freelance'),
('Agriculture'),
('Location immobiliere'),
('Investissement'),
('Bourse d etudes'),
('Pension'),
('Autres');
