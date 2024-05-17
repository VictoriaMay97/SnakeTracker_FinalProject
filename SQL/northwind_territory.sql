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
-- Table structure for table `territory`
--

DROP TABLE IF EXISTS `territory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `territory` (
  `territoryId` varchar(20) COLLATE utf8mb3_bin NOT NULL,
  `territorydescription` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `regionId` int NOT NULL,
  PRIMARY KEY (`territoryId`),
  KEY `regionId` (`regionId`),
  CONSTRAINT `territory_ibfk_1` FOREIGN KEY (`regionId`) REFERENCES `region` (`regionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `territory`
--

LOCK TABLES `territory` WRITE;
/*!40000 ALTER TABLE `territory` DISABLE KEYS */;
INSERT INTO `territory` VALUES ('01581','Westboro',1),('01730','Bedford',1),('01833','Georgetow',1),('02116','Boston',1),('02139','Cambridge',1),('02184','Braintree',1),('02903','Providence',1),('03049','Hollis',3),('03801','Portsmouth',3),('06897','Wilton',1),('07960','Morristown',1),('08837','Edison',1),('10019','New York',1),('10038','New York',1),('11747','Mellvile',1),('14450','Fairport',1),('19428','Philadelphia',3),('19713','Neward',1),('20852','Rockville',1),('27403','Greensboro',1),('27511','Cary',1),('29202','Columbia',4),('30346','Atlanta',4),('31406','Savannah',4),('32859','Orlando',4),('33607','Tampa',4),('40222','Louisville',1),('44122','Beachwood',3),('45839','Findlay',3),('48075','Southfield',3),('48084','Troy',3),('48304','Bloomfield Hills',3),('53404','Racine',3),('55113','Roseville',3),('55439','Minneapolis',3),('60179','Hoffman Estates',2),('60601','Chicago',2),('72716','Bentonville',4),('75234','Dallas',4),('78759','Austin',4),('80202','Denver',2),('80909','Colorado Springs',2),('85014','Phoenix',2),('85251','Scottsdale',2),('90405','Santa Monica',2),('94025','Menlo Park',2),('94105','San Francisco',2),('95008','Campbell',2),('95054','Santa Clara',2),('95060','Santa Cruz',2),('98004','Bellevue',2),('98052','Redmond',2),('98104','Seattle',2);
/*!40000 ALTER TABLE `territory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-17 18:59:41
