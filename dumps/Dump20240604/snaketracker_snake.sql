CREATE DATABASE  IF NOT EXISTS `snaketracker` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `snaketracker`;
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
  `LastWeightID` int DEFAULT NULL,
  `LastMealID` int DEFAULT NULL,
  `LastPoopID` int DEFAULT NULL,
  `Morph` varchar(45) DEFAULT NULL,
  `ImagePath` varchar(350) DEFAULT NULL,
  PRIMARY KEY (`SnakeID`),
  KEY `MealID_idx` (`LastMealID`),
  KEY `PoopID_idx` (`LastPoopID`),
  KEY `WeightID_idx` (`LastWeightID`),
  KEY `TypeID_idx` (`Type`),
  CONSTRAINT `MealID` FOREIGN KEY (`LastMealID`) REFERENCES `mealentry` (`MealID`),
  CONSTRAINT `PoopID` FOREIGN KEY (`LastPoopID`) REFERENCES `poopentry` (`PoopID`),
  CONSTRAINT `TypeID` FOREIGN KEY (`Type`) REFERENCES `snaketypes` (`TypeID`),
  CONSTRAINT `WeightID` FOREIGN KEY (`LastWeightID`) REFERENCES `weightentry` (`WeightID`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `snake`
--

LOCK TABLES `snake` WRITE;
/*!40000 ALTER TABLE `snake` DISABLE KEYS */;
INSERT INTO `snake` VALUES (15,1,'Olaf',1,'2023-12-05 00:00:00',NULL,NULL,NULL,'Mpjave','C:\\Users\\User\\Documents\\GitHub\\SnakeTraker_FinalProject\\SnakeTracker\\savedImages\\emote31.png');
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

-- Dump completed on 2024-06-04 16:40:46
