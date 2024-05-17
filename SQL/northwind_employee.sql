-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: northwind
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employeeId` int NOT NULL AUTO_INCREMENT,
  `lastname` varchar(20) COLLATE utf8mb3_bin NOT NULL,
  `firstname` varchar(10) COLLATE utf8mb3_bin NOT NULL,
  `title` varchar(30) COLLATE utf8mb3_bin DEFAULT NULL,
  `titleOfCourtesy` varchar(25) COLLATE utf8mb3_bin DEFAULT NULL,
  `birthDate` datetime DEFAULT NULL,
  `hireDate` datetime DEFAULT NULL,
  `address` varchar(60) COLLATE utf8mb3_bin DEFAULT NULL,
  `city` varchar(15) COLLATE utf8mb3_bin DEFAULT NULL,
  `region` varchar(15) COLLATE utf8mb3_bin DEFAULT NULL,
  `postalCode` varchar(10) COLLATE utf8mb3_bin DEFAULT NULL,
  `country` varchar(15) COLLATE utf8mb3_bin DEFAULT NULL,
  `phone` varchar(24) COLLATE utf8mb3_bin DEFAULT NULL,
  `extension` varchar(4) COLLATE utf8mb3_bin DEFAULT NULL,
  `mobile` varchar(24) COLLATE utf8mb3_bin DEFAULT NULL,
  `email` varchar(225) COLLATE utf8mb3_bin DEFAULT NULL,
  `photo` blob,
  `notes` blob,
  `mgrId` int DEFAULT NULL,
  `photoPath` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Davis','Sara','CEO','Ms.','1958-12-08 00:00:00','2002-05-01 00:00:00','7890 - 20th Ave. E., Apt. 2A','Seattle','WA','10003','USA','(206) 555-0101',NULL,NULL,NULL,NULL,NULL,NULL,NULL),(2,'Funk','Don','Vice President, Sales','Dr.','1962-02-19 00:00:00','2002-08-14 00:00:00','9012 W. Capital Way','Tacoma','WA','10001','USA','(206) 555-0100',NULL,NULL,NULL,NULL,NULL,1,NULL),(3,'Lew','Judy','Sales Manager','Ms.','1973-08-30 00:00:00','2002-04-01 00:00:00','2345 Moss Bay Blvd.','Kirkland','WA','10007','USA','(206) 555-0103',NULL,NULL,NULL,NULL,NULL,2,NULL),(4,'Peled','Yael','Sales Representative','Mrs.','1947-09-19 00:00:00','2003-05-03 00:00:00','5678 Old Redmond Rd.','Redmond','WA','10009','USA','(206) 555-0104',NULL,NULL,NULL,NULL,NULL,3,NULL),(5,'Buck','Sven','Sales Manager','Mr.','1965-03-04 00:00:00','2003-10-17 00:00:00','8901 Garrett Hill','London',NULL,'10004','UK','(71) 234-5678',NULL,NULL,NULL,NULL,NULL,2,NULL),(6,'Suurs','Paul','Sales Representative','Mr.','1973-07-02 00:00:00','2003-10-17 00:00:00','3456 Coventry House, Miner Rd.','London',NULL,'10005','UK','(71) 345-6789',NULL,NULL,NULL,NULL,NULL,5,NULL),(7,'King','Russell','Sales Representative','Mr.','1970-05-29 00:00:00','2004-01-02 00:00:00','6789 Edgeham Hollow, Winchester Way','London',NULL,'10002','UK','(71) 123-4567',NULL,NULL,NULL,NULL,NULL,5,NULL),(8,'Cameron','Maria','Sales Representative','Ms.','1968-01-09 00:00:00','2004-03-05 00:00:00','4567 - 11th Ave. N.E.','Seattle','WA','10006','USA','(206) 555-0102',NULL,NULL,NULL,NULL,NULL,3,NULL),(9,'Dolgopyatova','Zoya','Sales Representative','Ms.','1976-01-27 00:00:00','2004-11-15 00:00:00','1234 Houndstooth Rd.','London',NULL,'10008','UK','(71) 456-7890',NULL,NULL,NULL,NULL,NULL,5,NULL);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-17 18:59:40
