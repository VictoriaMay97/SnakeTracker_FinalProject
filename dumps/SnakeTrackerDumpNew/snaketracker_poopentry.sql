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
-- Table structure for table `poopentry`
--

DROP TABLE IF EXISTS `poopentry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poopentry` (
  `PoopID` int NOT NULL AUTO_INCREMENT,
  `Date` datetime NOT NULL,
  `SnakeID` int NOT NULL,
  `ImagePath` varchar(200) DEFAULT NULL,
  `Comment` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`PoopID`),
  UNIQUE KEY `PoopID_UNIQUE` (`PoopID`),
  KEY `SnakeID_idx` (`SnakeID`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poopentry`
--

LOCK TABLES `poopentry` WRITE;
/*!40000 ALTER TABLE `poopentry` DISABLE KEYS */;
INSERT INTO `poopentry` VALUES (2,'2024-06-11 00:00:00',32,'\\savedImages\\poop\\poop.png','sieht gut aus'),(3,'2024-05-27 00:00:00',32,NULL,'komische Konsistenz'),(4,'2024-05-13 00:00:00',32,NULL,'Kot halt'),(5,'2024-05-02 00:00:00',32,NULL,'stinkt nach faulen Eiern'),(6,'2024-04-24 00:00:00',32,NULL,'sehr braun'),(7,'2024-04-15 00:00:00',32,NULL,'eine wunderbare Wurst'),(8,'2024-05-27 00:00:00',30,NULL,'Kot halt'),(9,'2024-05-13 00:00:00',30,NULL,'sieht gut aus'),(10,'2024-05-02 00:00:00',30,NULL,'stinkt nach faulen Eiern'),(11,'2024-04-24 00:00:00',30,NULL,'komische Konsistenz'),(12,'2024-04-15 00:00:00',30,NULL,'Kot halt'),(13,'2024-05-27 00:00:00',34,NULL,'sieht gut aus'),(14,'2024-05-13 00:00:00',34,NULL,'stinkt nach faulen Eiern'),(15,'2024-05-02 00:00:00',34,NULL,'komische Konsistenz'),(16,'2024-04-24 00:00:00',34,NULL,'Kot halt'),(17,'2024-04-15 00:00:00',34,NULL,'sehr braun'),(18,'2024-05-27 00:00:00',35,NULL,'stinkt nach faulen Eiern'),(19,'2024-05-13 00:00:00',35,NULL,'Kot halt'),(20,'2024-05-02 00:00:00',35,NULL,'sieht gut aus'),(21,'2024-04-24 00:00:00',35,NULL,'sehr braun'),(22,'2024-04-15 00:00:00',35,NULL,'komische Konsistenz'),(23,'2024-05-27 00:00:00',36,NULL,'sieht gut aus'),(24,'2024-05-13 00:00:00',36,NULL,'sehr braun'),(25,'2024-05-02 00:00:00',36,NULL,'komische Konsistenz'),(26,'2024-04-24 00:00:00',36,NULL,'sehr braun'),(27,'2024-04-15 00:00:00',36,NULL,'sieht gut aus');
/*!40000 ALTER TABLE `poopentry` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-09  0:11:44
