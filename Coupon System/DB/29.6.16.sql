-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: coupon
-- ------------------------------------------------------
-- Server version	5.7.12-log

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
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `Comp_ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `Comp_name` varchar(30) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Comp_ID`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `comp_name` (`Comp_name`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (4,'Samsung','050','sam@gmail.com'),(5,'Playstation','12345','playstation@mail.com'),(7,'Sandisk','1234','sandisk'),(8,'Waze','1234','wa@gmail.com'),(9,'Java','1234','java@gmail.com'),(10,'ubisoft','1234','ubi@gmail.com'),(11,'Edios','1234','edi@gmail.com'),(12,'isracard','1234','isra@gmail.com'),(13,'MasterCard','1234','mas@gmail.com'),(14,'Visa','1234','visa@gmail.com'),(15,'Activition','1234','avc@gmail.com'),(16,'HBO','1234','hbo@gmail.com'),(17,'SQL','1234','sql@gmail.com'),(18,'Acrobat','1234','acro@gmail.com'),(19,'HP','1234','hp@gmail.com'),(20,'Apple','1234','apple@gmail.com'),(21,'Armada','1234','armada@gmail.com'),(22,'winrar','1234','win@gmail.com');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_coupon`
--

DROP TABLE IF EXISTS `company_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_coupon` (
  `Comp_ID` decimal(10,0) NOT NULL,
  `Coup_ID` decimal(10,0) NOT NULL,
  PRIMARY KEY (`Comp_ID`,`Coup_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_coupon`
--

LOCK TABLES `company_coupon` WRITE;
/*!40000 ALTER TABLE `company_coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `company_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `Coup_id` bigint(255) NOT NULL AUTO_INCREMENT,
  `Title` varchar(20) NOT NULL,
  `Start_Date` date NOT NULL,
  `End_Date` date NOT NULL,
  `Amount` int(11) NOT NULL,
  `Category` enum('Travel','Electronics','Fashion','Motors','Music') DEFAULT NULL,
  `Message` varchar(50) DEFAULT NULL,
  `Price` double DEFAULT NULL,
  `Image` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`Coup_id`),
  UNIQUE KEY `Coup_id` (`Coup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,'Muses','2011-01-01','2020-01-01',20,'Travel','Test!',20,'link'),(3,'Asus','2016-06-25','2018-06-25',2,'Travel','Nice ultraBook',200,'no link'),(4,'Groupon','2011-01-01','2020-01-01',2,'Travel','Prauge',200,'lo'),(5,'Apple','2011-01-01','2020-01-01',2,'Travel','Iphones',200,'no'),(6,'Samsung','2011-01-01','2020-01-01',2,'Electronics','new Note 3',200,'no link'),(7,'Hilton-Room','2016-01-01','2017-01-01',1,'Travel','one room per year',2000,'no link');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `cust_id` int(20) NOT NULL AUTO_INCREMENT,
  `cust_name` varchar(30) NOT NULL,
  `password` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`cust_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (2,'Juses','123'),(4,'Ron Wisly','1234'),(5,'Lior vanstain','12345'),(6,'Muses Rodrigo','1234');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_coupon`
--

DROP TABLE IF EXISTS `customer_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_coupon` (
  `Cust_ID` decimal(10,0) NOT NULL,
  `Coup_ID` decimal(10,0) NOT NULL,
  PRIMARY KEY (`Cust_ID`,`Coup_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_coupon`
--

LOCK TABLES `customer_coupon` WRITE;
/*!40000 ALTER TABLE `customer_coupon` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer_coupon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-29 16:30:38
