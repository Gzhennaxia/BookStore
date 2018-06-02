/*
SQLyog Ultimate - MySQL GUI v8.2 
MySQL - 5.5.27 : Database - bookstore
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`bookstore` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `bookstore`;

/*Table structure for table `admin` */

DROP TABLE IF EXISTS `admin`;

CREATE TABLE `admin` (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`aid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Data for the table `admin` */

insert  into `admin`(`aid`,`username`,`password`) values (1,'admin','666666'),(2,'admin02','123'),(3,'admin03','123'),(4,'admin04','123'),(5,'admin05','123'),(6,'admin06','123'),(7,'zhen','111');

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `category` */

insert  into `category`(`cid`,`cname`) values (1,'传记'),(2,'科技'),(3,'小说'),(4,'校园文学'),(5,'文学'),(6,'言情'),(7,'社会'),(8,'历史');

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `oid` varchar(128) NOT NULL,
  `money` double(10,2) DEFAULT NULL,
  `recipients` varchar(32) DEFAULT NULL,
  `tel` varchar(16) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `ordertime` date DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`oid`),
  KEY `FK_order` (`uid`),
  CONSTRAINT `FK_order` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `order` */

insert  into `order`(`oid`,`money`,`recipients`,`tel`,`address`,`state`,`ordertime`,`uid`) values ('a886853d-33d6-4676-914b-10a897db64f1',107.20,'刘广','18629374709','安防监控了付接口反倒是深刻理解科技发达尽快发到空间',0,'2018-03-19',2);

/*Table structure for table `orderitem` */

DROP TABLE IF EXISTS `orderitem`;

CREATE TABLE `orderitem` (
  `itemid` int(11) NOT NULL AUTO_INCREMENT,
  `oid` varchar(128) DEFAULT NULL,
  `pid` varchar(32) DEFAULT NULL,
  `buynum` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  KEY `FK_orderitem_1` (`oid`),
  KEY `FK_orderitem_2` (`pid`),
  CONSTRAINT `FK_orderitem_1` FOREIGN KEY (`oid`) REFERENCES `order` (`oid`),
  CONSTRAINT `FK_orderitem_2` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `orderitem` */

insert  into `orderitem`(`itemid`,`oid`,`pid`,`buynum`) values (3,'a886853d-33d6-4676-914b-10a897db64f1','111',1),(4,'a886853d-33d6-4676-914b-10a897db64f1','455252325423',3);

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `pid` varchar(100) NOT NULL,
  `pname` varchar(200) DEFAULT NULL,
  `estoreprice` double(10,2) DEFAULT NULL,
  `markprice` double(10,2) DEFAULT NULL,
  `pnum` int(11) DEFAULT NULL,
  `cid` int(11) DEFAULT NULL,
  `imgurl` varchar(255) DEFAULT NULL,
  `desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `FK_product` (`cid`),
  CONSTRAINT `FK_product` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `product` */

insert  into `product`(`pid`,`pname`,`estoreprice`,`markprice`,`pnum`,`cid`,`imgurl`,`desc`) values ('111','了不起的盖茨比（我抹掉过去，只为了让爱重来！2018全新译本，诗意还原经典神韵）',23.00,26.00,210,1,'4c15a3b3-297a-4743-b11e-fd5301bf4238了不起的盖茨比.jpg','莱昂纳多主演同名电影原著小说。特别收录菲氏魔幻小说《像里茨饭店一样大的钻石》。豆瓣8.9分译者张炽恒翻译'),('455252325423','《时间简史》',28.00,34.30,6,1,'d3988955-d10b-4f0b-bbb9-0baef075e69a图解时间简史.jpg','在霍金的著作中，首先要提到的就是他在1988年出版的代表作《时间简史》，这是一部无可争议的宇宙学权威著作。此书中，霍金以浅显的语言解读了许多深奥的宇宙问题，如空间和时间的本质。 　　时间到底是什么？时间可逆转吗？可以穿越时间隧道去旅行吗？宇宙有没有边缘？宇宙起源于哪里，未来会怎样'),('9787030383549','微纳流控芯片实验室',101.10,128.00,231,2,'13cdddd5-6fc1-4b59-8dcc-39b70726032a微纳流控芯片实验室.jpg','本书是作者在2006和2008年撰写的两本微流控芯片著作的续篇, 全书三分之二以上的内容涉及作者研究团队2008年以后的工作，特别是在前沿分支取得的重要成果。 全书共分15章, 包括微流控芯片的各种基础技术和主要应用。'),('9787101128369','郑天挺西南联大日记',152.90,156.00,431,8,'0a66ef9b-9a32-4e98-b092-11894e1ba815郑天挺西南联大日记.jpg','比《无问西东》剧情更生动详尽的联大往事。西南联大总务长郑天挺亲笔记录和梅贻琦、陈寅恪、冯友兰、汤用彤、傅斯年、钱穆、林徽因等人的交往。是研究西南联大校史、近代学术史、教育史、文化史...'),('9787108042453','我们仨（新版）',19.60,23.00,4312,1,'84480140-5659-4377-8163-71a21f9abb86我们仨（新版）.jpg','《我们仨》分为两部分。第一部分中，杨绛以其一贯的慧心、独特的笔法，用梦境的形式讲述了最后几年中一家三口相依为命的情感体验。第二部分，以平实感人的文字记录了自1935年伉俪二人赴英国留学，并在牛津喜得爱女，直至1998年钱先生逝世63年间这个家庭鲜为人知的坎坷历程。'),('9787201086538','人间有戏',26.80,34.80,326,5,'3635fbf3-ff92-4d02-9293-333c743571a0人间有戏.jpg','本书所选的都是与戏曲有关的文章，是汪曾祺在做北京市京剧团编剧时，二十多年来与戏曲打交道的见闻与思考，每一篇的篇幅虽然短小，但是每一篇都透着理性、睿智和从容。内容涵盖“样板戏”的谈往、名人轶事、戏曲与文学的关系、习剧札记等等，这些谈戏文章同他的游记、民俗类散文一样，无不潇洒有致，颇有看头。'),('9787211076215','随风不逝·张国荣',68.40,91.20,20,1,'2958b473-5b9a-4cf4-877b-a40563d58b88随风不逝·张国荣.jpg','55.6万个温暖文字\r\n70余张正式授权相片，部分首次曝光\r\n遇见张国荣，遇见随风不逝的他\r\n茫然中，思忆\r\n我们随风而逝的15年（2003-2018）'),('9787218068305','大逃港',103.20,124.00,246,8,'954c7c56-5787-41c1-acfb-beb65033111e大逃港.jpg','全景揭秘40多年前深圳河外逃风云，百万人“大逃港”逼出“大开放”，改革先锋为民请命'),('9787220104305','民国了:另类又真实的辛亥革命，一本打破对辛亥革命刻板印象的作品',48.00,68.00,257,8,'95ef0625-27fc-4d0e-a051-8a3f10312c66民国了.jpg','另类又真实的辛亥革命，一本打破对辛亥革命刻板印象的作品，新增绿茶手绘插图、全新修订 '),('9787506365437','活着',24.60,28.00,12,3,'21e1037d-22e8-42e2-964d-588a3bfb435b活着.jpg','中国作家之一，他的作品成了当代中国的典范。世界华文“冰心文学奖”，入选香港《亚洲周刊》评选的“20世纪中文小说百年百强”等'),('9787532748495','神的孩子全跳舞（村上春树作品）',17.20,22.00,1324,3,'36703ee7-a9e9-4e7e-b7d4-9591022f82b4神的孩子全跳舞（村上春树作品）.jpg','本书是村上春树*的一部短篇小说集，2000年出版。共六篇。各篇都以1995年日本大阪、神户大地震为背景，描写经历巨变的人们对自己以往人生的重新认识。他们有的丢开过去的种种顾虑，终于与爱慕已久的女子共同生活，有的终于吐露了久积胸中的郁闷，获得了精神的自由；有的小人物在巨变面前发现了自己的力量，干出了惊天动地的壮举。本书体现了村上春树对人生的深层思考，被评价为是村上春树的转折期之作。'),('9787539983721','席绢言情最新季：十年',14.90,20.00,541,6,'2704f998-42b8-406e-bfbc-6ce3e9d2c5fb席绢言情最新季：十年.jpg','此书讲述了女主角赵子昀在十年后的一天灵魂终于回到了自己的身体里，但是发现这个自己那么不对劲，神情刻薄，虚荣自私，众叛亲离，就连交往十年的男友都同意和她分手。这究竟是怎么回事？随后发生的一件件奇怪的事件，让她逐渐发现了真相，并通过这一件件事和分手的学长又走到了一起，并用她的真情感动了学长，让他逐渐相信了爱情，并向她求婚。');

/*Table structure for table `shoppingcar` */

DROP TABLE IF EXISTS `shoppingcar`;

CREATE TABLE `shoppingcar` (
  `sid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  KEY `FK_shoppingcar` (`uid`),
  CONSTRAINT `FK_shoppingcar` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `shoppingcar` */

insert  into `shoppingcar`(`sid`,`uid`) values (1,2);

/*Table structure for table `shoppingitem` */

DROP TABLE IF EXISTS `shoppingitem`;

CREATE TABLE `shoppingitem` (
  `itemid` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) DEFAULT NULL,
  `pid` varchar(32) DEFAULT NULL,
  `snum` int(11) DEFAULT NULL,
  PRIMARY KEY (`itemid`),
  KEY `FK_shoppingitem` (`sid`),
  KEY `fk_shopping_item` (`pid`),
  CONSTRAINT `FK_shoppingitem` FOREIGN KEY (`sid`) REFERENCES `shoppingcar` (`sid`),
  CONSTRAINT `fk_shopping_item` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `shoppingitem` */

insert  into `shoppingitem`(`itemid`,`sid`,`pid`,`snum`) values (1,1,'111',11);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `uid` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `nickname` varchar(32) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `updatetime` date DEFAULT NULL,
  `state` int(11) DEFAULT '0',
  `activecode` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`uid`,`username`,`nickname`,`password`,`email`,`birthday`,`updatetime`,`state`,`activecode`) values (1,'123',NULL,NULL,NULL,NULL,NULL,0,NULL),(2,'123','给朕拿下','666666','931077056@qq.com','1997-10-12','2018-03-19',1,'ac07a7ce-8f91-4d68-a6b2-383620e87683'),(3,'2','给朕拿下','666666','931077056@qq.com','1997-10-26','2018-03-17',NULL,'45a73b4c-2f4d-44e4-b716-a73ffa0034dd'),(4,'黄','给朕拿下','666666','931077056@qq.com','1997-10-26','2018-03-18',0,'2f9c38da-119f-4e73-971f-bdc5e7c3a392'),(5,'黄桑','给朕拿下','666666','931077056@qq.com','1997-10-26','2018-03-18',0,'47ea136c-effa-493e-ae08-c3f2ec3d6c42'),(6,'刘广','naive','libohaoshuai','931077056@qq.com','1997-10-26','2018-03-18',0,'5edb9d6a-5021-475e-b02b-69c2de8eb890');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
