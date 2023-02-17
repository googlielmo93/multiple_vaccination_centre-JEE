-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              8.0.27 - MySQL Community Server - GPL
-- S.O. server:                  Win64
-- HeidiSQL Versione:            11.3.0.6295
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dump della struttura del database googlielmo93
CREATE DATABASE IF NOT EXISTS `googlielmo93` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `googlielmo93`;

-- Dump della struttura di tabella googlielmo93.centro_vaccinale
CREATE TABLE IF NOT EXISTS `centro_vaccinale` (
  `id_centro_vaccinale` int unsigned NOT NULL AUTO_INCREMENT,
  `denominazione` varchar(50) NOT NULL,
  `comune` varchar(50) NOT NULL,
  `provincia` varchar(50) NOT NULL,
  `regione` varchar(50) NOT NULL,
  `indirizzo` varchar(50) NOT NULL,
  PRIMARY KEY (`id_centro_vaccinale`),
  UNIQUE KEY `denominazione_comune_provincia_regione_indirizzo` (`denominazione`,`comune`,`provincia`,`regione`,`indirizzo`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

-- Dump dei dati della tabella googlielmo93.centro_vaccinale: ~4 rows (circa)
/*!40000 ALTER TABLE `centro_vaccinale` DISABLE KEYS */;
INSERT INTO `centro_vaccinale` (`id_centro_vaccinale`, `denominazione`, `comune`, `provincia`, `regione`, `indirizzo`) VALUES
	(1, 'Centro vaccinale - Fiera del Mediterraneo', 'Palermo', 'Palermo', 'Sicilia', 'via della fiera del mediterraneo'),
	(2, 'farmacia Ciancio - stazione santa flavia', 'Santa Flavia', 'Palermo', 'Sicilia', 'corso dei Mille'),
	(4, 'farmacia Del Ricco - Bagheria Centro', 'Santa Flavia', 'Palermo', 'Sicilia', 'via della matrice'),
	(3, 'farmacia Sorce- Porticello', 'Santa Flavia', 'Palermo', 'Sicilia', 'via della chiesa');
/*!40000 ALTER TABLE `centro_vaccinale` ENABLE KEYS */;

-- Dump della struttura di tabella googlielmo93.dipendente_approvato
CREATE TABLE IF NOT EXISTS `dipendente_approvato` (
  `id_dipendente_approvato` int unsigned NOT NULL AUTO_INCREMENT,
  `id_user` int unsigned NOT NULL,
  `id_centro_vaccinale` int unsigned NOT NULL,
  `flag_approvato` int unsigned NOT NULL DEFAULT '0' COMMENT '0 = non approvato, 1 = approvato',
  PRIMARY KEY (`id_dipendente_approvato`) USING BTREE,
  UNIQUE KEY `id_user_id_centro_vaccinale` (`id_user`,`id_centro_vaccinale`),
  KEY `id_centro_vaccinale_dipendente` (`id_centro_vaccinale`),
  CONSTRAINT `id_centro_vaccinale_dipendente` FOREIGN KEY (`id_centro_vaccinale`) REFERENCES `centro_vaccinale` (`id_centro_vaccinale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_user_dipendente` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb3;

-- Dump dei dati della tabella googlielmo93.dipendente_approvato: ~3 rows (circa)
/*!40000 ALTER TABLE `dipendente_approvato` DISABLE KEYS */;
INSERT INTO `dipendente_approvato` (`id_dipendente_approvato`, `id_user`, `id_centro_vaccinale`, `flag_approvato`) VALUES
	(1, 2, 2, 1),
	(2, 3, 2, 1),
	(3, 4, 2, 1);
/*!40000 ALTER TABLE `dipendente_approvato` ENABLE KEYS */;

-- Dump della struttura di tabella googlielmo93.giorni_apertura_centro_vaccinale
CREATE TABLE IF NOT EXISTS `giorni_apertura_centro_vaccinale` (
  `id_giorno_apertura` int NOT NULL AUTO_INCREMENT,
  `data` timestamp NOT NULL,
  `id_centro_vaccinale` int unsigned NOT NULL,
  `posti_disponibili` int NOT NULL,
  `numero_tabellone` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_giorno_apertura`),
  UNIQUE KEY `data_id_centro_vaccinale` (`data`,`id_centro_vaccinale`),
  KEY `giorni_apertura_centro_vaccinale` (`id_centro_vaccinale`),
  CONSTRAINT `giorni_apertura_centro_vaccinale` FOREIGN KEY (`id_centro_vaccinale`) REFERENCES `centro_vaccinale` (`id_centro_vaccinale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8mb3;

-- Dump dei dati della tabella googlielmo93.giorni_apertura_centro_vaccinale: ~8 rows (circa)
/*!40000 ALTER TABLE `giorni_apertura_centro_vaccinale` DISABLE KEYS */;
INSERT INTO `giorni_apertura_centro_vaccinale` (`id_giorno_apertura`, `data`, `id_centro_vaccinale`, `posti_disponibili`, `numero_tabellone`) VALUES
	(99, '2021-11-19 00:00:00', 4, 45, 0),
	(100, '2021-11-20 00:00:00', 4, 45, 0),
	(101, '2021-11-19 00:00:00', 1, 150, 0),
	(102, '2021-11-20 00:00:00', 1, 150, 0),
	(103, '2021-11-19 00:00:00', 3, 45, 0),
	(104, '2021-11-20 00:00:00', 3, 45, 0),
	(106, '2021-11-19 00:00:00', 2, 44, 0),
	(107, '2021-11-20 00:00:00', 2, 45, 0);
/*!40000 ALTER TABLE `giorni_apertura_centro_vaccinale` ENABLE KEYS */;

-- Dump della struttura di tabella googlielmo93.prenotazione
CREATE TABLE IF NOT EXISTS `prenotazione` (
  `codice_prenotazione` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `data_prenotazione` timestamp NOT NULL,
  `data_inserimento` timestamp NOT NULL,
  `stato_completato` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '0 = non completato, 1 = completato, cioè somministrazione avvenuta',
  `id_user` int unsigned NOT NULL,
  `id_centro_vaccinale` int unsigned NOT NULL,
  PRIMARY KEY (`codice_prenotazione`),
  KEY `user_prenotazione` (`id_user`),
  KEY `centro-vaccinale_prenotazione` (`id_centro_vaccinale`),
  CONSTRAINT `centro-vaccinale_prenotazione` FOREIGN KEY (`id_centro_vaccinale`) REFERENCES `centro_vaccinale` (`id_centro_vaccinale`) ON UPDATE CASCADE,
  CONSTRAINT `user_prenotazione` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

-- Dump dei dati della tabella googlielmo93.prenotazione: ~0 rows (circa)
/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;

-- Dump della struttura di tabella googlielmo93.somministrazione
CREATE TABLE IF NOT EXISTS `somministrazione` (
  `id_somministrazione` int unsigned NOT NULL AUTO_INCREMENT,
  `tipo_vaccino` varchar(50) NOT NULL,
  `codice_vaccino` varchar(50) NOT NULL,
  `lotto` varchar(50) NOT NULL,
  `data_somministrazione` timestamp NOT NULL,
  `codice_prenotazione` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `id_user` int unsigned NOT NULL,
  `id_medico` int unsigned NOT NULL,
  `id_centro_vaccinale` int unsigned NOT NULL,
  PRIMARY KEY (`id_somministrazione`),
  UNIQUE KEY `codice_vaccino` (`codice_vaccino`),
  KEY `prenotazione_somministrazione` (`codice_prenotazione`),
  KEY `centro_vaccinale` (`id_centro_vaccinale`),
  KEY `user_somministrazione` (`id_medico`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `centro_vaccinale` FOREIGN KEY (`id_centro_vaccinale`) REFERENCES `centro_vaccinale` (`id_centro_vaccinale`),
  CONSTRAINT `codice_prenotazione` FOREIGN KEY (`codice_prenotazione`) REFERENCES `prenotazione` (`codice_prenotazione`) ON UPDATE CASCADE,
  CONSTRAINT `id_medico` FOREIGN KEY (`id_medico`) REFERENCES `user` (`id_user`) ON UPDATE CASCADE,
  CONSTRAINT `id_user` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;

-- Dump dei dati della tabella googlielmo93.somministrazione: ~0 rows (circa)
/*!40000 ALTER TABLE `somministrazione` DISABLE KEYS */;
/*!40000 ALTER TABLE `somministrazione` ENABLE KEYS */;

-- Dump della struttura di tabella googlielmo93.turno
CREATE TABLE IF NOT EXISTS `turno` (
  `id_turno` int unsigned NOT NULL AUTO_INCREMENT,
  `id_centro_vaccinale` int unsigned NOT NULL,
  `codice_prenotazione` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `data_inserimento` timestamp NOT NULL,
  PRIMARY KEY (`id_turno`),
  UNIQUE KEY `codice_prenotazione_data_inserimento` (`codice_prenotazione`,`data_inserimento`),
  KEY `centro_vaccinale_turno` (`id_centro_vaccinale`),
  CONSTRAINT `centro_vaccinale_turno` FOREIGN KEY (`id_centro_vaccinale`) REFERENCES `centro_vaccinale` (`id_centro_vaccinale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb3;

-- Dump dei dati della tabella googlielmo93.turno: ~0 rows (circa)
/*!40000 ALTER TABLE `turno` DISABLE KEYS */;
/*!40000 ALTER TABLE `turno` ENABLE KEYS */;

-- Dump della struttura di tabella googlielmo93.user
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int unsigned NOT NULL AUTO_INCREMENT,
  `USER_LOGIN` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `USER_PSWD` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `USER_ROLE` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'user',
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sesso` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'N/D',
  `codice_fiscale` varchar(16) NOT NULL,
  `data_nascita` timestamp NOT NULL,
  `data_registrazione` timestamp NOT NULL,
  `citta_residenza` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `provincia_residenza` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `regione_residenza` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id_user`) USING BTREE,
  UNIQUE KEY `USER_LOGIN` (`USER_LOGIN`),
  UNIQUE KEY `USER_ROLE_codice_fiscale` (`USER_ROLE`,`codice_fiscale`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8mb3 COMMENT='Tabella contenente i dati di accesso. Include i ruoli degli utenti registrati e permette l''accesso alle aree protette del sito. Include i dati principali degli utenti, e la chiave esterna alla tabella prenotazione';

-- Dump dei dati della tabella googlielmo93.user: ~5 rows (circa)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id_user`, `USER_LOGIN`, `USER_PSWD`, `USER_ROLE`, `nome`, `cognome`, `email`, `sesso`, `codice_fiscale`, `data_nascita`, `data_registrazione`, `citta_residenza`, `provincia_residenza`, `regione_residenza`) VALUES
	(1, 'user_test', '-1095334296', 'user', 'mario', 'rossi', 'user_test@gmail.com', 'M', 'RSSMRA80A01G273Z', '1980-01-01 00:00:00', '2021-11-12 17:44:26', 'Palermo', 'Palermo', 'Sicilia'),
	(2, 'asp_test', '1850069269', 'asp', 'carlo', 'verdoni', 'asp_test@gmail.com', 'N/D', 'VRDCRL60A01H501N', '2021-11-12 17:47:45', '2021-11-12 17:47:45', '', '', ''),
	(3, 'medico_test', '-212225366', 'medico', 'Mario', 'Bianchi', 'medico_test@gmail.com', 'N/D', 'BNCMRA93A01G273K', '2021-11-12 19:46:15', '2021-11-12 19:46:15', '', '', ''),
	(4, 'accettazione_test', '687773561', 'accettazione', 'Paolo', 'Giallo', 'accettazione_test@gmail.com', 'N/D', 'GLLPLA83A01F205R', '2021-11-12 20:13:15', '2021-11-12 20:13:15', '', '', ''),
	(5, 'user_test2', '345359182', 'user', 'Mario', 'Mari', 'user_test2@gmail.com', 'M', 'MRAMRA71A01G273W', '1971-01-01 00:00:00', '2021-11-18 18:48:37', 'Santa Flavia', 'Palermo', 'Sicilia');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
