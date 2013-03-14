CREATE DATABASE `facebookdatamining` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `facebookdatamining`;

-- --------------------------------------------------------

--
-- Table structure for table `profile`
--

CREATE TABLE IF NOT EXISTS `profile` (
  `Id` bigint(20) NOT NULL,
  `CrawllerLevel` bigint(20) NOT NULL,
  `Recorded` tinyint(1) NOT NULL DEFAULT '0',
  `Name` varchar(255) DEFAULT NULL,
  `About` text,
  `Birthday` varchar(255) DEFAULT NULL,
  `Sex` varchar(255) DEFAULT NULL,
  `InterestedIn` varchar(255) DEFAULT NULL,
  `ReltionshipStatus` varchar(255) DEFAULT NULL,
  `ReligiousView` text,
  `PoliticalViews` text,
  `Emails` varchar(255) DEFAULT NULL,
  `MobilePhones` varchar(255) DEFAULT NULL,
  `OtherPhones` varchar(255) DEFAULT NULL,
  `IMScreenNames` text,
  `Address` text,
  `WebSites` varchar(255) DEFAULT NULL,
  `Networks` varchar(255) DEFAULT NULL,
  `Job` text,
  `College` text,
  `HighSchool` text,
  `CurrentCity` varchar(255) DEFAULT NULL,
  `Hometown` varchar(255) DEFAULT NULL,
  `Family` text,
  `Books` text,
  `Music` text,
  `Movies` text,
  `Televisions` text,
  `Activities` text,
  `Interests` text,
  `QuantityOfPhotos` int(11) DEFAULT NULL,
  `QuantityOfFriends` int(11) DEFAULT NULL,
  `Friends` text,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;