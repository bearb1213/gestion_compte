CREATE TABLE compte(
   id INT AUTO_INCREMENT,
   id_particulier INT NOT NULL,
   numero VARCHAR(50) NOT NULL,
   date_ouverture DATE,
   solde DECIMAL(18,2),
   PRIMARY KEY(id),
   UNIQUE(numero)
);

CREATE TABLE fixe(
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   valeur DECIMAL(4,2) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE type_transaction(
   id INT AUTO_INCREMENT,
   libelle VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE transaction(
   id INT AUTO_INCREMENT,
   montant DECIMAL(15,2) NOT NULL,
   date_transaction DATETIME NOT NULL,
   description TEXT,
   mouvement CHAR(1) NOT NULL,
   id_type INT NOT NULL,
   id_compte INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES type_transaction(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id)
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
   libelle VARCHAR(50) NOT NULL,
   valeur DECIMAL(15,2) NOT NULL,
   date_changement DATETIME NOT NULL,
   supprime CHAR(1) NOT NULL,
   id_compte INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_compte) REFERENCES compte(id)
);
