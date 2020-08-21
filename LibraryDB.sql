-- MySQL dump 10.13  Distrib 8.0.21, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: libraryDB
-- ------------------------------------------------------
-- Server version	8.0.19-0ubuntu5

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
-- Table structure for table `Account`
--

DROP TABLE IF EXISTS `Account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Account` (
  `accountID` int NOT NULL AUTO_INCREMENT,
  `personID` int DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `accountStatus` varchar(45) DEFAULT NULL,
  `createdOn` date DEFAULT NULL,
  `accountType` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`accountID`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk_Account_1_idx` (`personID`),
  CONSTRAINT `fk_Account_1` FOREIGN KEY (`personID`) REFERENCES `Person` (`personID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Account`
--

LOCK TABLES `Account` WRITE;
/*!40000 ALTER TABLE `Account` DISABLE KEYS */;
INSERT INTO `Account` VALUES (1,16,'traidat','123456','Active','2020-08-11','Member'),(2,1,'trai','123456','Active','2020-08-11','Librarian');
/*!40000 ALTER TABLE `Account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Author`
--

DROP TABLE IF EXISTS `Author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Author` (
  `authorID` int NOT NULL AUTO_INCREMENT,
  `authorName` varchar(45) DEFAULT NULL,
  `authorDetail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`authorID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Author`
--

LOCK TABLES `Author` WRITE;
/*!40000 ALTER TABLE `Author` DISABLE KEYS */;
INSERT INTO `Author` VALUES (5,'traidat','traidat'),(6,'TheEarth','TheEarth'),(7,'Lucas','Lucas');
/*!40000 ALTER TABLE `Author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Book`
--

DROP TABLE IF EXISTS `Book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Book` (
  `bookID` int NOT NULL AUTO_INCREMENT,
  `nameBook` varchar(45) DEFAULT NULL,
  `bookStatus` varchar(45) DEFAULT NULL,
  `codeLocation` varchar(45) DEFAULT NULL,
  `authorID` int DEFAULT NULL,
  `categoryID` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `language` varchar(45) DEFAULT NULL,
  `numberPage` int DEFAULT NULL,
  `dateBuy` date DEFAULT NULL,
  PRIMARY KEY (`bookID`),
  KEY `fk_Book_1_idx` (`categoryID`),
  KEY `fk_Book_2_idx` (`authorID`),
  KEY `fk_Book_3_idx` (`bookStatus`),
  KEY `fk_Book_3_idx1` (`codeLocation`),
  CONSTRAINT `fk_Book_1` FOREIGN KEY (`categoryID`) REFERENCES `Category` (`categoryID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Book_2` FOREIGN KEY (`authorID`) REFERENCES `Author` (`authorID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_Book_3` FOREIGN KEY (`codeLocation`) REFERENCES `CodeLocation` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Book`
--

LOCK TABLES `Book` WRITE;
/*!40000 ALTER TABLE `Book` DISABLE KEYS */;
INSERT INTO `Book` VALUES (1,'vietnam trai','Available','f1s2',5,1,12345,'vietnamese',123,'2020-02-02'),(2,'sach hay','Available','f1s3',5,2,12345,'vietnamese',1000,'2019-12-12'),(3,'duoc khong','Available','f1s3-123',6,3,199999,'English',1234,'2020-02-02'),(4,'the gioi nay','Available','f2s4-123',7,2,10000,'English',1234,'2020-02-02'),(5,'the gioi nay la cua chung mai','Available','f8s3-123',5,3,12313,'vietnam',1234,'2019-12-12');
/*!40000 ALTER TABLE `Book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Category`
--

DROP TABLE IF EXISTS `Category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Category` (
  `categoryID` int NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(45) DEFAULT NULL,
  `categoryDetail` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`categoryID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Category`
--

LOCK TABLES `Category` WRITE;
/*!40000 ALTER TABLE `Category` DISABLE KEYS */;
INSERT INTO `Category` VALUES (1,'Fiction','contain a story that the author made up, such as romance or children\'s books'),(2,'Romantic','A story about love'),(3,'Thriller','Thrillers are suspenseful stories, which makes a crime thriller a book that has a crime as its main subject while keeping readers on edge');
/*!40000 ALTER TABLE `Category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CodeLocation`
--

DROP TABLE IF EXISTS `CodeLocation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `CodeLocation` (
  `code` varchar(45) NOT NULL,
  `codeDetail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`code`),
  KEY `index2` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CodeLocation`
--

LOCK TABLES `CodeLocation` WRITE;
/*!40000 ALTER TABLE `CodeLocation` DISABLE KEYS */;
INSERT INTO `CodeLocation` VALUES ('f1s2','floor 1 shelf 2'),('f1s3','floor 1 shelf 3'),('f1s3-123','floor 1 self 3 number 123'),('f2s4-123','floor 2 self 4 123'),('f8s3-123','floor 8 self 3 123');
/*!40000 ALTER TABLE `CodeLocation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LendingBook`
--

DROP TABLE IF EXISTS `LendingBook`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LendingBook` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `bookID` int DEFAULT NULL,
  `username` varchar(45) DEFAULT NULL,
  `lendingDate` date DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `returnDate` date DEFAULT NULL,
  `bookStatus` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_LendingBook_1_idx` (`bookID`),
  KEY `fk_LendingBook_1_idx1` (`username`),
  CONSTRAINT `fk_LendingBook_1` FOREIGN KEY (`username`) REFERENCES `Account` (`username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LendingBook`
--

LOCK TABLES `LendingBook` WRITE;
/*!40000 ALTER TABLE `LendingBook` DISABLE KEYS */;
INSERT INTO `LendingBook` VALUES (5,4,'trai','2020-08-17','2020-08-27',NULL,'Returned'),(6,1,'trai','2020-08-17','2020-08-27','2020-08-17','Returned'),(7,1,'trai','2020-08-19','2020-08-19',NULL,'Returned');
/*!40000 ALTER TABLE `LendingBook` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notification`
--

DROP TABLE IF EXISTS `Notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Notification` (
  `notificationID` int NOT NULL AUTO_INCREMENT,
  `personID` int DEFAULT NULL,
  `content` varchar(45) DEFAULT NULL,
  `createdOn` date DEFAULT NULL,
  PRIMARY KEY (`notificationID`),
  KEY `fk_Notification_1_idx` (`personID`),
  CONSTRAINT `fk_Notification_1` FOREIGN KEY (`personID`) REFERENCES `Person` (`personID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notification`
--

LOCK TABLES `Notification` WRITE;
/*!40000 ALTER TABLE `Notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `Notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Person`
--

DROP TABLE IF EXISTS `Person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Person` (
  `personID` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`personID`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Person`
--

LOCK TABLES `Person` WRITE;
/*!40000 ALTER TABLE `Person` DISABLE KEYS */;
INSERT INTO `Person` VALUES (1,'Truong','0123456789','t@gmail.com','Ha Noi'),(12,'Trai dat','124556','t1@gmail.com','1234567'),(16,'truong','09','hg@gmail.com','Ha Noi'),(17,'taada','123124','hgg@gmail.com','1asdad');
/*!40000 ALTER TABLE `Person` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-08-21 16:23:01
