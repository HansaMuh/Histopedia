-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 03, 2025 at 09:03 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `histopedia`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` varchar(6) NOT NULL,
  `title` varchar(100) NOT NULL,
  `genre` varchar(25) NOT NULL,
  `description` varchar(500) NOT NULL,
  `author` varchar(50) NOT NULL,
  `publisher` varchar(50) NOT NULL,
  `publication_year` int(11) NOT NULL,
  `isbn` varchar(25) NOT NULL,
  `status` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `genre`, `description`, `author`, `publisher`, `publication_year`, `isbn`, `status`) VALUES
('BID001', 'Biography of Soekarno', 'Biography', 'A detailed biography of Soekarno, the first President of Indonesia.', 'Budi Santoso', 'Gramedia Pustaka Utama', 2010, '978-1234567890', 'Available'),
('BID002', 'Biography of Hatta', 'Biography', 'A detailed biography of Mohammad Hatta, the first Vice President of Indonesia.', 'Siti Aminah', 'Balai Pustaka', 2011, '978-1234567891', 'Available'),
('BID003', 'Military History of Indonesia', 'Military History', 'An overview of the military history of Indonesia.', 'Agus Pratama', 'Kompas Gramedia', 2012, '978-1234567892', 'Available'),
('BID004', 'Indonesian Military Campaigns', 'Military History', 'A detailed account of various military campaigns in Indonesia.', 'Dewi Lestari', 'Pustaka Sinar Harapan', 2013, '978-1234567893', 'Available'),
('BID005', 'Indonesian Revolution', 'Revolution', 'A comprehensive study of the Indonesian revolution.', 'Rizky Hidayat', 'Yayasan Obor Indonesia', 2014, '978-1234567894', 'Available');

-- --------------------------------------------------------

--
-- Table structure for table `borrowing_records`
--

CREATE TABLE `borrowing_records` (
  `id` varchar(6) NOT NULL,
  `book_id` varchar(6) NOT NULL,
  `patron_id` varchar(6) NOT NULL,
  `librarian_id` varchar(6) DEFAULT NULL,
  `borrowing_date` date NOT NULL,
  `due_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  `request_state` int(11) NOT NULL,
  `record_status` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` varchar(6) NOT NULL,
  `access_level` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email_address` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `access_level`, `username`, `email_address`, `password`) VALUES
('UID001', 0, 'Budi', 'budiman@gmail.com', 'BudiBudi');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `borrowing_records`
--
ALTER TABLE `borrowing_records`
  ADD PRIMARY KEY (`id`),
  ADD KEY `book_id` (`book_id`),
  ADD KEY `patron_id` (`patron_id`),
  ADD KEY `librarian_id` (`librarian_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `borrowing_records`
--
ALTER TABLE `borrowing_records`
  ADD CONSTRAINT `borrowing_records_ibfk_1` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`),
  ADD CONSTRAINT `borrowing_records_ibfk_2` FOREIGN KEY (`patron_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `borrowing_records_ibfk_3` FOREIGN KEY (`librarian_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
