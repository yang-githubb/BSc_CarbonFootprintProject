-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 04, 2024 at 05:25 AM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `carbonfootprint`
--

-- --------------------------------------------------------

--
-- Table structure for table `answers`
--

CREATE TABLE `answers` (
  `answer_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `option_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `answers`
--

INSERT INTO `answers` (`answer_id`, `user_id`, `question_id`, `option_id`) VALUES
(51, 10, 17, 2);

-- --------------------------------------------------------

--
-- Table structure for table `options`
--

CREATE TABLE `options` (
  `option_id` int(11) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  `option_text` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `options`
--

INSERT INTO `options` (`option_id`, `question_id`, `option_text`) VALUES
(1, 1, '1'),
(2, 1, '2'),
(3, 1, '3'),
(4, 1, '4'),
(5, 1, 'More than 4'),
(6, 2, 'Renewable Energy'),
(7, 2, 'Electricity'),
(8, 3, 'Below 100kWh'),
(9, 3, '100-199kWh'),
(10, 3, '200-399kWh'),
(11, 3, '400-599kWh'),
(12, 3, 'More than 600kWh'),
(13, 4, 'Shower with no heater'),
(14, 4, 'Shower with heater (warm water)'),
(15, 4, 'Shower with heater (hot water)'),
(16, 5, 'Daily'),
(17, 5, 'Once a week'),
(18, 5, 'Twice a week'),
(19, 5, 'Once a month'),
(20, 6, 'Rack'),
(21, 6, 'Dryer'),
(22, 6, 'Send to laundrette'),
(23, 7, 'Supermarket'),
(24, 7, 'Online'),
(25, 7, 'Local market (pasar)'),
(26, 8, 'Below RM200'),
(27, 8, 'RM200-RM399'),
(28, 8, 'RM400-RM600'),
(29, 8, 'More than RM600'),
(30, 9, 'Never'),
(31, 9, 'Rarely'),
(32, 9, 'Often'),
(33, 9, 'Always'),
(34, 10, 'No'),
(35, 10, 'Yes'),
(36, 10, 'Depends'),
(37, 11, 'No'),
(38, 11, 'Yes'),
(39, 11, 'Depends'),
(40, 12, 'No'),
(41, 12, 'Yes'),
(42, 12, 'Depends'),
(43, 13, 'Never'),
(44, 13, 'Rarely'),
(45, 13, 'Often'),
(46, 13, 'Always'),
(47, 14, 'Never'),
(48, 14, 'Rarely'),
(49, 14, 'Often'),
(50, 14, 'Always'),
(51, 15, 'Never'),
(52, 15, 'Rarely'),
(53, 15, 'Often'),
(54, 15, 'Always'),
(55, 16, 'No'),
(56, 16, 'Yes'),
(57, 17, 'RM0-RM49'),
(58, 17, 'RM50-RM99'),
(59, 17, 'RM100-RM199'),
(60, 17, 'RM200-RM299'),
(61, 17, 'More than RM300'),
(63, 1, '1'),
(64, 1, '2'),
(65, 1, '3'),
(66, 1, '4'),
(67, 1, 'More than 4'),
(68, 2, 'Renewable Energy'),
(69, 2, 'Electricity'),
(70, 3, 'Below 100kWh'),
(71, 3, '100-199kWh'),
(72, 3, '200-399kWh'),
(73, 3, '400-599kWh'),
(74, 3, 'More than 600kWh'),
(75, 4, 'Shower with no heater'),
(76, 4, 'Shower with heater (warm water)'),
(77, 4, 'Shower with heater (hot water)'),
(78, 5, 'Daily'),
(79, 5, 'Once a week'),
(80, 5, 'Twice a week'),
(81, 5, 'Once a month'),
(82, 6, 'Rack'),
(83, 6, 'Dryer'),
(84, 6, 'Send to laundrette'),
(85, 7, 'Supermarket'),
(86, 7, 'Online'),
(87, 7, 'Local market (pasar)'),
(88, 8, 'Below RM200'),
(89, 8, 'RM200-RM399'),
(90, 8, 'RM400-RM600'),
(91, 8, 'More than RM600'),
(92, 9, 'Never'),
(93, 9, 'Rarely'),
(94, 9, 'Often'),
(95, 9, 'Always'),
(96, 10, 'No'),
(97, 10, 'Yes'),
(98, 10, 'Depends'),
(99, 11, 'No'),
(100, 11, 'Yes'),
(101, 11, 'Depends'),
(102, 12, 'No'),
(103, 12, 'Yes'),
(104, 12, 'Depends'),
(105, 13, 'Never'),
(106, 13, 'Rarely'),
(107, 13, 'Often'),
(108, 13, 'Always'),
(109, 14, 'Never'),
(110, 14, 'Rarely'),
(111, 14, 'Often'),
(112, 14, 'Always'),
(113, 15, 'Never'),
(114, 15, 'Rarely'),
(115, 15, 'Often'),
(116, 15, 'Always'),
(117, 16, 'No'),
(118, 16, 'Yes'),
(119, 17, 'RM0-RM49'),
(120, 17, 'RM50-RM99'),
(121, 17, 'RM100-RM199'),
(122, 17, 'RM200-RM299'),
(123, 17, 'More than RM300'),
(129, 8, 'Below RM200'),
(130, 8, 'RM200-RM399'),
(131, 8, 'RM400-RM600'),
(132, 8, 'More than RM600'),
(133, 9, 'Never'),
(134, 9, 'Rarely'),
(135, 9, 'Often'),
(136, 9, 'Always'),
(137, 10, 'No'),
(138, 10, 'Yes'),
(139, 10, 'Depends'),
(140, 11, 'No'),
(141, 11, 'Yes'),
(142, 11, 'Depends'),
(143, 12, 'No'),
(144, 12, 'Yes'),
(145, 12, 'Depends'),
(146, 13, 'Never'),
(147, 13, 'Rarely'),
(148, 13, 'Often'),
(149, 13, 'Always'),
(150, 14, 'Never'),
(151, 14, 'Rarely'),
(152, 14, 'Often'),
(153, 14, 'Always'),
(154, 15, 'Never'),
(155, 15, 'Rarely'),
(156, 15, 'Often'),
(157, 15, 'Always'),
(158, 16, 'No'),
(159, 16, 'Yes'),
(160, 17, 'RM0-RM49'),
(161, 17, 'RM50-RM99'),
(162, 17, 'RM100-RM199'),
(163, 17, 'RM200-RM299'),
(164, 17, 'More than RM300'),
(170, 8, 'Below RM200'),
(171, 8, 'RM200-RM399'),
(172, 8, 'RM400-RM600'),
(173, 8, 'More than RM600'),
(174, 9, 'Never'),
(175, 9, 'Rarely'),
(176, 9, 'Often'),
(177, 9, 'Always'),
(178, 10, 'No'),
(179, 10, 'Yes'),
(180, 10, 'Depends'),
(181, 11, 'No'),
(182, 11, 'Yes'),
(183, 11, 'Depends'),
(184, 12, 'No'),
(185, 12, 'Yes'),
(186, 12, 'Depends'),
(187, 13, 'Never'),
(188, 13, 'Rarely'),
(189, 13, 'Often'),
(190, 13, 'Always'),
(191, 14, 'Never'),
(192, 14, 'Rarely'),
(193, 14, 'Often'),
(194, 14, 'Always'),
(195, 15, 'Never'),
(196, 15, 'Rarely'),
(197, 15, 'Often'),
(198, 15, 'Always'),
(199, 16, 'No'),
(200, 16, 'Yes'),
(201, 17, 'RM0-RM49'),
(202, 17, 'RM50-RM99'),
(203, 17, 'RM100-RM199'),
(204, 17, 'RM200-RM299'),
(205, 17, 'More than RM300'),
(211, 8, 'Below RM200'),
(212, 8, 'RM200-RM399'),
(213, 8, 'RM400-RM600'),
(214, 8, 'More than RM600'),
(215, 9, 'Never'),
(216, 9, 'Rarely'),
(217, 9, 'Often'),
(218, 9, 'Always'),
(219, 10, 'No'),
(220, 10, 'Yes'),
(221, 10, 'Depends'),
(222, 11, 'No'),
(223, 11, 'Yes'),
(224, 11, 'Depends'),
(225, 12, 'No'),
(226, 12, 'Yes'),
(227, 12, 'Depends'),
(228, 13, 'Never'),
(229, 13, 'Rarely'),
(230, 13, 'Often'),
(231, 13, 'Always'),
(232, 14, 'No'),
(233, 14, 'Yes'),
(234, 15, 'RM0-RM49'),
(235, 15, 'RM50-RM99'),
(236, 15, 'RM100-RM199'),
(237, 15, 'RM200-RM299'),
(238, 15, 'More than RM300'),
(239, 16, 'Walk'),
(240, 16, 'Bus'),
(241, 16, 'Bike'),
(242, 16, 'Personal vehicle'),
(243, 16, 'Other'),
(244, 17, 'Walk'),
(245, 17, 'Bus'),
(246, 17, 'Bike'),
(247, 17, 'Personal vehicle'),
(248, 17, 'Other');

-- --------------------------------------------------------

--
-- Table structure for table `questions`
--

CREATE TABLE `questions` (
  `question_id` int(11) NOT NULL,
  `question_text` text NOT NULL,
  `category` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questions`
--

INSERT INTO `questions` (`question_id`, `question_text`, `category`) VALUES
(1, 'How many people live in your household?', 'house'),
(2, 'What is the source of energy?', 'house'),
(3, 'How much energy do you approximately consume monthly?', 'house'),
(4, 'How do you take a bath on daily basis?', 'house'),
(5, 'How often do you do laundry?', 'house'),
(6, 'How do you dry clothes after washing them?', 'house'),
(7, 'Where do you usually purchase groceries?', 'food'),
(8, 'How much do you usually spend on groceries weekly?', 'food'),
(9, 'How frequently do you eat at a restaurant on a weekly basis?', 'food'),
(10, 'Do you pack the leftover food in a restaurant when leaving?', 'food'),
(11, 'If the food that you have prepared is not finished, will you keep the leftover?', 'food'),
(12, 'Will you try your best to finish the food on your plate?', 'food'),
(13, 'How frequently do you bring your bag whenever you plan to buy something', 'food'),
(14, 'Do you or your family member own a Hybrid or electronic car?', 'transportation'),
(15, 'How much do you spend for your fuel on weekly basis?', 'transportation'),
(16, 'How do you go to school?', 'transportation'),
(17, 'What means of transport do you use the most?', 'transportation');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `home_index` int(11) NOT NULL,
  `transportation_index` int(11) NOT NULL,
  `food_index` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `username`, `password`, `home_index`, `transportation_index`, `food_index`) VALUES
(10, 'yang@546@hotmail.com', 'yang', '$2y$10$N0TAkn90pGx5aP1XRc0doOi3QoGh9zfF8iGifLpK2yF0utJvNnWIm', 0, 0, 0),
(11, 'rk@ruikang.com', 'rk', '$2y$10$.E.FjIfTNsiOmUP6eANFcuon3mYa18B2/.Z1nkMiZKnrCcF6BFS6C', 0, 0, 0),
(13, '1', '1', '$2y$10$4M3.xjQbs7/5a0qM/ZhP2OXiK9VDon7ttusafZ2CxW8yEXXLwn7le', 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`answer_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `question_id` (`question_id`),
  ADD KEY `option_id` (`option_id`);

--
-- Indexes for table `options`
--
ALTER TABLE `options`
  ADD PRIMARY KEY (`option_id`),
  ADD KEY `question_id` (`question_id`);

--
-- Indexes for table `questions`
--
ALTER TABLE `questions`
  ADD PRIMARY KEY (`question_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `answers`
--
ALTER TABLE `answers`
  MODIFY `answer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `options`
--
ALTER TABLE `options`
  MODIFY `option_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=249;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `question_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `answers_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`),
  ADD CONSTRAINT `answers_ibfk_3` FOREIGN KEY (`option_id`) REFERENCES `options` (`option_id`);

--
-- Constraints for table `options`
--
ALTER TABLE `options`
  ADD CONSTRAINT `options_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
