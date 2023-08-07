# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 11.0.2-MariaDB)
# Database: test
# Generation Time: 2023-08-07 16:05:41 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table AUTHOR
# ------------------------------------------------------------

DROP TABLE IF EXISTS `AUTHOR`;

CREATE TABLE `AUTHOR` (
  `name` varchar(50) DEFAULT NULL,
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `paperId` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `paperid` (`paperId`),
  KEY `author_ibfk_1` (`paperId`),
  CONSTRAINT `author_ibfk_1` FOREIGN KEY (`paperId`) REFERENCES `PAPER` (`paperId`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `AUTHOR` WRITE;
/*!40000 ALTER TABLE `AUTHOR` DISABLE KEYS */;

INSERT INTO `AUTHOR` (`name`, `id`, `paperId`)
VALUES
	('Xingyu Liu',1,'2307.00865'),
	('Juan Chen',2,'2307.00865'),
	('Quan Wen',3,'2307.00865'),
	('Ashish Vaswani',4,'1706.03762'),
	('Noam Shazeer',7,'1706.03762'),
	('Niki Parmar',9,'1706.03762'),
	('Derya Soydaner',10,'2204.13154');

/*!40000 ALTER TABLE `AUTHOR` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table KEYWORD
# ------------------------------------------------------------

DROP TABLE IF EXISTS `KEYWORD`;

CREATE TABLE `KEYWORD` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(200) DEFAULT NULL,
  `username` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `FK_USER_TO_KEYWORD_1` (`username`),
  CONSTRAINT `FK_USER_TO_KEYWORD_1` FOREIGN KEY (`username`) REFERENCES `USER` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table LIKEPAPER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LIKEPAPER`;

CREATE TABLE `LIKEPAPER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `paperId` varchar(200) NOT NULL,
  `isValid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_TO_QUERYCONTAINER_1` (`username`),
  KEY `FK_PAPER_TO_QUERYCONTAINER_1` (`paperId`),
  CONSTRAINT `FK_PAPER_TO_QUERYCONTAINER_1` FOREIGN KEY (`paperId`) REFERENCES `PAPER` (`paperId`),
  CONSTRAINT `FK_USER_TO_QUERYCONTAINER_1` FOREIGN KEY (`username`) REFERENCES `USER` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table PAPER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAPER`;

CREATE TABLE `PAPER` (
  `paperId` varchar(200) NOT NULL DEFAULT '',
  `paperCode` bigint(20) NOT NULL AUTO_INCREMENT,
  `conference` varchar(200) DEFAULT NULL,
  `cites` int(11) DEFAULT 0,
  `likes` int(11) DEFAULT 0,
  `lastUpdate` date DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `submitter` varchar(50) DEFAULT NULL,
  `comments` varchar(50) DEFAULT NULL,
  `journalRef` varchar(50) DEFAULT NULL,
  `doi` varchar(50) DEFAULT NULL,
  `categories` varchar(50) DEFAULT NULL,
  `license` varchar(300) DEFAULT NULL,
  `summary` varchar(50) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  `update_date` date DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`paperId`),
  UNIQUE KEY `paperCode` (`paperCode`)
) ENGINE=InnoDB AUTO_INCREMENT=2379 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `PAPER` WRITE;
/*!40000 ALTER TABLE `PAPER` DISABLE KEYS */;

INSERT INTO `PAPER` (`paperId`, `paperCode`, `conference`, `cites`, `likes`, `lastUpdate`, `title`, `submitter`, `comments`, `journalRef`, `doi`, `categories`, `license`, `summary`, `version`, `update_date`, `year`)
VALUES
	('1706.03762',2308,NULL,0,0,NULL,'Attention is all you need',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
	('2010.01369',2349,NULL,0,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),
	('2204.13154',2362,NULL,0,0,NULL,'Attention Mechanism in Neural Networks: Where it Comes and Where it Goes',NULL,NULL,NULL,NULL,NULL,NULL,'A long time ago in the machine learning literature',NULL,NULL,NULL),
	('2307.00865',2307,'',0,0,NULL,'A Survey on Graph Classification and Link Prediction based on GNN',NULL,NULL,NULL,NULL,NULL,NULL,'Traditional convolutional neural networks are limi',NULL,NULL,2023);

/*!40000 ALTER TABLE `PAPER` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table PAPERBUFFER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAPERBUFFER`;

CREATE TABLE `PAPERBUFFER` (
  `paperId` varchar(200) NOT NULL,
  `ishit` tinyint(1) DEFAULT 0,
  `uddate` date DEFAULT NULL,
  PRIMARY KEY (`paperId`),
  CONSTRAINT `FK_PAPER_TO_PAPERBUFFER_1` FOREIGN KEY (`paperId`) REFERENCES `PAPER` (`paperId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table PAPERINFO
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAPERINFO`;

CREATE TABLE `PAPERINFO` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `paperId` varchar(200) NOT NULL,
  `infotype` varchar(200) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PAPER_TO_PAPERINFO_1` (`paperId`),
  CONSTRAINT `FK_PAPER_TO_PAPERINFO_1` FOREIGN KEY (`paperId`) REFERENCES `PAPER` (`paperId`)
) ENGINE=InnoDB AUTO_INCREMENT=226 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table QUERYHISTORY
# ------------------------------------------------------------

DROP TABLE IF EXISTS `QUERYHISTORY`;

CREATE TABLE `QUERYHISTORY` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `paperId` varchar(20) NOT NULL,
  `idx` int(11) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `who` tinyint(1) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_TO_QUERYHISTORY_1` (`username`),
  KEY `FK_PAPER_TO_QUERYHISTORY_1` (`paperId`),
  CONSTRAINT `FK_PAPER_TO_QUERYHISTORY_1` FOREIGN KEY (`paperId`) REFERENCES `PAPER` (`paperId`),
  CONSTRAINT `FK_USER_TO_QUERYHISTORY_1` FOREIGN KEY (`username`) REFERENCES `USER` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table SearchHistory
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SearchHistory`;

CREATE TABLE `SearchHistory` (
  `rid` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(200) NOT NULL DEFAULT '',
  `query` varchar(2000) NOT NULL DEFAULT '',
  `answer` varchar(2000) NOT NULL DEFAULT '',
  `searchDate` date DEFAULT NULL,
  `searchType` int(20) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  PRIMARY KEY (`rid`),
  KEY `FK_USER_TO_SearchHistory_1` (`username`),
  CONSTRAINT `FK_USER_TO_SearchHistory_1` FOREIGN KEY (`username`) REFERENCES `USER` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table SearchHistoryPaper
# ------------------------------------------------------------

DROP TABLE IF EXISTS `SearchHistoryPaper`;

CREATE TABLE `SearchHistoryPaper` (
  `paperId` varchar(20) NOT NULL,
  `rid` bigint(20) NOT NULL,
  KEY `FK_PAPER_TO_SearchHistoryPaper_1` (`paperId`),
  KEY `FK_SearchHistory_TO_SearchHistoryPaper_1` (`rid`),
  CONSTRAINT `FK_PAPER_TO_SearchHistoryPaper_1` FOREIGN KEY (`paperId`) REFERENCES `PAPER` (`paperId`),
  CONSTRAINT `FK_SearchHistory_TO_SearchHistoryPaper_1` FOREIGN KEY (`rid`) REFERENCES `SearchHistory` (`rid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table STUDYFIELD
# ------------------------------------------------------------

DROP TABLE IF EXISTS `STUDYFIELD`;

CREATE TABLE `STUDYFIELD` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `studyfield` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1641 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table USER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
  `username` varchar(200) NOT NULL DEFAULT '',
  `password` varchar(50) NOT NULL DEFAULT '',
  `isfirst` tinyint(1) DEFAULT 1,
  `studyField` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
