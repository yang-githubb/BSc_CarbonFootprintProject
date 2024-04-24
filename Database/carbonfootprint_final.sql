-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 22, 2024 at 07:32 PM
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
-- Table structure for table `actions`
--

CREATE TABLE `actions` (
  `id` int(11) NOT NULL,
  `action_name` varchar(255) NOT NULL,
  `action_description` varchar(255) NOT NULL,
  `action_category` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `actions`
--

INSERT INTO `actions` (`id`, `action_name`, `action_description`, `action_category`) VALUES
(1, 'Install Solar Panels', 'Install solar panels to generate clean electricity directly from sunlight.', 'home'),
(2, 'Upgrade to Energy-Efficient Appliances', 'Replace older appliances with newer, energy-efficient models to reduce energy consumption.', 'home'),
(3, 'Use LED Lighting', 'Switch all lighting to LED bulbs which use less energy and last longer than traditional bulbs.', 'home'),
(4, 'Improve Home Insulation', 'Enhance insulation in your home to maintain temperature and reduce heating and cooling needs.', 'home'),
(5, 'Install Programmable Thermostats', 'Use programmable thermostats to control air conditioning and heating based on schedule.', 'home'),
(6, 'Seal Windows and Doors', 'Seal off drafts and leaks in windows and doors to improve energy efficiency.', 'home'),
(7, 'Use Smart Power Strips', 'Plug devices into smart power strips to reduce power usage when electronics are not in use.', 'home'),
(8, 'Regular HVAC Maintenance', 'Schedule regular maintenance for your HVAC system to keep it running efficiently.', 'home'),
(9, 'Lower Water Heating Temperature', 'Reduce your water heater’s temperature setting to lower energy consumption.', 'home'),
(10, 'Hang Dry Clothes', 'Hang dry clothes instead of using a dryer to save energy.', 'home'),
(11, 'Install Energy-Efficient Windows', 'Replace existing windows with energy-efficient ones to reduce energy loss.', 'home'),
(12, 'Use Renewable Energy Sources', 'Sign up for green energy programs that supply renewable energy.', 'home'),
(13, 'Add Solar Window Films', 'Install solar window films to reduce heat gain and improve HVAC efficiency.', 'home'),
(14, 'Use Ceiling Fans', 'Utilize ceiling fans to reduce reliance on air conditioning during warmer months.', 'home'),
(15, 'Update Roofing', 'Upgrade to reflective or green roofing to improve energy conservation in your home.', 'home'),
(16, 'Plant Shade Trees', 'Plant trees around your home to provide natural cooling and reduce air conditioning costs.', 'home'),
(17, 'Use Thermal Curtains', 'Hang thermal curtains to insulate windows and reduce heat loss during winter.', 'home'),
(18, 'Switch to a Tankless Water Heater', 'Install a tankless water heater for more efficient water heating only when needed.', 'home'),
(19, 'Conduct an Energy Audit', 'Hire a professional to conduct an energy audit and identify ways to improve energy efficiency.', 'home'),
(20, 'Install Motion Sensors', 'Use motion sensors for lights to ensure they are only on when needed.', 'home'),
(21, 'Use Eco-Friendly Insulation', 'Upgrade insulation using eco-friendly materials for better thermal regulation.', 'home'),
(22, 'Upgrade to a High-Efficiency Boiler', 'Replace your old boiler with a high-efficiency one to reduce energy use.', 'home'),
(23, 'Automate Home with IoT', 'Integrate IoT devices to monitor and control home energy use remotely.', 'home'),
(24, 'Install Reflective Wall Paint', 'Apply reflective paint on external walls to decrease heat absorption.', 'home'),
(25, 'Optimize Kitchen Appliance Use', 'Use energy-saving practices when cooking, like using a microwave instead of an oven.', 'home'),
(26, 'Weatherproof Your Home', 'Weatherproof your home to protect against energy losses from seasonal changes.', 'home'),
(27, 'Use Energy Monitoring Systems', 'Install systems to monitor real-time energy usage and identify waste.', 'home'),
(28, 'Install Dimmer Switches', 'Use dimmer switches to adjust light levels and save electricity.', 'home'),
(29, 'Opt for Energy Star Products', 'Choose products with the Energy Star label for guaranteed energy efficiency.', 'home'),
(30, 'Switch to a Smart Thermostat', 'Upgrade to a smart thermostat for optimal heating and cooling based on real-time needs.', 'home'),
(31, 'Use Public Transportation', 'Utilize public transit systems to reduce the number of individual car trips, lowering carbon emissions.', 'Transportation'),
(32, 'Carpool Regularly', 'Share rides with others to decrease the number of vehicles on the road, reducing per capita emissions.', 'Transportation'),
(33, 'Switch to Electric Vehicles', 'Replace gasoline-powered vehicles with electric ones to reduce greenhouse gas emissions.', 'Transportation'),
(34, 'Maintain Your Vehicle', 'Regular maintenance ensures vehicles run more efficiently and emit fewer pollutants.', 'Transportation'),
(35, 'Reduce Air Travel', 'Opt for alternative transportation methods over flying when possible, as air travel has a high carbon footprint.', 'Transportation'),
(36, 'Use Bicycles for Short Trips', 'Bike for short distances to decrease reliance on fossil fuels and improve health.', 'Transportation'),
(37, 'Optimize Route Planning', 'Use GPS technology to plan the most efficient routes, minimizing unnecessary mileage and fuel usage.', 'Transportation'),
(38, 'Telecommute When Possible', 'Work from home to eliminate commuting emissions altogether on telecommuting days.', 'Transportation'),
(39, 'Invest in Hybrid Vehicles', 'Use hybrid cars that combine electric and gasoline engines for better fuel efficiency and lower emissions.', 'Transportation'),
(40, 'Practice Eco-Driving Techniques', 'Drive smoothly with steady speeds to reduce fuel consumption and emissions.', 'Transportation'),
(41, 'Increase Car Load Efficiency', 'Utilize the full passenger and cargo capacity of your vehicle to reduce the emissions per person or per item.', 'Transportation'),
(42, 'Support Walkable Community Initiatives', 'Advocate for and support local planning efforts that promote walkable communities to reduce the need for vehicular travel.', 'Transportation'),
(43, 'Participate in Vehicle Buyback Programs', 'Engage in programs that encourage the retirement of older, less efficient vehicles.', 'Transportation'),
(44, 'Use Renewable Biofuels', 'Fuel vehicles with biofuels like biodiesel or ethanol when possible to reduce dependency on fossil fuels.', 'Transportation'),
(45, 'Promote Car Sharing Services', 'Support and use car-sharing programs to reduce the total number of vehicles on the road.', 'Transportation'),
(46, 'Adopt High-Speed Rail', 'Support and use high-speed rail services where available, as they offer a more efficient and lower-emission alternative to air or car travel.', 'Transportation'),
(47, 'Encourage Flexible Working Hours', 'Promote flexible working hours to spread peak traffic loads and reduce congestion.', 'Transportation'),
(48, 'Install Advanced Traffic Management Systems', 'Support the installation of smart traffic systems that reduce road congestion and idle times.', 'Transportation'),
(49, 'Promote Electric Public Transit', 'Advocate for and use electric buses and trains for public transit to lower urban emissions.', 'Transportation'),
(50, 'Upgrade Fleet Vehicles', 'Encourage businesses to upgrade their fleet vehicles to models with higher fuel efficiency or electric drivetrains.', 'Transportation'),
(51, 'Limit Vehicle Idling', 'Reduce idling time for vehicles to cut down on unnecessary fuel consumption and emissions.', 'Transportation'),
(52, 'Increase Tire Efficiency', 'Maintain proper tire pressure for optimal vehicle fuel efficiency and reduced emissions.', 'Transportation'),
(53, 'Invest in Sustainable Aviation', 'Support research and development in sustainable aviation technologies and practices.', 'Transportation'),
(54, 'Use Park-and-Ride Facilities', 'Utilize park-and-ride options to combine car travel with public transportation, reducing overall emissions.', 'Transportation'),
(55, 'Advocate for Improved Fuel Standards', 'Support policies that mandate higher fuel efficiency standards for vehicles.', 'Transportation'),
(56, 'Promote Sustainable Maritime Shipping', 'Advocate for cleaner shipping technologies and practices in the maritime industry.', 'Transportation'),
(57, 'Educate on Transportation Impact', 'Engage in and promote educational campaigns about the environmental impact of transportation choices.', 'Transportation'),
(58, 'Support Urban Car-Free Zones', 'Promote and participate in the establishment of car-free zones in city centers.', 'Transportation'),
(59, 'Utilize Energy-Efficient Tires', 'Switch to tires designed to improve fuel economy and lower rolling resistance.', 'Transportation'),
(60, 'Encourage Non-Motorized Transport', 'Promote walking and cycling as primary modes of transportation for urban commuting.', 'Transportation'),
(61, 'Implement Composting', 'Start composting at home to turn organic waste into valuable fertilizer, reducing landfill use.', 'waste'),
(62, 'Recycle Electronics', 'Properly dispose of and recycle electronics through certified e-waste recycling centers.', 'waste'),
(63, 'Use Reusable Bags', 'Use reusable shopping bags to reduce the consumption of disposable plastic bags.', 'waste'),
(64, 'Reduce Paper Use', 'Minimize the use of paper products by opting for digital alternatives whenever possible.', 'waste'),
(65, 'Recycle More', 'Increase recycling efforts by separating all recyclable materials from regular trash.', 'waste'),
(66, 'Avoid Single-Use Plastics', 'Reduce the use of single-use plastics by choosing reusable or biodegradable options.', 'waste'),
(67, 'Educate on Recycling Practices', 'Provide information and resources to educate the community about proper recycling practices.', 'waste'),
(68, 'Support Packaging Reforms', 'Advocate for and support changes in product packaging to reduce waste and improve recyclability.', 'waste'),
(69, 'Buy in Bulk', 'Purchase food and other products in bulk to reduce the packaging waste associated with individual items.', 'waste'),
(70, 'Opt for Reusable Containers', 'Use reusable containers for storing food and other items to minimize disposable packaging use.', 'waste'),
(71, 'Organize Community Clean-ups', 'Participate in or organize community clean-up events to reduce local waste and promote environmental stewardship.', 'waste'),
(72, 'Donate Unused Items', 'Donate items that are no longer needed to charitable organizations instead of discarding them.', 'Waste'),
(73, 'Implement Upcycling Projects', 'Encourage upcycling of old or unused items to create new products, reducing waste.', 'waste'),
(74, 'Promote Sustainable Product Design', 'Support initiatives that promote the design of products that are easier to repair, reuse, and recycle.', 'waste'),
(75, 'Reduce Food Waste', 'Adopt practices that reduce food waste, such as meal planning and proper food storage.', 'waste'),
(76, 'Support Waste-to-Energy Technologies', 'Advocate for the adoption of waste-to-energy solutions that convert waste materials into energy.', 'waste'),
(77, 'Develop Zero-Waste Policies', 'Work towards implementing zero-waste policies at home and within local communities.', 'waste'),
(78, 'Promote Biodegradable Alternatives', 'Encourage the use of biodegradable and compostable products to replace non-degradable ones.', 'waste'),
(79, 'Enhance Local Recycling Facilities', 'Support improvements and expansions of local recycling facilities to handle more types of recyclables.', 'waste'),
(80, 'Regulate Hazardous Waste', 'Support stricter regulations and practices for the disposal of hazardous waste.', 'waste'),
(81, 'Participate in Recycling Programs', 'Engage in local recycling programs and initiatives to ensure materials are properly recycled.', 'waste'),
(82, 'Reduce Construction Waste', 'Implement strategies to reduce waste in construction and demolition processes.', 'waste'),
(83, 'Adopt Reusable Water Bottles', 'Switch to using reusable water bottles instead of disposable ones to minimize plastic waste.', 'waste'),
(84, 'Implement Stricter Litter Laws', 'Support the enforcement of stricter littering laws to reduce waste in public spaces.', 'waste'),
(85, 'Promote Circular Economy Practices', 'Advocate for circular economy practices that keep resources in use for as long as possible.', 'waste'),
(86, 'Create Waste Reduction Educational Programs', 'Develop educational programs focused on reducing waste and promoting recycling.', 'waste'),
(87, 'Optimize Waste Collection Schedules', 'Work to optimize waste collection schedules to increase efficiency and reduce emissions from waste collection vehicles.', 'waste'),
(88, 'Promote Community Composting', 'Encourage community-wide composting programs that reduce organic waste and produce compost for local use.', 'waste'),
(89, 'Support Reusable Textile Initiatives', 'Support and use reusable textile programs to reduce textile waste.', 'waste'),
(90, 'Implement Reusable Dishware in Cafeterias', 'Encourage cafeterias and food services to use reusable dishware to minimize disposable waste.', 'waste');

-- --------------------------------------------------------

--
-- Table structure for table `answers`
--

CREATE TABLE `answers` (
  `answer_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `question_id` int(11) DEFAULT NULL,
  `option_index` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `answers`
--

INSERT INTO `answers` (`answer_id`, `user_id`, `question_id`, `option_index`) VALUES
(1, 1, 1, 4),
(2, 1, 2, 1),
(3, 1, 3, 2),
(4, 1, 4, 2),
(5, 1, 5, 3),
(6, 1, 6, 1),
(7, 1, 7, 1),
(8, 1, 8, 1),
(9, 1, 9, 1),
(10, 1, 10, 2),
(11, 1, 11, 2),
(12, 1, 12, 1),
(13, 1, 13, 1),
(14, 1, 14, 1),
(15, 1, 15, 2),
(16, 1, 16, 3),
(17, 1, 17, 2),
(35, 3, 1, 4),
(36, 3, 2, 1),
(37, 3, 3, 5),
(38, 3, 4, 2),
(39, 3, 5, 2),
(40, 3, 6, 3),
(41, 3, 7, 2),
(42, 3, 8, 4),
(43, 3, 9, 4),
(44, 3, 10, 1),
(45, 3, 11, 3),
(46, 3, 12, 2),
(47, 3, 13, 2),
(48, 3, 14, 2),
(49, 3, 15, 5),
(50, 3, 16, 3),
(51, 3, 17, 2),
(52, 4, 1, 2),
(53, 4, 2, 1),
(54, 4, 3, 2),
(55, 4, 4, 2),
(56, 4, 5, 3),
(57, 4, 6, 1),
(58, 4, 7, 2),
(59, 4, 8, 2),
(60, 4, 9, 1),
(61, 4, 10, 1),
(62, 4, 11, 1),
(63, 4, 12, 2),
(64, 4, 13, 3),
(65, 4, 14, 2),
(66, 4, 15, 1),
(67, 4, 16, 5),
(68, 4, 17, 5),
(121, 2, 1, 4),
(122, 2, 2, 1),
(123, 2, 3, 5),
(124, 2, 4, 2),
(125, 2, 5, 4),
(126, 2, 6, 3),
(127, 2, 7, 2),
(128, 2, 8, 4),
(129, 2, 9, 1),
(130, 2, 10, 2),
(131, 2, 11, 1),
(132, 2, 12, 2),
(133, 2, 13, 2),
(134, 2, 14, 2),
(135, 2, 15, 4),
(136, 2, 16, 2),
(137, 2, 17, 1),
(138, 5, 1, 4),
(139, 5, 2, 2),
(140, 5, 3, 1),
(141, 5, 4, 2),
(142, 5, 5, 4),
(143, 5, 6, 3),
(144, 5, 7, 1),
(145, 5, 8, 1),
(146, 5, 9, 2),
(147, 5, 10, 2),
(148, 5, 11, 2),
(149, 5, 12, 1),
(150, 5, 13, 1),
(151, 5, 14, 1),
(152, 5, 15, 5),
(153, 5, 16, 5),
(154, 5, 17, 2),
(155, 6, 1, 1),
(156, 6, 2, 1),
(157, 6, 3, 4),
(158, 6, 4, 3),
(159, 6, 5, 2),
(160, 6, 6, 2),
(161, 6, 7, 1),
(162, 6, 8, 4),
(163, 6, 9, 1),
(164, 6, 10, 3),
(165, 6, 11, 1),
(166, 6, 12, 3),
(167, 6, 13, 1),
(168, 6, 14, 2),
(169, 6, 15, 3),
(170, 6, 16, 1),
(171, 6, 17, 1),
(172, 7, 1, 3),
(173, 7, 2, 1),
(174, 7, 3, 2),
(175, 7, 4, 1),
(176, 7, 5, 3),
(177, 7, 6, 2),
(178, 7, 7, 3),
(179, 7, 8, 2),
(180, 7, 9, 3),
(181, 7, 10, 1),
(182, 7, 11, 3),
(183, 7, 12, 1),
(184, 7, 13, 1),
(185, 7, 14, 1),
(186, 7, 15, 1),
(187, 7, 16, 3),
(188, 7, 17, 1),
(189, 8, 1, 5),
(190, 8, 2, 1),
(191, 8, 3, 2),
(192, 8, 4, 3),
(193, 8, 5, 4),
(194, 8, 6, 3),
(195, 8, 7, 1),
(196, 8, 8, 3),
(197, 8, 9, 3),
(198, 8, 10, 3),
(199, 8, 11, 3),
(200, 8, 12, 3),
(201, 8, 13, 2),
(202, 8, 14, 1),
(203, 8, 15, 4),
(204, 8, 16, 2),
(205, 8, 17, 3),
(206, 9, 1, 5),
(207, 9, 2, 2),
(208, 9, 3, 3),
(209, 9, 4, 3),
(210, 9, 5, 4),
(211, 9, 6, 2),
(212, 9, 7, 2),
(213, 9, 8, 2),
(214, 9, 9, 2),
(215, 9, 10, 2),
(216, 9, 11, 1),
(217, 9, 12, 1),
(218, 9, 13, 2),
(219, 9, 14, 2),
(220, 9, 15, 4),
(221, 9, 16, 5),
(222, 9, 17, 1),
(223, 10, 1, 4),
(224, 10, 2, 2),
(225, 10, 3, 3),
(226, 10, 4, 2),
(227, 10, 5, 4),
(228, 10, 6, 2),
(229, 10, 7, 3),
(230, 10, 8, 4),
(231, 10, 9, 4),
(232, 10, 10, 3),
(233, 10, 11, 1),
(234, 10, 12, 3),
(235, 10, 13, 2),
(236, 10, 14, 2),
(237, 10, 15, 1),
(238, 10, 16, 5),
(239, 10, 17, 5),
(240, 11, 1, 1),
(241, 11, 2, 2),
(242, 11, 3, 1),
(243, 11, 4, 2),
(244, 11, 5, 2),
(245, 11, 6, 1),
(246, 11, 7, 3),
(247, 11, 8, 3),
(248, 11, 9, 2),
(249, 11, 10, 3),
(250, 11, 11, 1),
(251, 11, 12, 2),
(252, 11, 13, 1),
(253, 11, 14, 1),
(254, 11, 15, 1),
(255, 11, 16, 5),
(256, 11, 17, 2),
(257, 12, 1, 1),
(258, 12, 2, 2),
(259, 12, 3, 5),
(260, 12, 4, 3),
(261, 12, 5, 3),
(262, 12, 6, 2),
(263, 12, 7, 1),
(264, 12, 8, 1),
(265, 12, 9, 2),
(266, 12, 10, 1),
(267, 12, 11, 1),
(268, 12, 12, 1),
(269, 12, 13, 2),
(270, 12, 14, 2),
(271, 12, 15, 5),
(272, 12, 16, 5),
(273, 12, 17, 5),
(274, 13, 1, 4),
(275, 13, 2, 1),
(276, 13, 3, 2),
(277, 13, 4, 2),
(278, 13, 5, 2),
(279, 13, 6, 2),
(280, 13, 7, 3),
(281, 13, 8, 4),
(282, 13, 9, 2),
(283, 13, 10, 3),
(284, 13, 11, 2),
(285, 13, 12, 3),
(286, 13, 13, 2),
(287, 13, 14, 2),
(288, 13, 15, 4),
(289, 13, 16, 3),
(290, 13, 17, 1),
(291, 14, 1, 4),
(292, 14, 2, 2),
(293, 14, 3, 5),
(294, 14, 4, 2),
(295, 14, 5, 3),
(296, 14, 6, 1),
(297, 14, 7, 2),
(298, 14, 8, 4),
(299, 14, 9, 3),
(300, 14, 10, 2),
(301, 14, 11, 1),
(302, 14, 12, 1),
(303, 14, 13, 1),
(304, 14, 14, 1),
(305, 14, 15, 5),
(306, 14, 16, 1),
(307, 14, 17, 3),
(308, 15, 1, 5),
(309, 15, 2, 1),
(310, 15, 3, 5),
(311, 15, 4, 3),
(312, 15, 5, 3),
(313, 15, 6, 2),
(314, 15, 7, 1),
(315, 15, 8, 3),
(316, 15, 9, 2),
(317, 15, 10, 2),
(318, 15, 11, 2),
(319, 15, 12, 1),
(320, 15, 13, 2),
(321, 15, 14, 1),
(322, 15, 15, 2),
(323, 15, 16, 3),
(324, 15, 17, 4),
(325, 16, 1, 4),
(326, 16, 2, 2),
(327, 16, 3, 3),
(328, 16, 4, 1),
(329, 16, 5, 2),
(330, 16, 6, 1),
(331, 16, 7, 1),
(332, 16, 8, 4),
(333, 16, 9, 4),
(334, 16, 10, 3),
(335, 16, 11, 2),
(336, 16, 12, 3),
(337, 16, 13, 3),
(338, 16, 14, 1),
(339, 16, 15, 3),
(340, 16, 16, 1),
(341, 16, 17, 2),
(342, 17, 1, 4),
(343, 17, 2, 2),
(344, 17, 3, 3),
(345, 17, 4, 2),
(346, 17, 5, 2),
(347, 17, 6, 1),
(348, 17, 7, 2),
(349, 17, 8, 4),
(350, 17, 9, 3),
(351, 17, 10, 3),
(352, 17, 11, 3),
(353, 17, 12, 1),
(354, 17, 13, 2),
(355, 17, 14, 1),
(356, 17, 15, 4),
(357, 17, 16, 1),
(358, 17, 17, 3),
(359, 18, 1, 1),
(360, 18, 2, 1),
(361, 18, 3, 5),
(362, 18, 4, 3),
(363, 18, 5, 3),
(364, 18, 6, 3),
(365, 18, 7, 3),
(366, 18, 8, 1),
(367, 18, 9, 2),
(368, 18, 10, 1),
(369, 18, 11, 3),
(370, 18, 12, 1),
(371, 18, 13, 3),
(372, 18, 14, 2),
(373, 18, 15, 1),
(374, 18, 16, 1),
(375, 18, 17, 4),
(376, 19, 1, 1),
(377, 19, 2, 2),
(378, 19, 3, 2),
(379, 19, 4, 1),
(380, 19, 5, 4),
(381, 19, 6, 1),
(382, 19, 7, 2),
(383, 19, 8, 2),
(384, 19, 9, 3),
(385, 19, 10, 2),
(386, 19, 11, 3),
(387, 19, 12, 3),
(388, 19, 13, 4),
(389, 19, 14, 1),
(390, 19, 15, 5),
(391, 19, 16, 2),
(392, 19, 17, 2),
(393, 20, 1, 1),
(394, 20, 2, 2),
(395, 20, 3, 2),
(396, 20, 4, 3),
(397, 20, 5, 2),
(398, 20, 6, 2),
(399, 20, 7, 1),
(400, 20, 8, 1),
(401, 20, 9, 3),
(402, 20, 10, 2),
(403, 20, 11, 1),
(404, 20, 12, 1),
(405, 20, 13, 1),
(406, 20, 14, 1),
(407, 20, 15, 5),
(408, 20, 16, 2),
(409, 20, 17, 2),
(410, 21, 1, 3),
(411, 21, 2, 2),
(412, 21, 3, 1),
(413, 21, 4, 2),
(414, 21, 5, 1),
(415, 21, 6, 1),
(416, 21, 7, 3),
(417, 21, 8, 3),
(418, 21, 9, 1),
(419, 21, 10, 1),
(420, 21, 11, 1),
(421, 21, 12, 1),
(422, 21, 13, 1),
(423, 21, 14, 2),
(424, 21, 15, 5),
(425, 21, 16, 1),
(426, 21, 17, 5),
(665, 93, 1, 5),
(666, 93, 2, 2),
(667, 93, 3, 2),
(668, 93, 4, 2),
(669, 93, 5, 2),
(670, 93, 6, 2),
(671, 93, 7, 1),
(672, 93, 8, 4),
(673, 93, 9, 4),
(674, 93, 10, 1),
(675, 93, 11, 2),
(676, 93, 12, 3),
(677, 93, 13, 1),
(678, 93, 14, 1),
(679, 93, 15, 1),
(680, 93, 16, 1),
(681, 93, 17, 1),
(699, 22, 1, 3),
(700, 22, 2, 2),
(701, 22, 3, 2),
(702, 22, 4, 2),
(703, 22, 5, 3),
(704, 22, 6, 2),
(705, 22, 7, 3),
(706, 22, 8, 3),
(707, 22, 9, 2),
(708, 22, 10, 3),
(709, 22, 11, 3),
(710, 22, 12, 1),
(711, 22, 13, 3),
(712, 22, 14, 1),
(713, 22, 15, 5),
(714, 22, 16, 3),
(715, 22, 17, 1);

-- --------------------------------------------------------

--
-- Table structure for table `options`
--

CREATE TABLE `options` (
  `option_id` int(11) NOT NULL,
  `question_id` int(11) DEFAULT NULL,
  `option_index` int(11) NOT NULL,
  `option_text` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `options`
--

INSERT INTO `options` (`option_id`, `question_id`, `option_index`, `option_text`) VALUES
(1, 1, 1, '1'),
(2, 1, 2, '2'),
(3, 1, 3, '3'),
(4, 1, 4, '4'),
(5, 1, 5, 'More than 4'),
(6, 2, 1, 'Renewable Energy'),
(7, 2, 2, 'Electricity'),
(8, 3, 1, 'Below 100kWh'),
(9, 3, 2, '100-199kWh'),
(10, 3, 3, '200-399kWh'),
(11, 3, 4, '400-599kWh'),
(12, 3, 5, 'More than 600kWh'),
(13, 4, 1, 'Shower with no heater'),
(14, 4, 2, 'Shower with heater (warm water)'),
(15, 4, 3, 'Shower with heater (hot water)'),
(16, 5, 1, 'Daily'),
(17, 5, 2, 'Once a week'),
(18, 5, 3, 'Twice a week'),
(19, 5, 4, 'Once a month'),
(20, 6, 1, '<1kg'),
(21, 6, 2, '1.01kg-5kg'),
(22, 6, 3, '>5kg'),
(23, 7, 1, 'Supermarket'),
(24, 7, 2, 'Online'),
(25, 7, 3, 'Local market/pasar'),
(26, 8, 1, 'Below RM200'),
(27, 8, 2, 'RM200-RM399'),
(28, 8, 3, 'RM400-RM600'),
(29, 8, 4, 'More than RM600'),
(30, 9, 1, 'Never'),
(31, 9, 2, 'Rarely'),
(32, 9, 3, 'Often'),
(33, 9, 4, 'Always'),
(34, 10, 1, 'No'),
(35, 10, 2, 'Yes'),
(36, 10, 3, 'Depends'),
(37, 11, 1, 'No'),
(38, 11, 2, 'Yes'),
(39, 11, 3, 'Depends'),
(40, 12, 1, 'No'),
(41, 12, 2, 'Yes'),
(42, 12, 3, 'Depends'),
(43, 13, 1, 'Never'),
(44, 13, 2, 'Rarely'),
(45, 13, 3, 'Often'),
(46, 13, 4, 'Always'),
(47, 14, 1, 'No'),
(48, 14, 2, 'Yes'),
(49, 15, 1, '<200km'),
(50, 15, 2, '201km-400km'),
(51, 15, 3, '401km-600km'),
(52, 15, 4, '601km-800km'),
(53, 15, 5, '>800km'),
(54, 16, 1, 'Walk'),
(55, 16, 2, 'Bus'),
(56, 16, 3, 'Bike'),
(57, 16, 4, 'Personal vehicle'),
(58, 16, 5, 'Other'),
(59, 17, 1, 'Walk'),
(60, 17, 2, 'Bus'),
(61, 17, 3, 'Bike'),
(62, 17, 4, 'Personal vehicle'),
(63, 17, 5, 'Other');

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
(6, 'How much waste you throw per week? (In kg)', 'food'),
(7, 'Where do you usually purchase groceries?', 'food'),
(8, 'How much do you usually spend on groceries weekly?', 'food'),
(9, 'How frequently do you eat at a restaurant on a weekly basis?', 'food'),
(10, 'Do you pack the leftover food in a restaurant when leaving?', 'food'),
(11, 'If the food that you have prepared is not finished, will you keep the leftover?', 'food'),
(12, 'Will you try your best to finish the food on your plate?', 'food'),
(13, 'How frequently do you bring your bag whenever you plan to buy something', 'food'),
(14, 'Do you or your family member own a Hybrid or electronic car?', 'transportation'),
(15, 'How many fuel consumption on weekly basis?', 'transportation'),
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
  `carbon_footprint` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `username`, `password`, `carbon_footprint`) VALUES
(1, '1', '1', '$2y$10$lgYUZmAOuuY93q7GXcadleNuiPaXOgbGhYjn/XHoRz76Xuxgsvl.C', 0),
(2, 'user2', 'user2', 'user2', 0),
(3, 'user3@example.com', 'user3', '', 0),
(4, 'user4@example.com', 'user4', '', 0),
(5, 'user5@example.com', 'user5', '', 0),
(6, 'user6@example.com', 'user6', '', 0),
(7, 'user7@example.com', 'user7', '', 0),
(8, 'user8@example.com', 'user8', '', 0),
(9, 'user9@example.com', 'user9', '', 0),
(10, 'user10@example.com', 'user10', '', 0),
(11, 'user11@example.com', 'user11', '', 0),
(12, 'user12@example.com', 'user12', '', 0),
(13, 'user13@example.com', 'user13', '', 0),
(14, 'user14@example.com', 'user14', '', 0),
(15, 'user15@example.com', 'user15', '', 0),
(16, '2', '2', '$2y$10$HIiwXMgfjL4QVe5JW6sEZ..AQLuwwGXNel4F/MPf/eW0DfcInMk5C', 0),
(17, 'user17@example.com', 'user17', '', 0),
(18, 'user18@example.com', 'user18', '', 0),
(19, 'user19@example.com', 'user19', '', 0),
(20, 'user20@example.com', 'user20', '', 0),
(21, 'user21@example.com', 'user21', '', 0),
(22, 'yang', 'yang', '$2y$10$DQzJSs3cCptw6t9ZV/p7yOoGmLvHtNh0ZSYRRSp1szDB.4Dw2mnrW', 0),
(23, 'yiusadf@gmail.com', 'yeuanyang', '$2y$10$rAkfw.4FWrhI3uW20PaYeuotH3TlLfv4Z8GCwu6PL3iwAUVsH2s46', 0),
(24, 'yang@gmail.com', 'ahyang', '$2y$10$rFXzFXFW4y6d36IvLfpttuykL965qa4uK72QJ1dyBJG3nRBfPMBTK', 0),
(27, '0', '3', '$2y$10$zrcLHRUR6e5Txum6WsSGNOs/xaadCIBoHo6U0K4hKInP3S1Hq8Xt.', 0),
(28, '0', '4', '$2y$10$dG0F2IdhRfE8O3jMpAeVsOQZCMwhnDz90sr4BO6LuFyufcj3N4h1m', 0),
(29, '0', '5', '$2y$10$l1igW4ZEVWCsKxlpsHQ3He4FxOZgc/qT5dQsCcSDqDGoldZXEZ9n.', 0),
(30, '0', '6', '$2y$10$kP7buPCv6OOKD6MEplkJ7eA72yuOTaM0advCDuXLoDC/e.OnqC7uO', 0),
(31, '0', '7', '$2y$10$k3AUn/1xjh9YZjiz2Z3rteN7hjj8BBSRre5iJeDYWC/w8.mB43/Iq', 0),
(32, '0', '8', '$2y$10$5viRbQ6rN2G/QqFTuTkqJ.jlzzl/91OkYUiB2PIBOxuI9o36TJ7wi', 0),
(33, '0', '9', '$2y$10$LVkdmhe7lE1QzAFadFUFY.2dMD52Wcoa7P1DUp0OVfYyKPJFeETlq', 0),
(34, '0', '10', '$2y$10$SCXsSymj0xm6zlsnr1cMYeXkT1nZJA59c.SuSR1u67PMTvYUQt4CK', 0),
(35, '0', '11', '$2y$10$PaVQbtjS5mWwqwqM3eqOTeFU50Tq9cZE7/.wPUP0ifKfGt55/BJl2', 0),
(36, '0', '12', '$2y$10$U5i0E6dioqL2CC.M9BXtUuOks9irADj1ziEv6FpM0h6nUr6H5xgCq', 0),
(37, '0', '13', '$2y$10$EYk9ShgHhLkIPHYjx51nGeA8tsh9sPY/PAb8xmw4.YTzAcxTrIU2G', 0),
(38, '0', '14', '$2y$10$scMiXjcZl.1FiHx342lFieA0YNnhoa2t3LeTMNO3Pnv7G924iGlGW', 0),
(39, '0', '15', '$2y$10$pMrYJ6qMp1Zayj0vyJcQbOtrdwGVhnr0zI5TG1.FjCoWu4rL6hQMi', 0),
(40, '0', '16', '$2y$10$qPiBhUz1c/7zJfj2IFKqRujttM9xOl4WuyCIb17pgzEW2rg3TwxGK', 0),
(41, '0', '17', '$2y$10$Xl.s9F3ni2eUp5QvbI9JdOwbUTZzCsC0uQODx34y7Nhr5IveMEcVi', 0),
(93, 'taham655@gmail.com', 'taham655', '$2y$10$hW52pskwDiVh18YsU7GS5uRcdY5S78eVXxyj2coWQ8SrrYQdWi0Ui', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `actions`
--
ALTER TABLE `actions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `answers`
--
ALTER TABLE `answers`
  ADD PRIMARY KEY (`answer_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `question_id` (`question_id`),
  ADD KEY `option_id` (`option_index`);

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
  MODIFY `answer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=716;

--
-- AUTO_INCREMENT for table `options`
--
ALTER TABLE `options`
  MODIFY `option_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT for table `questions`
--
ALTER TABLE `questions`
  MODIFY `question_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=94;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `answers`
--
ALTER TABLE `answers`
  ADD CONSTRAINT `answers_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `answers_ibfk_2` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`),
  ADD CONSTRAINT `answers_ibfk_3` FOREIGN KEY (`option_index`) REFERENCES `options` (`option_id`);

--
-- Constraints for table `options`
--
ALTER TABLE `options`
  ADD CONSTRAINT `options_ibfk_1` FOREIGN KEY (`question_id`) REFERENCES `questions` (`question_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
