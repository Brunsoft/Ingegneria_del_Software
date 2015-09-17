/* Script per DataBase MySQL 
 *
 * NB. se si intende eseguire questo script su un db postgress sostituire AUTO_INCREMENT con SERIAL
 *
*/
DROP TABLE Posizione;
DROP TABLE Furto;
DROP TABLE Gestione;
DROP TABLE Utente;
DROP TABLE Allarme;
DROP TABLE Veicolo;

CREATE TABLE Utente (
	mail VARCHAR(30) NOT NULL,
	nome VARCHAR(20) NOT NULL,
	cognome VARCHAR(20) NOT NULL,
	pwd VARCHAR(10) NOT NULL,
	tipo CHAR(1) NOT NULL,
	tel VARCHAR(11) NOT NULL,
	PRIMARY KEY (mail)
); 

CREATE TABLE Veicolo (
	targa VARCHAR(8) PRIMARY KEY,
	marca VARCHAR(20) NOT NULL,
	modello VARCHAR(20) NOT NULL,
	limite INT(3),
	latitudine DECIMAL(10,7),
	longitudine DECIMAL(10,7),
	data DATE,
	ora TIME
);

CREATE TABLE Allarme (
	id INT(4) AUTO_INCREMENT,
	targa VARCHAR(8) NOT NULL,
	dVel INT(3) NOT NULL,
	velocita INT(3) NOT NULL,
	data DATE NOT NULL,
	ora TIME NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY(targa) REFERENCES Veicolo( targa ) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Gestione (
	targa VARCHAR(8) NOT NULL,
	mail VARCHAR(30) NOT NULL,
	PRIMARY KEY (targa, mail),
	FOREIGN KEY(targa) REFERENCES Veicolo( targa ) 
        ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY(mail) REFERENCES Utente( mail ) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Furto (
	id INT(6) AUTO_INCREMENT,
	targa VARCHAR(8) NOT NULL,
	data DATE NOT NULL,
	ora TIME NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY(targa) REFERENCES Veicolo( targa ) 
        ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Posizione (
	id INT(6) AUTO_INCREMENT,
	id_furto INT(6),
	latitudine DECIMAL(10,7) NOT NULL,
	longitudine DECIMAL(10,7) NOT NULL,
	ora TIME NOT NULL,
	PRIMARY KEY (id, id_furto),
	FOREIGN KEY(id_furto) REFERENCES Furto( id ) 
        ON DELETE CASCADE ON UPDATE CASCADE
); 

-- INSERIMENTO DATI IN UTENTE
INSERT INTO Utente (nome, cognome, pwd, tipo, mail, tel) VALUES 
('Luca','Vicentini','hellas','A','luca@gmail.com','3479247800'),
('Kevin','Mansoldo','hellas','U','kevin@gmail.com','3407352800'),
('Matteo','Dal Monte', 'hellas', 'U', 'matteo@gmail.com', '3475689800');

INSERT INTO Veicolo (targa, latitudine, longitudine, data, ora) VALUES 
('EV528LB', '45.4017960', '11.0067410', '2015-09-02', '16:46:38');

INSERT INTO Allarme (targa, dVel, velocita, data, ora) VALUES 
('AB123CZ', '30', '160', '2015-09-05', '16:10:10');

INSERT INTO Furto (id, targa, data, ora) values 
('1', 'AB123CZ', '2015-09-05', '21:55:10');

INSERT INTO Posizione (id, id_furto, latitudine, longitudine, ora) VALUES
(1, 1, '45.393923', '11.270056', '21:55:10'),
(2, 1, '45.393538', '11.269954', '21:55:12'),
(3, 1, '45.393293', '11.269895', '21:55:14'),
(4, 1, '45.393493', '11.269241', '21:55:16'),
(5, 1, '45.393512', '11.268967', '21:55:18'),
(6, 1, '45.393146', '11.269482', '21:55:20'),
(7, 1, '45.392747', '11.269815', '21:55:22'),
(8, 1, '45.392242', '11.269745', '21:55:24'),
(9, 1, '45.391643', '11.269493', '21:55:26'),
(10, 1, '45.391221', '11.269369', '21:55:28'),
(11, 1, '45.390796', '11.269707', '21:55:30'),
(12, 1, '45.390283', '11.269976', '21:55:32'),
(13, 1, '45.389669', '11.270453', '21:55:34');

INSERT INTO Furto (id, targa, data, ora) VALUES 
('2', 'EV528LB', '2015-09-06', '13:20:55');

INSERT INTO Posizione (id, id_furto, latitudine, longitudine, ora) VALUES
(1, 2, '45.518173', '11.240301', '13:20:55'),
(2, 2, '45.518113', '11.240945', '13:20:57'),
(3, 2, '45.518365', '11.241149', '13:20:59'),
(4, 2, '45.518745', '11.241224', '13:21:01'),
(5, 2, '45.518756', '11.240596', '13:21:03'),
(6, 2, '45.518812', '11.239861', '13:21:05'),
(7, 2, '45.519211', '11.240001', '13:21:07'),
(8, 2, '45.519684', '11.240129', '13:21:09'),
(9, 2, '45.520184', '11.240011', '13:21:11'),
(10, 2, '45.520267', '11.239963', '13:21:13'),
(11, 2, '45.520658', '11.239679', '13:21:15'),
(12, 2, '45.520940', '11.239443', '13:21:17'),
(13, 2, '45.521315', '11.239174', '13:21:19');
