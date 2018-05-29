-- MySQL dump 10.13  Distrib 5.7.20, for Win64 (x86_64)
--
-- Host: localhost    Database: dbfoodmaster
-- ------------------------------------------------------
-- Server version	5.7.20-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `должность`
--

DROP TABLE IF EXISTS `должность`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `должность` (
  `ИДДолжность` int(11) NOT NULL AUTO_INCREMENT,
  `Должность` varchar(256) NOT NULL,
  PRIMARY KEY (`ИДДолжность`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `должность`
--

LOCK TABLES `должность` WRITE;
/*!40000 ALTER TABLE `должность` DISABLE KEYS */;
/*!40000 ALTER TABLE `должность` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `единицаизмерения`
--

DROP TABLE IF EXISTS `единицаизмерения`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `единицаизмерения` (
  `ИДЕдиницаИзмерения` int(11) NOT NULL AUTO_INCREMENT,
  `ЕдиницаИзмерения` varchar(45) NOT NULL,
  `Обозначение` varchar(45) NOT NULL,
  PRIMARY KEY (`ИДЕдиницаИзмерения`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `единицаизмерения`
--

LOCK TABLES `единицаизмерения` WRITE;
/*!40000 ALTER TABLE `единицаизмерения` DISABLE KEYS */;
INSERT INTO `единицаизмерения` VALUES (1,'кг','кг');
/*!40000 ALTER TABLE `единицаизмерения` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `заказ`
--

DROP TABLE IF EXISTS `заказ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `заказ` (
  `ИДЗаказ` int(11) NOT NULL AUTO_INCREMENT,
  `ИДКлиент` int(11) NOT NULL,
  `ДатаЗаказа` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Сумма` decimal(10,2) NOT NULL DEFAULT '0.00',
  `ИДИсполнитель` int(11) DEFAULT NULL,
  `ДатаИсполнения` datetime DEFAULT NULL,
  `Выполнен` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ИДЗаказ`),
  KEY `fk_Заказ_Клиент1_idx` (`ИДКлиент`),
  KEY `fk_Заказ_Сотрудник2_idx` (`ИДИсполнитель`),
  CONSTRAINT `fk_Заказ_Клиент1` FOREIGN KEY (`ИДКлиент`) REFERENCES `клиент` (`ИДКлиент`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Заказ_Сотрудник2` FOREIGN KEY (`ИДИсполнитель`) REFERENCES `сотрудник` (`ИДСотрудник`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `заказ`
--

LOCK TABLES `заказ` WRITE;
/*!40000 ALTER TABLE `заказ` DISABLE KEYS */;
INSERT INTO `заказ` VALUES (12,1,'2018-05-29 18:15:22',500.00,NULL,NULL,0);
/*!40000 ALTER TABLE `заказ` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `категория`
--

DROP TABLE IF EXISTS `категория`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `категория` (
  `ИДКатегория` int(11) NOT NULL AUTO_INCREMENT,
  `Категория` varchar(45) NOT NULL,
  `Примечание` longtext,
  PRIMARY KEY (`ИДКатегория`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `категория`
--

LOCK TABLES `категория` WRITE;
/*!40000 ALTER TABLE `категория` DISABLE KEYS */;
INSERT INTO `категория` VALUES (1,'Food Master',''),(2,'Био-С имун+',NULL),(3,'Дольче',NULL),(4,'Lactel','Примечание Lactel'),(5,'President',NULL),(6,'Локо Моко',NULL),(7,'Galbani',NULL);
/*!40000 ALTER TABLE `категория` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `клиент`
--

DROP TABLE IF EXISTS `клиент`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `клиент` (
  `ИДКлиент` int(11) NOT NULL AUTO_INCREMENT,
  `ФИОКлиент` varchar(64) NOT NULL,
  `ДатаРождения` datetime NOT NULL,
  `Почта` varchar(45) DEFAULT NULL,
  `Телефон` varchar(45) NOT NULL,
  `Примечание` longtext,
  `Логин` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`ИДКлиент`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `клиент`
--

LOCK TABLES `клиент` WRITE;
/*!40000 ALTER TABLE `клиент` DISABLE KEYS */;
INSERT INTO `клиент` VALUES (1,'Дана Жанкушикова','1990-11-11 00:00:00','dana@mail.ru','87773334422',NULL,'Dana'),(2,'Николай Николаев','1991-12-12 00:00:00','nikolo@gmail.com','87772221111',NULL,NULL);
/*!40000 ALTER TABLE `клиент` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `корзина`
--

DROP TABLE IF EXISTS `корзина`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `корзина` (
  `ИДКорзина` int(11) NOT NULL AUTO_INCREMENT,
  `ИДЗаказ` int(11) NOT NULL,
  `ИДПродукт` int(11) NOT NULL,
  `Стоимость` decimal(10,2) NOT NULL,
  `Количество` float NOT NULL,
  PRIMARY KEY (`ИДКорзина`),
  KEY `fk_Корзина_Продукт1_idx` (`ИДПродукт`),
  KEY `fk_Корзина_Заказ1_idx` (`ИДЗаказ`),
  CONSTRAINT `fk_Корзина_Заказ1` FOREIGN KEY (`ИДЗаказ`) REFERENCES `заказ` (`ИДЗаказ`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Корзина_Продукт1` FOREIGN KEY (`ИДПродукт`) REFERENCES `продукт` (`ИДПродукт`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `корзина`
--

LOCK TABLES `корзина` WRITE;
/*!40000 ALTER TABLE `корзина` DISABLE KEYS */;
INSERT INTO `корзина` VALUES (7,12,8,100.00,2),(8,12,9,100.00,3);
/*!40000 ALTER TABLE `корзина` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `назначение`
--

DROP TABLE IF EXISTS `назначение`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `назначение` (
  `ИДНазначение` int(11) NOT NULL AUTO_INCREMENT,
  `ИДПродукт` int(11) DEFAULT NULL,
  `ИДКатегория` int(11) DEFAULT NULL,
  `ИДСкидка` int(11) NOT NULL,
  PRIMARY KEY (`ИДНазначение`),
  KEY `fk_Назначение_Скидка1_idx` (`ИДСкидка`),
  KEY `fk_Назначение_Продукт1_idx` (`ИДПродукт`),
  KEY `fk_Назначение_Категория1_idx` (`ИДКатегория`),
  CONSTRAINT `fk_Назначение_Категория1` FOREIGN KEY (`ИДКатегория`) REFERENCES `категория` (`ИДКатегория`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Назначение_Продукт1` FOREIGN KEY (`ИДПродукт`) REFERENCES `продукт` (`ИДПродукт`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Назначение_Скидка1` FOREIGN KEY (`ИДСкидка`) REFERENCES `скидка` (`ИДСкидка`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `назначение`
--

LOCK TABLES `назначение` WRITE;
/*!40000 ALTER TABLE `назначение` DISABLE KEYS */;
/*!40000 ALTER TABLE `назначение` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `новость`
--

DROP TABLE IF EXISTS `новость`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `новость` (
  `ИДНовость` int(11) NOT NULL AUTO_INCREMENT,
  `Заголовок` varchar(256) NOT NULL,
  `Дата` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Текст` longtext NOT NULL,
  `Логин` varchar(64) NOT NULL,
  PRIMARY KEY (`ИДНовость`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `новость`
--

LOCK TABLES `новость` WRITE;
/*!40000 ALTER TABLE `новость` DISABLE KEYS */;
/*!40000 ALTER TABLE `новость` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `пользователь`
--

DROP TABLE IF EXISTS `пользователь`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `пользователь` (
  `Логин` varchar(64) NOT NULL,
  `Пароль` varchar(512) NOT NULL,
  PRIMARY KEY (`Логин`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `пользователь`
--

LOCK TABLES `пользователь` WRITE;
/*!40000 ALTER TABLE `пользователь` DISABLE KEYS */;
INSERT INTO `пользователь` VALUES ('Dana','danak'),('Nikolo','123');
/*!40000 ALTER TABLE `пользователь` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `приход`
--

DROP TABLE IF EXISTS `приход`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `приход` (
  `ИДПриход` int(11) NOT NULL AUTO_INCREMENT,
  `ИДПродукт` int(11) NOT NULL,
  `Дата` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Количество` float NOT NULL,
  `Принял` varchar(64) DEFAULT NULL,
  `Примечание` longtext,
  PRIMARY KEY (`ИДПриход`),
  KEY `fk_Приход_Продукт1_idx` (`ИДПродукт`),
  CONSTRAINT `fk_Приход_Продукт1` FOREIGN KEY (`ИДПродукт`) REFERENCES `продукт` (`ИДПродукт`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `приход`
--

LOCK TABLES `приход` WRITE;
/*!40000 ALTER TABLE `приход` DISABLE KEYS */;
/*!40000 ALTER TABLE `приход` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `продукт`
--

DROP TABLE IF EXISTS `продукт`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `продукт` (
  `ИДПродукт` int(11) NOT NULL AUTO_INCREMENT,
  `Продукт` varchar(256) NOT NULL,
  `ИДЕдиницаИзмерения` int(11) NOT NULL,
  `ИДКатегория` int(11) NOT NULL,
  `Количество` float NOT NULL DEFAULT '0',
  `Стоимость` decimal(10,2) NOT NULL,
  `Примечание` longtext,
  PRIMARY KEY (`ИДПродукт`),
  KEY `fk_Продукт_Категория1_idx` (`ИДКатегория`),
  KEY `fk_Продукт_ЕдиницаИзмерения1_idx` (`ИДЕдиницаИзмерения`),
  CONSTRAINT `fk_Продукт_ЕдиницаИзмерения1` FOREIGN KEY (`ИДЕдиницаИзмерения`) REFERENCES `единицаизмерения` (`ИДЕдиницаИзмерения`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Продукт_Категория1` FOREIGN KEY (`ИДКатегория`) REFERENCES `категория` (`ИДКатегория`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `продукт`
--

LOCK TABLES `продукт` WRITE;
/*!40000 ALTER TABLE `продукт` DISABLE KEYS */;
INSERT INTO `продукт` VALUES (8,'Простокваша',1,2,100,100.00,''),(9,'Биойогурт',1,2,100,100.00,NULL);
/*!40000 ALTER TABLE `продукт` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `роль`
--

DROP TABLE IF EXISTS `роль`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `роль` (
  `Логин` varchar(64) NOT NULL,
  `Роль` varchar(64) NOT NULL,
  PRIMARY KEY (`Роль`,`Логин`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `роль`
--

LOCK TABLES `роль` WRITE;
/*!40000 ALTER TABLE `роль` DISABLE KEYS */;
INSERT INTO `роль` VALUES ('Dana','admin'),('Nikolo','guest');
/*!40000 ALTER TABLE `роль` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `скидка`
--

DROP TABLE IF EXISTS `скидка`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `скидка` (
  `ИДСкидка` int(11) NOT NULL AUTO_INCREMENT,
  `Описание` longtext,
  `Размер` float NOT NULL DEFAULT '0',
  `Начало` datetime NOT NULL,
  `Окончание` datetime NOT NULL,
  PRIMARY KEY (`ИДСкидка`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `скидка`
--

LOCK TABLES `скидка` WRITE;
/*!40000 ALTER TABLE `скидка` DISABLE KEYS */;
INSERT INTO `скидка` VALUES (1,'Тестовая скидка',5,'2018-05-01 00:00:00','2018-05-31 00:00:00');
/*!40000 ALTER TABLE `скидка` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `сообщение`
--

DROP TABLE IF EXISTS `сообщение`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `сообщение` (
  `ИДСообщение` int(11) NOT NULL AUTO_INCREMENT,
  `Логин` varchar(64) NOT NULL,
  `ИДТема` int(11) NOT NULL,
  `Дата` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Сообщение` longtext NOT NULL,
  PRIMARY KEY (`ИДСообщение`),
  KEY `fk_Сообщение_Тема2_idx` (`ИДТема`),
  CONSTRAINT `fk_Сообщение_Тема2` FOREIGN KEY (`ИДТема`) REFERENCES `тема` (`ИДТема`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `сообщение`
--

LOCK TABLES `сообщение` WRITE;
/*!40000 ALTER TABLE `сообщение` DISABLE KEYS */;
/*!40000 ALTER TABLE `сообщение` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `сотрудник`
--

DROP TABLE IF EXISTS `сотрудник`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `сотрудник` (
  `ИДСотрудник` int(11) NOT NULL AUTO_INCREMENT,
  `ИДДолжность` int(11) NOT NULL,
  `ФИОСотрудник` varchar(45) NOT NULL,
  `ДатаРождения` datetime NOT NULL,
  `Телефон` varchar(45) NOT NULL,
  `Примечание` longtext,
  PRIMARY KEY (`ИДСотрудник`),
  KEY `fk_Сотрудник_Должность1_idx` (`ИДДолжность`),
  CONSTRAINT `fk_Сотрудник_Должность1` FOREIGN KEY (`ИДДолжность`) REFERENCES `должность` (`ИДДолжность`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `сотрудник`
--

LOCK TABLES `сотрудник` WRITE;
/*!40000 ALTER TABLE `сотрудник` DISABLE KEYS */;
/*!40000 ALTER TABLE `сотрудник` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `тема`
--

DROP TABLE IF EXISTS `тема`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `тема` (
  `ИДТема` int(11) NOT NULL AUTO_INCREMENT,
  `Тема` varchar(256) NOT NULL,
  PRIMARY KEY (`ИДТема`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `тема`
--

LOCK TABLES `тема` WRITE;
/*!40000 ALTER TABLE `тема` DISABLE KEYS */;
/*!40000 ALTER TABLE `тема` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-05-29 18:21:29
