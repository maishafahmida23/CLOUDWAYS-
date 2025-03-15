-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 10, 2024 at 07:57 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `airlinedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `bookingstbl`
--

CREATE TABLE `bookingstbl` (
  `TicketId` int(11) NOT NULL,
  `PName` varchar(255) NOT NULL,
  `FICode` varchar(255) NOT NULL,
  `PSeat` varchar(255) NOT NULL,
  `PPass` varchar(255) NOT NULL,
  `Amount` int(11) NOT NULL,
  `Nationality` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bookingstbl`
--

INSERT INTO `bookingstbl` (`TicketId`, `PName`, `FICode`, `PSeat`, `PPass`, `Amount`, `Nationality`) VALUES
(1, 'Ramisha', 'F9087', 'Window seat', 'BD45367', 12000, 'Bangladeshi'),
(3, 'Maisha', 'F0025XZ', 'Middle row', 'IND2367', 32000, 'Indian'),
(4, 'Arijit', 'F9087', 'Front Seat', 'IND67545', 45690, 'Indian');

-- --------------------------------------------------------

--
-- Table structure for table `cancellationtbl`
--

CREATE TABLE `cancellationtbl` (
  `CancId` int(11) NOT NULL,
  `TicketId` int(11) NOT NULL,
  `FICode` varchar(255) NOT NULL,
  `CancDate` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cancellationtbl`
--

INSERT INTO `cancellationtbl` (`CancId`, `TicketId`, `FICode`, `CancDate`) VALUES
(1, 2, 'F0025XZ', 'Sat Jan 06 16:55:41 BDT 2024'),
(2, 2, 'F0025XZ', 'Sat Jan 06 17:02:59 BDT 2024'),
(3, 2, 'F0025XZ', 'Sat Jan 06 17:08:11 BDT 2024');

-- --------------------------------------------------------

--
-- Table structure for table `flighttbl`
--

CREATE TABLE `flighttbl` (
  `FICode` varchar(255) NOT NULL,
  `FISource` varchar(255) NOT NULL,
  `FIDest` varchar(255) NOT NULL,
  `FIDate` varchar(255) NOT NULL,
  `FISeats` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `flighttbl`
--

INSERT INTO `flighttbl` (`FICode`, `FISource`, `FIDest`, `FIDate`, `FISeats`) VALUES
('F0025XZ', 'Bangladesh', 'London', 'Mon Jan 01 22:53:17 BDT 2024', 500),
('F9087', 'Bangladesh', 'Canada', 'Thu Jan 04 00:00:00 BDT 2024', 56);

-- --------------------------------------------------------

--
-- Table structure for table `passengerstbl`
--

CREATE TABLE `passengerstbl` (
  `PId` int(11) NOT NULL,
  `PName` varchar(255) NOT NULL,
  `PNat` varchar(255) NOT NULL,
  `PGen` varchar(255) NOT NULL,
  `PPass` varchar(255) NOT NULL,
  `PAdd` varchar(255) NOT NULL,
  `Pphone` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `passengerstbl`
--

INSERT INTO `passengerstbl` (`PId`, `PName`, `PNat`, `PGen`, `PPass`, `PAdd`, `Pphone`) VALUES
(1, 'Ramisha', 'Bangladeshi', 'Female', 'BD45367', 'Dhaka', '01897645907'),
(2, 'Maisha', 'Indian', 'Female', 'IND2367', 'Kolkata', '998646237'),
(3, 'Arijit', 'Indian', 'Male', 'IND67545', 'Delhi', '98765467'),
(4, 'Nabila', 'Bangladeshi', 'Female', 'BD12363', 'Dhaka', '01678564321');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bookingstbl`
--
ALTER TABLE `bookingstbl`
  ADD PRIMARY KEY (`TicketId`);

--
-- Indexes for table `cancellationtbl`
--
ALTER TABLE `cancellationtbl`
  ADD PRIMARY KEY (`CancId`);

--
-- Indexes for table `flighttbl`
--
ALTER TABLE `flighttbl`
  ADD PRIMARY KEY (`FICode`);

--
-- Indexes for table `passengerstbl`
--
ALTER TABLE `passengerstbl`
  ADD PRIMARY KEY (`PId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bookingstbl`
--
ALTER TABLE `bookingstbl`
  MODIFY `TicketId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
