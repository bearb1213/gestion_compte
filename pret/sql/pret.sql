CREATE DATABASE pret;
use pret;

CREATE USER 'pret'@'localhost' IDENTIFIED BY 'pret';
GRANT ALL PRIVILEGES ON pret.* TO 'pret'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE compte(
   id INT AUTO_INCREMENT,
   numero VARCHAR(50) NOT NULL,
   date_ouverture DATE NOT NULL,
   id_particulier INT NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(numero),
   UNIQUE(id_particulier)
);

CREATE TABLE type(
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE status_remboursement(
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE status(
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE histo_status(
   id INT AUTO_INCREMENT,
   date_changement DATETIME NOT NULL,
   id_compte INT NOT NULL,
   id_status INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id),
   FOREIGN KEY(id_status) REFERENCES status(id)
);

CREATE TABLE pret(
   id INT AUTO_INCREMENT,
   montant DECIMAL(18,2) NOT NULL,
   date_commencement DATE NOT NULL,
   taux DECIMAL(5,2) NOT NULL,
   duree_mois INT,
   id_type INT NOT NULL,
   id_compte INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES type(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id)
);

CREATE TABLE remboursement(
   id INT AUTO_INCREMENT,
   date_prevue DATE NOT NULL,
   date_payement DATE,
   montant_total DECIMAL(15,2) NOT NULL,
   montant_capital DECIMAL(15,2) NOT NULL,
   montant_interet DECIMAL(15,2) NOT NULL,
   montant_assurance DECIMAL(15,2),
   id_status INT NOT NULL,
   id_pret INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_status) REFERENCES status_remboursement(id),
   FOREIGN KEY(id_pret) REFERENCES pret(id)
);

CREATE TABLE histo_status_remboursement(
   id INT AUTO_INCREMENT,
   date_paiment DATE NOT NULL,
   montant DECIMAL(15,2) NOT NULL,
   id_remboursement INT NOT NULL,
   id_status INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_remboursement) REFERENCES remboursement(id),
   FOREIGN KEY(id_status) REFERENCES status_remboursement(id)
);


INSERT INTO status (libelle) VALUES
('Actif'),
('Inactif'),
('Suspendu'),
('Fermer'),
('Bloquer');

INSERT INTO type (libelle) VALUES
('Personnel'),
('Immobilier'),
('Auto'),
('Etudiant'),
('Entreprise');

INSERT INTO status_remboursement (libelle) VALUES
('En cours'),
('Payer'),
('En retard'),
('Annuler');