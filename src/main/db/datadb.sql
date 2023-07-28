# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 11.0.2-MariaDB)
# Database: test
# Generation Time: 2023-07-21 14:03:49 +0000
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
  CONSTRAINT `author_ibfk_1` FOREIGN KEY (`paperId`) REFERENCES `PAPER` (`paperId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `AUTHOR` WRITE;
/*!40000 ALTER TABLE `AUTHOR` DISABLE KEYS */;

INSERT INTO `AUTHOR` (`name`, `id`, `paperId`)
VALUES
	('Ashish Vaswani',1,'1706.03762'),
	('Noam Shazeer',2,'1706.03762'),
	('Niki Parmar',3,'1706.03762'),
	('Jakob Uszkoreit',4,'1706.03762'),
	('Llion Jones',5,'1706.03762'),
	('Aidan N. Gomez',6,'1706.03762'),
	('Łukasz Kaiser',7,'1706.03762'),
	('Illia Polosukhin',8,'1706.03762');

/*!40000 ALTER TABLE `AUTHOR` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table KEYWORD
# ------------------------------------------------------------

DROP TABLE IF EXISTS `KEYWORD`;

CREATE TABLE `KEYWORD` (
  `username` varchar(20) NOT NULL DEFAULT '0',
  `keyword` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`username`),
  CONSTRAINT `FK_USER_TO_KEYWORD_1` FOREIGN KEY (`username`) REFERENCES `USER` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



# Dump of table LIKEPAPER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `LIKEPAPER`;

CREATE TABLE `LIKEPAPER` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `paperId` varchar(200) NOT NULL DEFAULT '',
  `isValid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_TO_QUERYCONTAINER_1` (`username`),
  KEY `FK_PAPER_TO_QUERYCONTAINER_1` (`paperId`),
  CONSTRAINT `FK_PAPER_TO_QUERYCONTAINER_1` FOREIGN KEY (`paperId`) REFERENCES `PAPER` (`paperId`),
  CONSTRAINT `FK_USER_TO_QUERYCONTAINER_1` FOREIGN KEY (`username`) REFERENCES `USER` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `LIKEPAPER` WRITE;
/*!40000 ALTER TABLE `LIKEPAPER` DISABLE KEYS */;

INSERT INTO `LIKEPAPER` (`id`, `username`, `paperId`, `isValid`)
VALUES
	(1,'testtest1','1706.03762',1),
	(2,'testtest2','1706.03762',1),
	(4,'testtest1','1909.13474',1),
	(5,'testtest1','2010.01369',1),
	(6,'testtest1','2203.12114',1);

/*!40000 ALTER TABLE `LIKEPAPER` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table PAPER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAPER`;

CREATE TABLE `PAPER` (
  `paperId` varchar(200) NOT NULL DEFAULT '',
  `title` varchar(200) DEFAULT NULL,
  `conference` varchar(200) DEFAULT NULL,
  `cites` int(11) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `summary` varchar(2000) DEFAULT NULL,
  `likes` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  PRIMARY KEY (`paperId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `PAPER` WRITE;
/*!40000 ALTER TABLE `PAPER` DISABLE KEYS */;

INSERT INTO `PAPER` (`paperId`, `title`, `conference`, `cites`, `url`, `summary`, `likes`, `year`)
VALUES
	('1404.5997','Default title','Default confernece',3,'http://arxiv.org/abs/1404.5997','Default Abastract',3,2023),
	('1706.03762','Attention is all you need','2017 - proceedings.neurips.cc',81860,'https://arxiv.org/abs/1706.03762','Default Abastract',202,2017),
	('1707.02725','Default title','Default confernece',3,'http://arxiv.org/abs/1707.02725','Default Abastract',3,2023),
	('1903.08131','Default title','Default confernece',3,'http://arxiv.org/abs/1903.08131','Default Abastract',3,2023),
	('1909.02765','Default title','Default confernece',3,'http://arxiv.org/abs/1909.02765','Default Abastract',3,2023),
	('1909.13474','Default title','Default confernece',3,'http://arxiv.org/abs/1909.13474','Default Abastract',3,2023),
	('2001.09608','Default title','Default confernece',3,'http://arxiv.org/abs/2001.09608','Default Abastract',3,2023),
	('2009.07888','Default title','Default confernece',3,'http://arxiv.org/abs/2009.07888','Default Abastract',3,2023),
	('2010.01369','Default title','Default confernece',3,'http://arxiv.org/abs/2010.01369','Default Abastract',10,2023),
	('2105.10559','Default title','Default confernece',3,'http://arxiv.org/abs/2105.10559','Default Abastract',3,2023),
	('2108.03258','Default title','Default confernece',3,'http://arxiv.org/abs/2108.03258','Default Abastract',3,2023),
	('2108.11510','Default title','Default confernece',3,'http://arxiv.org/abs/2108.11510','Default Abastract',3,2023),
	('2111.00977','Default title','Default confernece',3,'http://arxiv.org/abs/2111.00977','Default Abastract',3,2023),
	('2202.05135','Default title','Default confernece',3,'http://arxiv.org/abs/2202.05135','Default Abastract',3,2023),
	('2203.12114','Default title','Default confernece',3,'http://arxiv.org/abs/2203.12114','Default Abastract',4,2023),
	('2204.05437','Default title','Default confernece',3,'http://arxiv.org/abs/2204.05437','Default Abastract',3,2023),
	('2204.11897','Default title','Default confernece',3,'http://arxiv.org/abs/2204.11897','Default Abastract',3,2023),
	('2204.13154','Attention Mechanism in Neural Networks: Where it Comes and Where it Goes','2021 - proceedings.mlr.press',170,'https://arxiv.org/abs/2204.13154','A long time ago in the machine learning literature, the idea of incorporating a mechanism inspired by the human visual system into neural networks was introduced. This idea is named the attention mechanism, and it has gone through a long development period. Today, many works have been devoted to this idea in a variety of tasks. Remarkable performance has recently been demonstrated. The goal of this paper is to provide an overview from the early work on searching for ways to implement attention idea with neural networks until the recent trends. This review emphasizes the important milestones during this progress regarding different tasks. By this way, this study aims to provide a road map for researchers to explore the current development and get inspired for novel approaches beyond the attention',12,2022),
	('2212.00253','Default title','Default confernece',3,'http://arxiv.org/abs/2212.00253','Default Abastract',3,2023),
	('2212.09507','Default title','Default confernece',3,'http://arxiv.org/abs/2212.09507','Default Abastract',3,2023),
	('2307.00865','Default title','Default confernece',3,'http://arxiv.org/abs/2307.00865','Default Abastract',10,2023),
	('2307.01452','Default title','Default confernece',3,'http://arxiv.org/abs/2307.01452','Default Abastract',3,2023);

/*!40000 ALTER TABLE `PAPER` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table PAPERBUFFER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAPERBUFFER`;

CREATE TABLE `PAPERBUFFER` (
  `id` varchar(200) NOT NULL,
  `ishit` tinyint(1) DEFAULT 0,
  `uddate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK_PAPER_TO_PAPERBUFFER_1` FOREIGN KEY (`id`) REFERENCES `PAPER` (`paperId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `PAPERBUFFER` WRITE;
/*!40000 ALTER TABLE `PAPERBUFFER` DISABLE KEYS */;

INSERT INTO `PAPERBUFFER` (`id`, `ishit`, `uddate`)
VALUES
	('1706.03762',1,'2023-07-18'),
	('2204.13154',0,NULL);

/*!40000 ALTER TABLE `PAPERBUFFER` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table PAPERINFO
# ------------------------------------------------------------

DROP TABLE IF EXISTS `PAPERINFO`;

CREATE TABLE `PAPERINFO` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `paperid` varchar(200) NOT NULL,
  `infotype` varchar(200) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_PAPER_TO_PAPERINFO_1` (`paperid`),
  CONSTRAINT `FK_PAPER_TO_PAPERINFO_1` FOREIGN KEY (`paperid`) REFERENCES `PAPER` (`paperId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `PAPERINFO` WRITE;
/*!40000 ALTER TABLE `PAPERINFO` DISABLE KEYS */;

INSERT INTO `PAPERINFO` (`id`, `paperid`, `infotype`, `content`)
VALUES
	(1,'1706.03762','insight','The Transformer architecture relies exclusively on attention mechanisms and eliminates the need for recurrent or convolutional layers, resulting in improved performance, better parallelizability, and reduced training time.'),
	(2,'1706.03762','insight','Experimental results on machine translation tasks demonstrate that the Transformer model achieves superior translation quality compared to existing models, surpassing previous state-of-the-art results.'),
	(3,'1706.03762','insight','The Transformer model achieves a BLEU score of 28.4 on the WMT 2014 English-to-German translation task, surpassing existing results by more than 2 BLEU points.'),
	(4,'1706.03762','insight','For the WMT 2014 English-to-French translation task, the Transformer model sets a new single-model state-of-the-art BLEU score of 41.8 after training for 3.5 days on eight GPUs, significantly reducing training costs compared to previous approaches.'),
	(5,'1706.03762','insight','The generalizability of the Transformer model is demonstrated by successfully applying it to English constituency parsing tasks, showcasing its effectiveness with both large and limited training data.'),
	(6,'1706.03762','question','What motivated you to explore a network architecture solely based on attention mechanisms for sequence transduction tasks?'),
	(7,'1706.03762','question','Could you elaborate on the specific advantages of the Transformer model in terms of parallelizability and reduced training time?'),
	(8,'1706.03762','question','How does the Transformer model handle long-range dependencies in sequences compared to recurrent or convolutional neural networks?'),
	(9,'1706.03762','question','Have you experimented with the Transformer architecture on tasks other than machine translation and constituency parsing? If so, what were the results?'),
	(10,'1706.03762','question','What are the potential limitations or challenges of the Transformer model that future research should address?'),
	(11,'1706.03762','subjectrecommend','Investigate the performance of the Transformer model on other natural language processing tasks such as text summarization, question answering, and sentiment analysis.'),
	(12,'1706.03762','subjectrecommend','Explore techniques to enhance the interpretability of attention mechanisms in the Transformer architecture, enabling better understanding of the model\'s decision-making process.'),
	(13,'1706.03762','subjectrecommend','Extend the Transformer model to handle multimodal tasks that involve both textual and visual input, such as image captioning or video understanding.'),
	(14,'1706.03762','subjectrecommend','Investigate methods to adapt the Transformer architecture for low-resource languages, where training data may be limited, and explore techniques for transfer learning across languages.'),
	(15,'1706.03762','subjectrecommend','Study techniques for incorporating domain-specific knowledge or external memory mechanisms into the Transformer model to improve performance on domain-specific tasks.'),
	(16,'1706.03762','reference','Vaswani, A., Shazeer, N., Parmar, N., Uszkoreit, J., Jones, L., Gomez, A. N., Kaiser, L., & Polosukhin, I. (2017). Attention Is All You Need. In Advances in Neural Information Processing Systems (pp. 5998-6008). [Link: https://proceedings.neurips.cc/paper/2017/file/3f5ee243547dee91fbd053c1c4a845aa-Paper.pdf]'),
	(17,'1706.03762','reference','Bahdanau, D., Cho, K., & Bengio, Y. (2014). Neural Machine Translation by Jointly Learning to Align and Translate. arXiv preprint arXiv:1409.0473.'),
	(18,'1706.03762','reference','Gehring, J., Auli, M., Grangier, D., & Dauphin, Y. N. (2017). Convolutional Sequence to Sequence Learning. In Proceedings of the 34th International Conference on Machine Learning (Vol. 70, pp. 1243-1252). [Link: https://proceedings.icml.cc/static/paper_files/papers/P3229.pdf]'),
	(19,'1706.03762','reference','Sutskever, I., Vinyals, O., & Le, Q. V. (2014). Sequence to Sequence Learning with Neural Networks. In Advances in Neural Information Processing Systems (pp. 3104-3112). [Link: https://proceedings.neurips.cc/paper/2014/file/a14ac55a4f27472c5d894ec1c3c743d2-Paper.pdf]'),
	(20,'1706.03762','reference','Gehring, J., Auli, M., Grangier, D., & Dauphin, Y. N. (2017). Convolutional Sequence to Sequence Learning. In Proceedings of the 34th International Conference on Machine Learning (Vol. 70, pp. 1243-1252). [Link: https://proceedings.icml.cc/static/paper_files/papers/P3229.pdf]'),
	(21,'1706.03762','reference','Liu, Y., Wei, F., & Zhou, M. (2018). Empirical Evaluation of Gated Recurrent Neural Networks on Sequence Modeling. Neural Computing and Applications, 29(10), 1361-1375.✏');

/*!40000 ALTER TABLE `PAPERINFO` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table QUERYHISTORY
# ------------------------------------------------------------

DROP TABLE IF EXISTS `QUERYHISTORY`;

CREATE TABLE `QUERYHISTORY` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `paperid` varchar(200) NOT NULL,
  `idx` int(11) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `who` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USER_TO_QUERYHISTORY_1` (`username`),
  KEY `FK_PAPER_TO_QUERYHISTORY_1` (`paperid`),
  CONSTRAINT `FK_PAPER_TO_QUERYHISTORY_1` FOREIGN KEY (`paperid`) REFERENCES `PAPER` (`paperId`),
  CONSTRAINT `FK_USER_TO_QUERYHISTORY_1` FOREIGN KEY (`username`) REFERENCES `USER` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `QUERYHISTORY` WRITE;
/*!40000 ALTER TABLE `QUERYHISTORY` DISABLE KEYS */;

INSERT INTO `QUERYHISTORY` (`id`, `username`, `paperid`, `idx`, `content`, `who`)
VALUES
	(1,'testtest1','1706.03762',1,'what is transformer in RNN? Tell me under 10000 characters',1),
	(2,'testtest1','1706.03762',2,'The Transformer is not a part of Recurrent Neural Networks (RNNs). It is a standalone model architecture that relies on self-attention mechanisms to process sequences in parallel. Unlike RNNs, the Transformer does not have sequential dependencies, enabling efficient parallel computation and capturing long-range dependencies. It is widely used in natural language processing tasks, such as machine translation, due to its ability to model context effectively and handle variable-length sequences.',0),
	(6,'testtest1','1706.03762',3,'hi',1),
	(7,'testtest1','1706.03762',4,'\"bi\"',0),
	(8,'testtest1','1706.03762',5,'hi',1),
	(9,'testtest1','1706.03762',6,'\"bi\"',0),
	(10,'testtest1','1706.03762',7,'hihi',1),
	(11,'testtest1','1706.03762',8,'hihi',1),
	(13,'testtest1','1706.03762',9,'hihi',1),
	(14,'testtest1','1706.03762',10,'\"bibi\"',0),
	(15,'testtest1','1706.03762',11,'hihi',1),
	(16,'testtest1','1706.03762',12,'\"bibi\"',0),
	(17,'testtest1','1706.03762',13,'hihi',1),
	(18,'testtest1','1706.03762',14,'bibi',0),
	(19,'testtest1','1706.03762',15,'hihi',1),
	(20,'testtest1','1706.03762',16,'bibi',0);

/*!40000 ALTER TABLE `QUERYHISTORY` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table STUDYFIELD
# ------------------------------------------------------------

DROP TABLE IF EXISTS `STUDYFIELD`;

CREATE TABLE `STUDYFIELD` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `studyfield` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `STUDYFIELD` WRITE;
/*!40000 ALTER TABLE `STUDYFIELD` DISABLE KEYS */;

INSERT INTO `STUDYFIELD` (`id`, `studyfield`)
VALUES
	(1,'RNN'),
	(2,'CNN'),
	(3,'AI'),
	(4,'Chemistry');

/*!40000 ALTER TABLE `STUDYFIELD` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table USER
# ------------------------------------------------------------

DROP TABLE IF EXISTS `USER`;

CREATE TABLE `USER` (
  `username` varchar(200) NOT NULL DEFAULT '',
  `password` varchar(50) DEFAULT NULL,
  `isfirst` tinyint(1) DEFAULT 1,
  `studyField` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;

INSERT INTO `USER` (`username`, `password`, `isfirst`, `studyField`)
VALUES
	('testtest1','testtest',1,NULL),
	('testtest2','testtest',0,'RNN'),
	('testtest3','testtest',1,NULL),
	('testtest4','testtest',1,NULL);

/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
