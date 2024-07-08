-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: snaketracker
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `snake`
--

DROP TABLE IF EXISTS `snake`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `snake` (
  `SnakeID` int NOT NULL AUTO_INCREMENT,
  `Type` int NOT NULL,
  `Name` varchar(30) DEFAULT NULL,
  `Sex` tinyint NOT NULL,
  `BirthDate` datetime NOT NULL,
  `Morph` varchar(45) DEFAULT NULL,
  `ImagePath` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`SnakeID`),
  KEY `TypeID_idx` (`Type`),
  CONSTRAINT `TypeID` FOREIGN KEY (`Type`) REFERENCES `snaketypes` (`TypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snake`
--

LOCK TABLES `snake` WRITE;
/*!40000 ALTER TABLE `snake` DISABLE KEYS */;
INSERT INTO `snake` VALUES (30,3,'Gustav',1,'2023-12-13 00:00:00','Albino Motley','/savedImages/snakes/boa_albino_motley_labyrinth.jpeg'),(32,1,'Bubu',0,'2024-03-01 00:00:00','Candy Pied','/savedImages/snakes/ball_candypied.jpeg'),(34,4,'Baumi',1,'2023-11-02 00:00:00','Biak','/savedImages/snakes/baum_biak.jpeg'),(35,5,'Longboi',0,'2020-04-20 00:00:00','Pied','/savedImages/snakes/tiger_pied.jpeg'),(36,2,'Glubschi',1,'2024-03-23 00:00:00','Palmetto','/savedImages/snakes/korn_palmetto.jpeg');
/*!40000 ALTER TABLE `snake` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-09  0:11:49
