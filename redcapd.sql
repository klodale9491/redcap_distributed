-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versione server:              10.1.31-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win32
-- HeidiSQL Versione:            9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dump della struttura del database redcapd
CREATE DATABASE IF NOT EXISTS `redcapd` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `redcapd`;

-- Dump della struttura di tabella redcapd.field
CREATE TABLE IF NOT EXISTS `field` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(255) NOT NULL,
  `variable` varchar(255) NOT NULL,
  `form_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_field_form` (`form_id`),
  CONSTRAINT `FK_field_form` FOREIGN KEY (`form_id`) REFERENCES `form` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.field: ~0 rows (circa)
/*!40000 ALTER TABLE `field` DISABLE KEYS */;
/*!40000 ALTER TABLE `field` ENABLE KEYS */;

-- Dump della struttura di tabella redcapd.form
CREATE TABLE IF NOT EXISTS `form` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_form_project` (`project_id`),
  CONSTRAINT `FK_form_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.form: ~0 rows (circa)
/*!40000 ALTER TABLE `form` DISABLE KEYS */;
/*!40000 ALTER TABLE `form` ENABLE KEYS */;

-- Dump della struttura di tabella redcapd.language
CREATE TABLE IF NOT EXISTS `language` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `code` varchar(10) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code_UNIQUE` (`code`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.language: ~0 rows (circa)
/*!40000 ALTER TABLE `language` DISABLE KEYS */;
/*!40000 ALTER TABLE `language` ENABLE KEYS */;

-- Dump della struttura di tabella redcapd.project
CREATE TABLE IF NOT EXISTS `project` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.project: ~0 rows (circa)
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

-- Dump della struttura di tabella redcapd.project_role
CREATE TABLE IF NOT EXISTS `project_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `project_id` int(10) unsigned NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_role_project` (`project_id`),
  CONSTRAINT `FK_project_role_project` FOREIGN KEY (`project_id`) REFERENCES `project` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.project_role: ~0 rows (circa)
/*!40000 ALTER TABLE `project_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_role` ENABLE KEYS */;

-- Dump della struttura di tabella redcapd.project_role_field
CREATE TABLE IF NOT EXISTS `project_role_field` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `field_id` int(10) unsigned NOT NULL,
  `project_role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_project_role_field_field` (`field_id`),
  KEY `FK_project_role_field_project_role` (`project_role_id`),
  CONSTRAINT `FK_project_role_field_field` FOREIGN KEY (`field_id`) REFERENCES `field` (`id`),
  CONSTRAINT `FK_project_role_field_project_role` FOREIGN KEY (`project_role_id`) REFERENCES `project_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.project_role_field: ~0 rows (circa)
/*!40000 ALTER TABLE `project_role_field` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_role_field` ENABLE KEYS */;

-- Dump della struttura di tabella redcapd.user
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `language_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  KEY `FK_user_language` (`language_id`),
  CONSTRAINT `FK_user_language` FOREIGN KEY (`language_id`) REFERENCES `language` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.user: ~0 rows (circa)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dump della struttura di tabella redcapd.user_custom_field
CREATE TABLE IF NOT EXISTS `user_custom_field` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned DEFAULT NULL,
  `key` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk1_idx` (`user_id`),
  CONSTRAINT `FK_user_custom_field_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.user_custom_field: ~0 rows (circa)
/*!40000 ALTER TABLE `user_custom_field` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_custom_field` ENABLE KEYS */;

-- Dump della struttura di tabella redcapd.user_project_role
CREATE TABLE IF NOT EXISTS `user_project_role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `project_role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_project_role_user` (`user_id`),
  KEY `FK_user_project_role_project_role` (`project_role_id`),
  CONSTRAINT `FK_user_project_role_project_role` FOREIGN KEY (`project_role_id`) REFERENCES `project_role` (`id`),
  CONSTRAINT `FK_user_project_role_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dump dei dati della tabella redcapd.user_project_role: ~0 rows (circa)
/*!40000 ALTER TABLE `user_project_role` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_project_role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
