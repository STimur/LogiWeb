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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cargos`
--

LOCK TABLES `Cargos` WRITE;
/*!40000 ALTER TABLE `Cargos` DISABLE KEYS */;
INSERT INTO `Cargos` VALUES (1,'A',10000,'READY'),(2,'cheese',5000,'READY'),(3,'stone',8000,'READY'),(4,'milk',15000,'READY'),(5,'toyota',4000,'READY'),(6,'cats',1000,'READY');
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Cities`
--

LOCK TABLES `Cities` WRITE;
/*!40000 ALTER TABLE `Cities` DISABLE KEYS */;
INSERT INTO `Cities` VALUES (1,'Saint Petersburg'),(2,'Frankfurt'),(3,'London'),(4,'Milan'),(5,'Volgograd');
/*!40000 ALTER TABLE `Cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Distances`
--

DROP TABLE IF EXISTS `Distances`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Distances` (
  `fromCityId` int(11) NOT NULL,
  `toCityId` int(11) NOT NULL,
  `distance` int(11) NOT NULL,
  KEY `FK_distFromCity_Сities_idx` (`fromCityId`),
  KEY `FK_DistToCity_Сities_idx` (`toCityId`),
  CONSTRAINT `FK_DistFromCity_Сities` FOREIGN KEY (`fromCityId`) REFERENCES `Cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_DistToCity_Сities` FOREIGN KEY (`toCityId`) REFERENCES `Cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Distances`
--

LOCK TABLES `Distances` WRITE;
/*!40000 ALTER TABLE `Distances` DISABLE KEYS */;
INSERT INTO `Distances` VALUES (1,1,0),(1,2,2179),(1,3,2727),(1,4,2736),(1,5,1681),(2,1,2179),(2,2,0),(2,3,764),(2,4,662),(2,5,3096),(3,1,2727),(3,2,764),(3,3,0),(3,4,1251),(3,5,3647),(4,1,2736),(4,2,662),(4,3,1251),(4,4,0),(4,5,3262),(5,1,1681),(5,2,3096),(5,3,3647),(5,4,3262),(5,5,0);
/*!40000 ALTER TABLE `Distances` ENABLE KEYS */;
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
  KEY `FK_Drivers_Orders_idx` (`currentOrderId`),
  CONSTRAINT `FK_Drivers_Cities` FOREIGN KEY (`currentCityId`) REFERENCES `Cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Drivers_Orders` FOREIGN KEY (`currentOrderId`) REFERENCES `Orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Drivers_Trucks` FOREIGN KEY (`currentTruckId`) REFERENCES `Trucks` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Drivers`
--

LOCK TABLES `Drivers` WRITE;
/*!40000 ALTER TABLE `Drivers` DISABLE KEYS */;
INSERT INTO `Drivers` VALUES (5,'Will','Smith',11,'REST',3,NULL,NULL),(7,'Angelina','Jolie',11,'DRIVE',2,1,1),(9,'Kirsten','Danst',11,'DRIVE',2,1,1),(11,'Mila','Kunis',11,'DRIVE',2,2,2),(13,'Halle','Berry',10,'DRIVE',2,2,2),(17,'Mike','Tomson',40,'DRIVE',1,NULL,NULL),(18,'Jim','Morisson',60,'DRIVE',1,NULL,NULL),(19,'Tom','Clancy',30,'DRIVE',1,NULL,NULL),(27,'Misha','Popov',0,'DRIVE',3,NULL,NULL),(29,'Misha','Popov',0,'REST',3,NULL,NULL),(48,'Max','Ivanov',0,'REST',3,NULL,NULL),(49,'Dima','Petrov',0,'SHIFT',3,NULL,NULL),(50,'Max','Ivanov',0,'SHIFT',3,NULL,NULL);
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
  KEY `FK_Orders_RoutePoints_idx` (`routeId`),
  KEY `FK_Orders_Trucks_idx` (`truckId`),
  CONSTRAINT `FK_Orders_RoutePoints` FOREIGN KEY (`routeId`) REFERENCES `RoutePoints` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Orders_Trucks` FOREIGN KEY (`truckId`) REFERENCES `Trucks` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
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
  PRIMARY KEY (`id`),
  KEY `FK_RoutePoints_Cities_idx` (`cityId`),
  KEY `FK_RoutePoints_Cargos_idx` (`cargoId`),
  KEY `FK_RoutePoints_RoutePoints_idx` (`nextPointId`),
  CONSTRAINT `FK_RoutePoints_Cargos` FOREIGN KEY (`cargoId`) REFERENCES `Cargos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_RoutePoints_Cities` FOREIGN KEY (`cityId`) REFERENCES `Cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_RoutePoints_RoutePoints` FOREIGN KEY (`nextPointId`) REFERENCES `RoutePoints` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `RoutePoints`
--

LOCK TABLES `RoutePoints` WRITE;
/*!40000 ALTER TABLE `RoutePoints` DISABLE KEYS */;
INSERT INTO `RoutePoints` VALUES (1,1,1,'LOAD',2),(2,2,2,'LOAD',3),(3,3,3,'LOAD',4),(4,4,4,'LOAD',5),(5,5,1,'UNLOAD',6),(6,2,2,'UNLOAD',7),(7,3,3,'UNLOAD',8),(8,4,4,'UNLOAD',NULL),(9,2,6,'LOAD',10),(10,1,6,'UNLOAD',11),(11,1,5,'LOAD',12),(12,2,5,'UNLOAD',NULL);
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
  CONSTRAINT `FK_Trucks_Cities` FOREIGN KEY (`currentCityId`) REFERENCES `Cities` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Trucks_Orders` FOREIGN KEY (`orderId`) REFERENCES `Orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Trucks`
--

LOCK TABLES `Trucks` WRITE;
/*!40000 ALTER TABLE `Trucks` DISABLE KEYS */;
INSERT INTO `Trucks` VALUES (1,'AA10223',2,20,'OK',2,1),(2,'BB10223',2,20,'OK',2,2),(3,'CC10223',4,20,'BROKEN',1,NULL),(4,'DD10223',4,20,'OK',4,NULL),(5,'EE10223',4,20,'BROKEN',5,NULL),(6,'GG10000',3,40,'OK',1,NULL),(44,'HG22000',2,20,'BROKEN',3,NULL),(55,'YU50023',2,40,'OK',3,NULL),(58,'TW25552',4,40,'BROKEN',3,NULL),(61,'CH35678',4,40,'BROKEN',NULL,NULL),(63,'XX20025',2,30,'BROKEN',3,NULL);
/*!40000 ALTER TABLE `Trucks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `rolename` varchar(20) NOT NULL,
  PRIMARY KEY (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('driver'),('manager');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `username` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('driver','driver'),('manager','manager');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_roles` (
  `username` varchar(20) NOT NULL,
  `rolename` varchar(20) NOT NULL,
  PRIMARY KEY (`username`,`rolename`),
  KEY `users_roles_fk2` (`rolename`),
  CONSTRAINT `users_roles_fk1` FOREIGN KEY (`username`) REFERENCES `users` (`username`),
  CONSTRAINT `users_roles_fk2` FOREIGN KEY (`rolename`) REFERENCES `roles` (`rolename`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_roles`
--

LOCK TABLES `users_roles` WRITE;
/*!40000 ALTER TABLE `users_roles` DISABLE KEYS */;
INSERT INTO `users_roles` VALUES ('driver','driver'),('manager','manager');
/*!40000 ALTER TABLE `users_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-03-01  7:49:29
