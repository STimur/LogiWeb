-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: logiweb
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `Cargos`
--

DROP TABLE IF EXISTS `Cargos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cargos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `state` enum('READY','SHIPPED','DELIVERED') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cargos`
--

LOCK TABLES `Cargos` WRITE;
/*!40000 ALTER TABLE `Cargos` DISABLE KEYS */;
INSERT INTO `Cargos` VALUES (1,'potato',10,'READY'),(2,'cheese',5,'READY'),(3,'stone',8,'READY'),(4,'milk',15,'READY'),(5,'toyota',4,'READY'),(6,'cats',1,'READY');
/*!40000 ALTER TABLE `Cargos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Cities`
--

DROP TABLE IF EXISTS `Cities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Cities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cities`
--

LOCK TABLES `Cities` WRITE;
/*!40000 ALTER TABLE `Cities` DISABLE KEYS */;
INSERT INTO `Cities` VALUES (1,'Moscow'),(2,'SPb'),(3,'Sochi'),(4,'Rostov'),(5,'Volgograd'),(6,'Tallinn'),(13,'Riga'),(14,'Minsk'),(15,'Warszawa'),(16,'Praha'),(17,'Berlin'),(18,'Frankfurt');
/*!40000 ALTER TABLE `Cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Drivers`
--

DROP TABLE IF EXISTS `Drivers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Drivers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL DEFAULT 'Ivan',
  `surname` varchar(45) NOT NULL DEFAULT 'Ivanov',
  `hoursWorkedThisMonth` int(11) NOT NULL DEFAULT '0',
  `state` enum('REST','SHIFT','DRIVE') NOT NULL DEFAULT 'REST',
  `currentCityId` int(11) NOT NULL,
  `currentTruckId` int(11) DEFAULT NULL,
  `currentOrderId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Drivers_Cities_idx` (`currentCityId`),
  KEY `FK_Drivers_Trucks_idx` (`currentTruckId`),
  CONSTRAINT `FK_Drivers_Cities` FOREIGN KEY (`currentCityId`) REFERENCES `Cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Drivers_Trucks` FOREIGN KEY (`currentTruckId`) REFERENCES `Trucks` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Drivers`
--

LOCK TABLES `Drivers` WRITE;
/*!40000 ALTER TABLE `Drivers` DISABLE KEYS */;
INSERT INTO `Drivers` VALUES (5,'Will','Smith',11,'DRIVE',2,1,1),(7,'Angelina','Jolie',11,'DRIVE',2,1,1),(9,'Kirsten','Danst',11,'DRIVE',2,1,1),(11,'Mila','Kunis',11,'DRIVE',2,2,2),(13,'Halle','Berry',10,'DRIVE',2,2,2),(17,'Mike','Tomson',40,'DRIVE',1,NULL,NULL),(18,'Jim','Morisson',60,'DRIVE',1,NULL,NULL),(19,'Tom','Clancy',30,'DRIVE',1,NULL,NULL);
/*!40000 ALTER TABLE `Drivers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Orders`
--

DROP TABLE IF EXISTS `Orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `isFinished` tinyint(1) DEFAULT NULL,
  `routeId` int(11) DEFAULT NULL,
  `truckId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_orders_trucks_idx` (`truckId`),
  CONSTRAINT `FK_orders_trucks` FOREIGN KEY (`truckId`) REFERENCES `Trucks` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Orders`
--

LOCK TABLES `Orders` WRITE;
/*!40000 ALTER TABLE `Orders` DISABLE KEYS */;
INSERT INTO `Orders` VALUES (1,0,1,1),(2,1,9,2);
/*!40000 ALTER TABLE `Orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `RoutePoints`
--

DROP TABLE IF EXISTS `RoutePoints`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `RoutePoints` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cityId` int(11) DEFAULT NULL,
  `cargoId` int(11) DEFAULT NULL,
  `type` enum('LOAD','UNLOAD') DEFAULT NULL,
  `nextPointId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RoutePoints`
--

LOCK TABLES `RoutePoints` WRITE;
/*!40000 ALTER TABLE `RoutePoints` DISABLE KEYS */;
INSERT INTO `RoutePoints` VALUES (1,2,1,'LOAD',2),(2,6,2,'LOAD',3),(3,13,3,'LOAD',4),(4,14,4,'LOAD',5),(5,15,1,'UNLOAD',6),(6,16,2,'UNLOAD',7),(7,17,3,'UNLOAD',8),(8,18,4,'UNLOAD',NULL),(9,2,6,'LOAD',10),(10,1,6,'UNLOAD',11),(11,1,5,'LOAD',12),(12,2,5,'UNLOAD',NULL);
/*!40000 ALTER TABLE `RoutePoints` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Trucks`
--

DROP TABLE IF EXISTS `Trucks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Trucks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `regNumber` varchar(7) NOT NULL,
  `shiftSize` int(11) NOT NULL DEFAULT '2',
  `capacity` int(11) NOT NULL DEFAULT '10',
  `state` varchar(10) NOT NULL,
  `currentCityId` int(11) DEFAULT NULL,
  `orderId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `city_id_idx` (`currentCityId`),
  KEY `FK_trucks_orders_idx` (`orderId`),
  CONSTRAINT `FK_trucks_cities` FOREIGN KEY (`currentCityId`) REFERENCES `Cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_trucks_orders` FOREIGN KEY (`orderId`) REFERENCES `Orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trucks`
--

LOCK TABLES `Trucks` WRITE;
/*!40000 ALTER TABLE `Trucks` DISABLE KEYS */;
INSERT INTO `Trucks` VALUES (1,'AA10223',4,20,'OK',2,1),(2,'BB10223',4,20,'OK',2,1),(3,'CC10223',4,20,'BROKEN',1,NULL),(4,'DD10223',4,20,'OK',4,NULL),(5,'EE10223',4,20,'BROKEN',5,NULL),(6,'GG10000',3,100,'OK',1,NULL),(44,'HG22000',2,20,'BROKEN',3,NULL),(55,'YU50023',2,40,'OK',18,NULL),(57,'YO25552',10,100,'OK',3,NULL),(58,'YO25552',10,100,'OK',17,NULL);
/*!40000 ALTER TABLE `Trucks` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-19  4:14:11
