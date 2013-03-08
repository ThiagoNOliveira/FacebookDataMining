-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 08, 2013 at 02:28 AM
-- Server version: 5.5.27
-- PHP Version: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `facebookdatamining`
--

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
  `Id` bigint(20) NOT NULL,
  `CrawllerLevel` bigint(20) NOT NULL,
  `Recorded` tinyint(1) NOT NULL DEFAULT '0',
  `Name` varchar(255)  DEFAULT NULL,
  `About` text ,
  `Birthday` varchar(255)  DEFAULT NULL,
  `Sex` varchar(255)  DEFAULT NULL,
  `InterestedIn` varchar(255)  DEFAULT NULL,
  `ReltionshipStatus` varchar(255)  DEFAULT NULL,
  `ReligiousView` text ,
  `PoliticalViews` text ,
  `Emails` varchar(255)  DEFAULT NULL,
  `MobilePhones` varchar(255)  DEFAULT NULL,
  `OtherPhones` varchar(255)  DEFAULT NULL,
  `IMScreenNames` text ,
  `Address` text ,
  `WebSites` varchar(255)  DEFAULT NULL,
  `Networks` varchar(255)  DEFAULT NULL,
  `Job` text ,
  `College` text ,
  `HighSchool` text ,
  `CurrentCity` varchar(255)  DEFAULT NULL,
  `Hometown` varchar(255)  DEFAULT NULL,
  `Family` text ,
  `Books` text ,
  `Music` text ,
  `Movies` text ,
  `Televisions` text,
  `Activities` text ,
  `Interests` text ,
  `QuantityOfPhotos` int(11) DEFAULT NULL,
  `QuantityOfFriends` int(11) DEFAULT NULL,
  `Friends` text ,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
