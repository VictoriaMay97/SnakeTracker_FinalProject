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
-- Table structure for table `supplier`
--

DROP TABLE IF EXISTS `supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supplier` (
  `supplierId` int NOT NULL AUTO_INCREMENT,
  `companyName` varchar(40) COLLATE utf8mb3_bin NOT NULL,
  `contactName` varchar(30) COLLATE utf8mb3_bin DEFAULT NULL,
  `contactTitle` varchar(30) COLLATE utf8mb3_bin DEFAULT NULL,
  `address` varchar(60) COLLATE utf8mb3_bin DEFAULT NULL,
  `city` varchar(15) COLLATE utf8mb3_bin DEFAULT NULL,
  `region` varchar(15) COLLATE utf8mb3_bin DEFAULT NULL,
  `postalCode` varchar(10) COLLATE utf8mb3_bin DEFAULT NULL,
  `country` varchar(15) COLLATE utf8mb3_bin DEFAULT NULL,
  `phone` varchar(24) COLLATE utf8mb3_bin DEFAULT NULL,
  `email` varchar(225) COLLATE utf8mb3_bin DEFAULT NULL,
  `fax` varchar(24) COLLATE utf8mb3_bin DEFAULT NULL,
  `HomePage` text COLLATE utf8mb3_bin,
  PRIMARY KEY (`supplierId`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supplier`
--

LOCK TABLES `supplier` WRITE;
/*!40000 ALTER TABLE `supplier` DISABLE KEYS */;
INSERT INTO `supplier` VALUES (1,'Supplier SWRXU','Adolphi, Stephan','Purchasing Manager','2345 Gilbert St.','London',NULL,'10023','UK','(171) 456-7890',NULL,NULL,NULL),(2,'Supplier VHQZD','Hance, Jim','Order Administrator','P.O. Box 5678','New Orleans','LA','10013','USA','(100) 555-0111',NULL,NULL,NULL),(3,'Supplier STUAZ','Parovszky, Alfons','Sales Representative','1234 Oxford Rd.','Ann Arbor','MI','10026','USA','(313) 555-0109',NULL,'(313) 555-0112',NULL),(4,'Supplier QOVFD','Balázs, Erzsébet','Marketing Manager','7890 Sekimai Musashino-shi','Tokyo',NULL,'10011','Japan','(03) 6789-0123',NULL,NULL,NULL),(5,'Supplier EQPNC','Holm, Michael','Export Administrator','Calle del Rosal 4567','Oviedo','Asturias','10029','Spain','(98) 123 45 67',NULL,NULL,NULL),(6,'Supplier QWUSF','Popkova, Darya','Marketing Representative','8901 Setsuko Chuo-ku','Osaka',NULL,'10028','Japan','(06) 789-0123',NULL,NULL,NULL),(7,'Supplier GQRCV','Ræbild, Jesper','Marketing Manager','5678 Rose St. Moonie Ponds','Melbourne','Victoria','10018','Australia','(03) 123-4567',NULL,'(03) 456-7890',NULL),(8,'Supplier BWGYE','Iallo, Lucio','Sales Representative','9012 King\'s Way','Manchester',NULL,'10021','UK','(161) 567-8901',NULL,NULL,NULL),(9,'Supplier QQYEU','Basalik, Evan','Sales Agent','Kaloadagatan 4567','Göteborg',NULL,'10022','Sweden','031-345 67 89',NULL,'031-678 90 12',NULL),(10,'Supplier UNAHG','Barnett, Dave','Marketing Manager','Av. das Americanas 2345','Sao Paulo',NULL,'10034','Brazil','(11) 345 6789',NULL,NULL,NULL),(11,'Supplier ZPYVS','Jain, Mukesh','Sales Manager','Tiergartenstraße 3456','Berlin',NULL,'10016','Germany','(010) 3456789',NULL,NULL,NULL),(12,'Supplier SVIYA','Regev, Barak','International Marketing Mgr.','Bogenallee 9012','Frankfurt',NULL,'10024','Germany','(069) 234567',NULL,NULL,NULL),(13,'Supplier TEGSC','Brehm, Peter','Coordinator Foreign Markets','Frahmredder 3456','Cuxhaven',NULL,'10019','Germany','(04721) 1234',NULL,'(04721) 2345',NULL),(14,'Supplier KEREV','Keil, Kendall','Sales Representative','Viale Dante, 6789','Ravenna',NULL,'10015','Italy','(0544) 56789',NULL,'(0544) 34567',NULL),(15,'Supplier NZLIF','Sałas-Szlejter, Karolina','Marketing Manager','Hatlevegen 1234','Sandvika',NULL,'10025','Norway','(0)9-012345',NULL,NULL,NULL),(16,'Supplier UHZRG','Scholl, Thorsten','Regional Account Rep.','8901 - 8th Avenue Suite 210','Bend','OR','10035','USA','(503) 555-0108',NULL,NULL,NULL),(17,'Supplier QZGUF','Kleinerman, Christian','Sales Representative','Brovallavägen 0123','Stockholm',NULL,'10033','Sweden','08-234 56 78',NULL,NULL,NULL),(18,'Supplier LVJUA','Canel, Fabrice','Sales Manager','3456, Rue des Francs-Bourgeois','Paris',NULL,'10031','France','(1) 90.12.34.56',NULL,'(1) 01.23.45.67',NULL),(19,'Supplier JDNUG','Chapman, Greg','Wholesale Account Agent','Order Processing Dept. 7890 Paul Revere Blvd.','Boston','MA','10027','USA','(617) 555-0110',NULL,'(617) 555-0113',NULL),(20,'Supplier CIYNM','Köszegi, Emília','Owner','6789 Serangoon Loop, Suite #402','Singapore',NULL,'10037','Singapore','012-3456',NULL,NULL,NULL),(21,'Supplier XOXZA','Shakespear, Paul','Sales Manager','Lyngbysild Fiskebakken 9012','Lyngby',NULL,'10012','Denmark','67890123',NULL,'78901234',NULL),(22,'Supplier FNUXM','Skelly, Bonnie L.','Accounting Manager','Verkoop Rijnweg 8901','Zaandam',NULL,'10014','Netherlands','(12345) 8901',NULL,'(12345) 5678',NULL),(23,'Supplier ELCRN','LaMee, Brian','Product Manager','Valtakatu 1234','Lappeenranta',NULL,'10032','Finland','(953) 78901',NULL,NULL,NULL),(24,'Supplier JNNES','Clark, Molly','Sales Representative','6789 Prince Edward Parade Hunter\'s Hill','Sydney','NSW','10030','Australia','(02) 234-5678',NULL,'(02) 567-8901',NULL),(25,'Supplier ERVYZ','Sprenger, Christof','Marketing Manager','7890 Rue St. Laurent','Montréal','Québec','10017','Canada','(514) 456-7890',NULL,NULL,NULL),(26,'Supplier ZWZDM','Cunha, Gonçalo','Order Administrator','Via dei Gelsomini, 5678','Salerno',NULL,'10020','Italy','(089) 4567890',NULL,'(089) 4567890',NULL),(27,'Supplier ZRYDZ','Leoni, Alessandro','Sales Manager','4567, rue H. Voiron','Montceau',NULL,'10036','France','89.01.23.45',NULL,NULL,NULL),(28,'Supplier OAVQT','Teper, Jeff','Sales Representative','Bat. B 2345, rue des Alpes','Annecy',NULL,'10010','France','01.23.45.67',NULL,'89.01.23.45',NULL),(29,'Supplier OGLRK','Walters, Rob','Accounting Manager','0123 rue Chasseur','Ste-Hyacinthe','Québec','10009','Canada','(514) 567-890',NULL,'(514) 678-9012',NULL);
/*!40000 ALTER TABLE `supplier` ENABLE KEYS */;
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
