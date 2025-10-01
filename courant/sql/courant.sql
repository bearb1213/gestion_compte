CREATE TABLE particulie(
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

CREATE TABLE compte(
   id INT AUTO_INCREMENT,
   numero_compte VARCHAR(50) NOT NULL,
   date_ouverture DATE NOT NULL,
   montant_decouvert VARCHAR(50),
   id_particulier INT NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(numero_compte),
   FOREIGN KEY(id_particulier) REFERENCES particulie(id)
);

CREATE TABLE type_transaction(
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

CREATE TABLE frais(
   id INT AUTO_INCREMENT,
   date_changement DATETIME NOT NULL,
   tenu_compte DECIMAL(15,2) NOT NULL,
   decouvert DECIMAL(15,2),
   id_compte INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id)
);

CREATE TABLE moyen_paiement(
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE type_revenu(
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE transaction(
   id INT AUTO_INCREMENT,
   montant DECIMAL(15,2) NOT NULL,
   date_transaction DATETIME NOT NULL,
   description TEXT,
   id_moyen INT,
   id_type_transaction INT NOT NULL,
   id_compte INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_moyen) REFERENCES moyen_paiement(id),
   FOREIGN KEY(id_type_transaction) REFERENCES type_transaction(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id)
);

CREATE TABLE revenu(
   id INT AUTO_INCREMENT,
   montant DECIMAL(15,2) NOT NULL,
   date_changement DATE NOT NULL,
   id_type INT NOT NULL,
   id_particulier INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES type_revenu(id),
   FOREIGN KEY(id_particulier) REFERENCES particulie(id)
);
