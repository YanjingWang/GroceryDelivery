/*
SQLyog Community v13.1.9 (64 bit)
MySQL - 5.7.19 : Database - delivery
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`delivery` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `delivery`;

/*Table structure for table `customer` */
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `account_id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `phonenumber` varchar(50) NOT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO `user`(`account_id`,`password`,`firstname`,`lastname`,`phonenumber`) VALUES
('aapple2','123456','Alana','Apple','222-222-2222'),
('ccherry4','234567','carlos','cherry','444-444-4444'),
('ffig8','345678','Finneas','Fig','888-888-8888'),
('ggrape17','456789','Gillian','Grape','999-999-9999');
DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `customer_id` varchar(50) NOT NULL,
  `rating` int(50) NOT NULL,
  `credit` int(50) NOT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `customer` */

insert  into `customer`(`customer_id`,`rating`,`credit`) values 
('aapple2',4,100),
('ccherry4',5,300);


/*Table structure for table `drone` */

DROP TABLE IF EXISTS `drone`;

CREATE TABLE `drone` (
  `number` int(50) NOT NULL AUTO_INCREMENT COMMENT 'number',
  `drone_id` varchar(50) NOT NULL COMMENT 'Drone Unique ID',
  `total_capacity` int(20) NOT NULL COMMENT 'totalCapacity of drone',
  `max_deliveries` int(20) NOT NULL COMMENT 'max deliveries need to maintance',
  `trips_completed` int(20) DEFAULT '0' COMMENT 'completed deliveries',
  `remain_Capacity` int(20) DEFAULT '0' COMMENT 'current remaining capacity',
  `pilot_id` varchar(50) DEFAULT NULL COMMENT 'id of assigned pilot',
  `store_name` varchar(50) NOT NULL COMMENT 'drone belongs to',
   PRIMARY KEY (`number`)
  -- KEY `account_id_pilot` (`account_id_pilot`),
  -- KEY `store_name` (`store_name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `drone` */

insert  into `drone`(`number`,`drone_id`,`total_capacity`,`max_deliveries`,`trips_completed`,`remain_Capacity`,`pilot_id`,`store_name`) values 
(1,'1',40,1,0,40,NULL,'kroger'),
(2,'1',40,3,0,40,NULL,'publix'),
(3,'2',20,3,0,20,NULL,'kroger'),
(4,'2',30,4,0,30,NULL,'publix'),
(5,'3',25,2,0,25,NULL,'kroger'),
(6,'1',40,1,0,40,NULL,'costco');

/*Table structure for table `item` */

DROP TABLE IF EXISTS `item`;

CREATE TABLE `item` (
  `number` int(50) NOT NULL AUTO_INCREMENT,
  `item_name` varchar(50) NOT NULL,
  `unit_weight` int(50) NOT NULL,
  `store_name` varchar(50) NOT NULL,
   PRIMARY KEY (`number`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `item` */

insert  into `item`(`number`,`item_name`,`unit_weight`,`store_name`) values 
(1,'pot_roast',5,'kroger'),
(2,'cheesecake',4,'kroger'),
(3,'cheesecake',8,'publix'),
(4,'apple',2,'publix'),
(5,'apple',3,'costco'),
(6,'cheese',1,'costco');

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `no` int(50) NOT NULL AUTO_INCREMENT COMMENT 'number',
  `store_name` varchar(50) NOT NULL COMMENT 'order from store',
  `order_id` varchar(50) NOT NULL COMMENT 'Order ID',
  `drone_id` varchar(50) NOT NULL COMMENT 'assigned drone ID',
  `customer_id` varchar(50) NOT NULL COMMENT 'order by customer ID',
  PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`no`,`store_name`,`order_id`,`drone_id`,`customer_id`) values 
(1,'kroger','purchaseA','1','aapple2'),
(2,'kroger','purchaseB','1','aapple2'),
(3,'kroger','purchaseD','2','ccherry4');

/*Table structure for table `pilot` */

DROP TABLE IF EXISTS `pilot`;

CREATE TABLE `pilot` (
  `pilot_id` varchar(50) NOT NULL COMMENT 'Unique account',
  `tax_id` varchar(50) NOT NULL COMMENT 'unique SSN',
  `license_id` varchar(50) NOT NULL COMMENT 'unique licenseID',
  `experience` int(50) NOT NULL COMMENT 'completed trips',
  `store_name` varchar(50) DEFAULT NULL COMMENT 'assign a drone from store',
  `drone` int(50) DEFAULT NULL COMMENT 'assign a drone from store',
  PRIMARY KEY (`pilot_id`),
  KEY `tax_id` (`tax_id`,`license_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `pilot` */

insert  into `pilot`(`pilot_id`,`tax_id`,`license_id`,`experience`,`store_name`,`drone`) values 
('ffig8','890-12-3456','panam_10',33,NULL,NULL),
('ggrape17','234-56-7890','twa_21',31,NULL,NULL);

/*Table structure for table `requested_item` */

DROP TABLE IF EXISTS `requested_item`;

CREATE TABLE `requested_item` (
  `no` int(50) NOT NULL AUTO_INCREMENT COMMENT 'auto increment',
  `item_name` varchar(50) NOT NULL COMMENT 'request item name',
  `store_name` varchar(50) NOT NULL COMMENT 'store name',
  `order_id` varchar(50) NOT NULL COMMENT 'order ID',
  `unit_price` int(50) NOT NULL COMMENT 'item unit price',
  `quantity` INT NOT NULL,
  `totalprice` INT NOT NULL,
  `totalweight` INT NOT NULL,
  `weight` int(50) NOT NULL COMMENT 'item unit weight, may not be needed',
   PRIMARY KEY (`no`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `requested_item` */

insert  into `requested_item`(`no`,`item_name`,`store_name`,`order_id`,`unit_price`,`quantity`,`totalprice`,`totalweight`,`weight`) values 
(1,'pot_roast','kroger','purchaseA',3,9,27,15,10),
(2,'pot_roast','kroger','purchaseB',4,4,16,20,5),
(3,'cheesecake','publix','purchaseaA',3,9,27,15,10), -- not sure 
(4,'cheesecake','kroger','purchaseD',1,2,2,4,10); -- not sure

/*Table structure for table `store` */

DROP TABLE IF EXISTS `store`;

CREATE TABLE `store` (
  `store_name` varchar(50) NOT NULL COMMENT 'Unique Store Name',
  `revenue` int(50) NOT NULL COMMENT 'initial revenue',
  PRIMARY KEY (`store_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `store` */

insert  into `store`(`store_name`,`revenue`) values 
('costco',31000),
('kroger',33000),
('publix',33000);

/*Table structure for table `store_manager` */

DROP TABLE IF EXISTS `store_manager`;

CREATE TABLE `store_manager` (
  `manager_id` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `store_name` varchar(50) NOT NULL,
  PRIMARY KEY (`manager_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `store_manager` */

insert  into `store_manager`(`manager_id`,`password`,`store_name`) values 
('kroger_manager','1','kroger'),
('publix_manager','2','publix');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
