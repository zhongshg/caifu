# Host: localhost  (Version: 5.1.40-community)
# Date: 2016-09-24 18:19:06
# Generator: MySQL-Front 5.3  (Build 4.233)

/*!40101 SET NAMES utf8 */;

#
# Structure for table "assets"
#

CREATE TABLE `assets` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL DEFAULT '0' COMMENT '用户id',
  `assets` float(16,2) DEFAULT '0.00' COMMENT '总资产',
  `balance` float(16,2) DEFAULT '0.00' COMMENT '可取现余额',
  `wealth` float(16,2) DEFAULT '0.00' COMMENT '财富点',
  `dr` tinyint(1) DEFAULT '0' COMMENT '数据标识',
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='财富资产表';

#
# Data for table "assets"
#


#
# Structure for table "assets_in"
#

CREATE TABLE `assets_in` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `oid` int(11) DEFAULT NULL COMMENT '订单ID',
  `amount` float DEFAULT '0' COMMENT '金额',
  `type` tinyint(2) DEFAULT '0' COMMENT '资产变动类型,1代表收入佣金，2代表收入员工福利，3代表打款收入',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `dr` tinyint(1) DEFAULT '0' COMMENT '数据标识',
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产收入表';

#
# Data for table "assets_in"
#


#
# Structure for table "assets_out"
#

CREATE TABLE `assets_out` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户ID',
  `oid` int(11) DEFAULT NULL COMMENT '订单ID',
  `amount` float DEFAULT '0' COMMENT '金额',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `dr` tinyint(1) DEFAULT '0' COMMENT '数据标识',
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  `type` tinyint(2) DEFAULT '0' COMMENT '资产变动类型,1代表支出佣金，2代表支出会员福利，3代表提现支出，4代表商品购入支出',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资产支出表';

#
# Data for table "assets_out"
#


#
# Structure for table "logininfo"
#

CREATE TABLE `logininfo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户姓名',
  `logintime` datetime DEFAULT NULL COMMENT '登录时间',
  `ip` varchar(50) DEFAULT NULL COMMENT '登录ip',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='登录日志表';

#
# Data for table "logininfo"
#


#
# Structure for table "nodes"
#

CREATE TABLE `nodes` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '节点名称',
  `url` varchar(100) DEFAULT NULL COMMENT '节点链接',
  `parentid` int(10) DEFAULT '-1' COMMENT '父节点ID',
  `orders` tinyint(2) DEFAULT '0' COMMENT '节点排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `nodelvl` tinyint(1) DEFAULT '0' COMMENT '节点级别',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='节点表';

#
# Data for table "nodes"
#


#
# Structure for table "order"
#

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `f_no` varchar(55) DEFAULT NULL COMMENT '订单号',
  `f_date` datetime DEFAULT NULL COMMENT '订单日期',
  `f_price` float(16,2) DEFAULT '0.00' COMMENT '单金额订含运费',
  `tsf_price` float(16,2) DEFAULT '0.00' COMMENT '快递费用',
  `tf_price` float(16,2) DEFAULT '0.00' COMMENT '应收金额合计',
  `sf_price` float(16,2) DEFAULT '0.00' COMMENT '实收金额合计',
  `cf_price` float(16,2) DEFAULT '0.00' COMMENT '折扣金额合计',
  `userId` varchar(255) DEFAULT NULL COMMENT '用户UID',
  `suserId` varchar(255) DEFAULT NULL COMMENT '用户名称',
  `ip` varchar(55) DEFAULT NULL COMMENT '订单人IP',
  `sts` tinyint(3) DEFAULT '0' COMMENT '订单状态：1未发货2已发货3确认收货4未评价5已评价6退货中7已退货',
  `state` tinyint(3) NOT NULL DEFAULT '1' COMMENT '交易状态：1未完成2已完成',
  `cate` tinyint(3) NOT NULL DEFAULT '1' COMMENT '类型：1产品2课程',
  `agree` tinyint(3) NOT NULL DEFAULT '1' COMMENT '讲师确认：1未授权2已授权',
  `type` tinyint(3) DEFAULT NULL COMMENT '订单类型',
  `ispay` tinyint(3) DEFAULT '1' COMMENT '支付状态：1未支付2已支付3货到付款',
  `f_name` varchar(55) DEFAULT NULL COMMENT '收货人名称',
  `f_address` varchar(55) DEFAULT NULL COMMENT '收货人地址',
  `f_mobile` varchar(55) DEFAULT NULL COMMENT '收货人电话',
  `f_tel` varchar(55) DEFAULT NULL COMMENT '收货人手机',
  `demons` varchar(255) DEFAULT NULL,
  `shipno` varchar(55) DEFAULT NULL COMMENT '邮编',
  `shipname` varchar(255) DEFAULT NULL,
  `shiptime` timestamp NULL DEFAULT NULL COMMENT '发货时间',
  `shiptype` tinyint(3) DEFAULT '2' COMMENT '是否发货1发货2未发货',
  `paytype` tinyint(3) DEFAULT '0' COMMENT '付款类型',
  `posttype` tinyint(3) DEFAULT '1',
  `s_name` varchar(55) DEFAULT NULL,
  `s_mobile` varchar(255) DEFAULT NULL,
  `percent` float(18,2) DEFAULT NULL COMMENT '订单积分',
  `ssinv` tinyint(3) DEFAULT NULL COMMENT '是否开具发票',
  `firstyj` float DEFAULT NULL,
  `secondyj` float DEFAULT NULL,
  `thirdyj` float DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL COMMENT '备注/留言',
  `out_trade_no` varchar(200) DEFAULT NULL,
  `transaction_id` varchar(200) DEFAULT NULL,
  `endduring` tinyint(4) DEFAULT NULL,
  `provience` varchar(50) DEFAULT '0',
  `city` varchar(50) DEFAULT '0',
  `area` varchar(50) DEFAULT '0',
  `confirmtimes` datetime DEFAULT NULL,
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  `dr` tinyint(1) DEFAULT '0' COMMENT '数据标识',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='订单';

#
# Data for table "order"
#


#
# Structure for table "powers"
#

CREATE TABLE `powers` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `parentid` int(10) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `orders` tinyint(2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;

#
# Data for table "powers"
#

INSERT INTO `powers` VALUES (1,'系统设置','',0,'',1),(2,'会员管理','',0,'',2),(3,'商品管理','',0,'',3),(4,'订单管理','',0,'',4),(5,'数据统计','',0,'',5),(7,'角色管理','manageRoles.jsp',1,'',1),(8,'密码修改','editpwd.jsp',1,'',2),(9,'会员管理','#',2,'',1),(10,'会员审批','#',2,'',2),(11,'会员服务中心','#',2,'',3),(12,'商品管理','#',3,'',1),(13,'订单查询','#',4,'',1),(14,'发货管理','#',4,'',2),(15,'数据统计','#',5,'',1),(16,'系统设置','#',999,'',1),(17,'会员管理','#',999,'',2),(18,'商品管理','#',999,'',3),(19,'订单管理','#',999,'',4),(20,'资金管理','#',999,'',5),(21,'数据统计','#',999,NULL,6),(22,'分类管理','#',999,'',1),(23,'会员服务中心','#',999,'',1);

#
# Structure for table "product"
#

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `procode` varchar(32) DEFAULT NULL COMMENT '商品编码',
  `suserid` varchar(55) DEFAULT NULL COMMENT '商家ID ',
  `susername` varchar(55) DEFAULT NULL COMMENT '商家名称',
  `liulan` int(11) NOT NULL DEFAULT '0' COMMENT '大保健',
  `gongneng` int(11) NOT NULL DEFAULT '0' COMMENT '功能',
  `renqun` int(11) NOT NULL DEFAULT '0' COMMENT '人群',
  `jixing` int(11) NOT NULL DEFAULT '0' COMMENT '剂型',
  `rid` int(11) NOT NULL DEFAULT '0' COMMENT '根节点',
  `pid` int(11) NOT NULL DEFAULT '0' COMMENT '父节点',
  `cid` int(11) NOT NULL DEFAULT '0' COMMENT '分类id',
  `webdescr` varchar(255) DEFAULT NULL COMMENT '网页描述',
  `webkeywords` varchar(500) NOT NULL DEFAULT ',1,1,1,1,1,1,1,1,1,1,1,1,1,1' COMMENT '网页关键字(产品属性)',
  `webtitle` varchar(255) DEFAULT NULL COMMENT '网页标题',
  `title` varchar(200) NOT NULL COMMENT '商品名称',
  `abbreviation` varchar(64) DEFAULT NULL COMMENT '商品简称',
  `keywords` varchar(255) DEFAULT NULL COMMENT '商品关键字',
  `descr` varchar(255) DEFAULT NULL COMMENT '产品描述',
  `measurId` int(11) NOT NULL DEFAULT '0' COMMENT '基本计量单位内码',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `yongtu` varchar(255) DEFAULT NULL COMMENT '用途',
  `issale` int(4) NOT NULL DEFAULT '2' COMMENT '是否代销',
  `brandId` int(11) DEFAULT '0' COMMENT '品牌ID',
  `isshelves` int(4) NOT NULL DEFAULT '2' COMMENT '是否上架',
  `viewimg` varchar(255) DEFAULT NULL COMMENT '图片',
  `supplier` varchar(64) DEFAULT NULL COMMENT '供应商',
  `photos` text COMMENT '图片群',
  `cate` int(4) DEFAULT '1',
  `photo` text COMMENT '首页图片',
  `price` float NOT NULL DEFAULT '0' COMMENT '促销价',
  `pricem` float NOT NULL DEFAULT '0' COMMENT '销售价',
  `content` longtext NOT NULL COMMENT '商品详细',
  `addtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `modtime` timestamp NULL DEFAULT '0000-00-00 00:00:00' COMMENT '修改时间',
  `shelves` datetime DEFAULT '2000-12-12 12:12:12' COMMENT '上架时间',
  `psts` tinyint(3) NOT NULL DEFAULT '0' COMMENT '是否暂停出售',
  `isnew` int(4) DEFAULT '0' COMMENT '首页位置',
  `isrem` int(4) DEFAULT '1' COMMENT '隐藏预留字段',
  `sales` int(11) NOT NULL DEFAULT '0' COMMENT '销量',
  `praice` int(11) NOT NULL DEFAULT '0' COMMENT '好评',
  `tags` varchar(255) DEFAULT NULL COMMENT '备用标签',
  `salenum` int(11) DEFAULT '0',
  `isshi` int(4) DEFAULT '0',
  `begintime` datetime DEFAULT '0000-00-00 00:00:00',
  `endtime` datetime DEFAULT '0000-00-00 00:00:00',
  `kuaidi` float(6,2) NOT NULL DEFAULT '0.00',
  `time_pid` varchar(200) CHARACTER SET armscii8 NOT NULL DEFAULT '0' COMMENT '产品标签时间戳',
  `wxid` varchar(50) DEFAULT NULL,
  `distributionmoney` float(6,2) DEFAULT NULL,
  `distributionfirstdiscount` float(6,2) DEFAULT NULL,
  `distributionseconddiscount` float(6,2) DEFAULT NULL,
  `distributionthirddiscount` float(6,2) DEFAULT NULL,
  `distributiontype` tinyint(1) DEFAULT '0',
  `propertys` varchar(150) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='商品表';

#
# Data for table "product"
#


#
# Structure for table "roles"
#

CREATE TABLE `roles` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

#
# Data for table "roles"
#

INSERT INTO `roles` VALUES (1,'管理员',''),(2,'ceshi','');

#
# Structure for table "rolespowers"
#

CREATE TABLE `rolespowers` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `rid` int(10) DEFAULT NULL,
  `pid` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=129 DEFAULT CHARSET=utf8;

#
# Data for table "rolespowers"
#

INSERT INTO `rolespowers` VALUES (1,1,1),(2,1,2),(3,1,3),(4,1,4),(5,1,5),(6,1,6),(7,1,7),(8,1,8),(9,1,9),(10,1,10),(11,1,11),(12,1,12),(13,1,13),(14,1,14),(15,1,15);

#
# Structure for table "ucode"
#

CREATE TABLE `ucode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ucode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=405 DEFAULT CHARSET=utf8 COMMENT='未分配会员编号表';

#
# Data for table "ucode"
#

INSERT INTO `ucode` VALUES (1,'93893'),(2,'77880'),(3,'28371'),(4,'83657'),(5,'81441'),(6,'11554'),(7,'12484'),(8,'50107'),(9,'96374'),(10,'44204'),(11,'44481'),(12,'68990'),(13,'44559'),(14,'42792'),(15,'31972'),(16,'93262'),(17,'52265'),(18,'76730'),(19,'20041'),(20,'53784'),(21,'74620'),(22,'46785'),(23,'74517'),(24,'54289'),(25,'99246'),(26,'93934'),(27,'93625'),(28,'97389'),(29,'73090'),(30,'49346'),(31,'95777'),(32,'95623'),(33,'16656'),(34,'91302'),(35,'11202'),(36,'43055'),(37,'39936'),(38,'33957'),(39,'35717'),(40,'26290'),(41,'14018'),(42,'20276'),(43,'10979'),(44,'26335'),(45,'33960'),(46,'87911'),(47,'34173'),(48,'29762'),(49,'37179'),(50,'88376'),(51,'54144'),(52,'22442'),(53,'48091'),(54,'68674'),(55,'15710'),(56,'77606'),(57,'12323'),(58,'21547'),(59,'78005'),(60,'77609'),(61,'10271'),(62,'64450'),(63,'61213'),(64,'39466'),(65,'94205'),(66,'81089'),(67,'34315'),(68,'95645'),(69,'33909'),(70,'18596'),(71,'66169'),(72,'27215'),(73,'27109'),(74,'43176'),(75,'96031'),(76,'65335'),(77,'65476'),(78,'14333'),(79,'42733'),(80,'54046'),(81,'31743'),(82,'94977'),(83,'94121'),(84,'45859'),(85,'91406'),(86,'38217'),(87,'39795'),(88,'99319'),(89,'62050'),(90,'43270'),(91,'23721'),(92,'62373'),(93,'45837'),(94,'19863'),(95,'62506'),(96,'13520'),(97,'66676'),(98,'14299'),(99,'52508'),(100,'78255'),(101,'81615'),(102,'195202'),(103,'116758'),(104,'507418'),(105,'721452'),(106,'189244'),(107,'402377'),(108,'202846'),(109,'913466'),(110,'479445'),(111,'303548'),(112,'344277'),(113,'679742'),(114,'843263'),(115,'337428'),(116,'316726'),(117,'781508'),(118,'914769'),(119,'374725'),(120,'707700'),(121,'382510'),(122,'905143'),(123,'124093'),(124,'243113'),(125,'603545'),(126,'416845'),(128,'625936'),(129,'228406'),(130,'804585'),(131,'543622'),(132,'230007'),(133,'773637'),(134,'122888'),(135,'834031'),(136,'731242'),(137,'266209'),(138,'822744'),(139,'958990'),(140,'415596'),(141,'771548'),(142,'647035'),(143,'300405'),(144,'179163'),(145,'690104'),(146,'822153'),(147,'611083'),(148,'728688'),(149,'232246'),(150,'250343'),(151,'255414'),(152,'703416'),(153,'230231'),(154,'912219'),(155,'637066'),(156,'670916'),(157,'597380'),(158,'387799'),(159,'411797'),(160,'182942'),(161,'614857'),(162,'461839'),(163,'103537'),(164,'603130'),(165,'417064'),(166,'131164'),(167,'400124'),(168,'908487'),(169,'499739'),(170,'954282'),(171,'862396'),(172,'891849'),(173,'910334'),(174,'682723'),(175,'763801'),(176,'357042'),(177,'737446'),(178,'747993'),(179,'715911'),(180,'610672'),(181,'703754'),(182,'486759'),(183,'401004'),(184,'652432'),(185,'535591'),(186,'475524'),(187,'831932'),(188,'664731'),(189,'975851'),(190,'856223'),(191,'201531'),(192,'683424'),(193,'677612'),(194,'957274'),(195,'998535'),(196,'350857'),(197,'450077'),(198,'581383'),(199,'908190'),(200,'932967'),(201,'289449'),(202,'754035'),(203,'8743968'),(204,'7346991'),(205,'8804087'),(206,'2392396'),(207,'7516483'),(208,'5977614'),(209,'9634843'),(210,'3531504'),(211,'3250430'),(212,'3589961'),(213,'3419995'),(214,'4005876'),(215,'9047990'),(216,'2598808'),(217,'4791355'),(218,'4946276'),(219,'1481588'),(220,'6726818'),(221,'8810271'),(222,'9373577'),(223,'1102470'),(224,'9294768'),(225,'8035399'),(226,'3057026'),(227,'2628244'),(228,'4843207'),(229,'5564931'),(230,'5603522'),(231,'1732810'),(232,'7145204'),(233,'2766027'),(234,'9347844'),(235,'9797244'),(236,'2718139'),(237,'5770830'),(238,'2718838'),(239,'3028701'),(240,'5289353'),(241,'8602912'),(242,'6107644'),(243,'1192154'),(244,'4070890'),(245,'5500371'),(246,'3764436'),(247,'5221003'),(248,'9387191'),(249,'5382777'),(250,'4653938'),(251,'6687010'),(252,'7120285'),(253,'8851446'),(254,'8405986'),(255,'2085047'),(256,'5047983'),(257,'6487301'),(258,'4739839'),(259,'7226748'),(260,'8066977'),(261,'9168465'),(262,'7010730'),(263,'2673104'),(264,'3228154'),(265,'6426346'),(266,'7409989'),(267,'9313950'),(268,'3012401'),(269,'8319014'),(270,'4445376'),(271,'7900154'),(272,'3888966'),(273,'4818632'),(274,'5443918'),(275,'6518252'),(276,'6630404'),(277,'9575935'),(278,'4937375'),(279,'4625179'),(280,'3262658'),(281,'9948828'),(282,'1424389'),(283,'4666298'),(284,'1641288'),(285,'3375147'),(286,'6647552'),(287,'5065231'),(288,'5484875'),(289,'4483137'),(291,'3328520'),(292,'4738699'),(293,'6338782'),(294,'1705652'),(295,'8102593'),(296,'6659494'),(297,'9470762'),(298,'3788289'),(299,'2997170'),(301,'1665218'),(302,'4237460'),(303,'4425885'),(304,'84161494'),(305,'63996387'),(306,'16016929'),(307,'19007931'),(308,'62882212'),(309,'27772042'),(310,'32133380'),(311,'37934797'),(312,'87719356'),(314,'35452517'),(315,'44539345'),(316,'65140219'),(317,'42986402'),(318,'62126901'),(319,'80154381'),(320,'58651911'),(321,'19168787'),(322,'92850943'),(323,'96879950'),(324,'28516687'),(325,'69919679'),(326,'27477704'),(327,'75325462'),(328,'37149430'),(329,'15351575'),(330,'91312631'),(331,'24281762'),(332,'28087655'),(333,'68658811'),(334,'32228226'),(335,'40211999'),(336,'72942161'),(337,'24348055'),(338,'35724588'),(339,'64302044'),(340,'21730932'),(341,'85798294'),(342,'97410105'),(343,'31593737'),(344,'38928906'),(345,'78811031'),(346,'41136923'),(347,'33965796'),(349,'34121756'),(350,'53220431'),(351,'51653453'),(352,'54788336'),(353,'30446862'),(354,'90179701'),(355,'35428480'),(356,'25370400'),(357,'70856238'),(358,'11239844'),(359,'88288803'),(360,'87989835'),(361,'52748152'),(362,'82969993'),(363,'36571814'),(364,'81683688'),(365,'48005209'),(366,'74820282'),(367,'90206091'),(368,'58290199'),(369,'70579413'),(370,'77014972'),(371,'90690285'),(372,'45100420'),(373,'60539517'),(374,'16097308'),(375,'30759128'),(376,'50829120'),(377,'81250792'),(378,'63366655'),(379,'74725774'),(380,'78083976'),(381,'84797770'),(382,'50880408'),(383,'37798880'),(384,'28923526'),(385,'77937204'),(386,'86085540'),(387,'18314303'),(388,'16333426'),(389,'79335814'),(390,'36669735'),(391,'53661705'),(392,'29645994'),(393,'31914706'),(394,'97192082'),(395,'18216565'),(396,'77142494'),(397,'51455979'),(398,'50264243'),(399,'97734200'),(400,'24644704'),(401,'86791347'),(402,'48597303'),(403,'22331692'),(404,'35655423');

#
# Structure for table "users"
#

CREATE TABLE `users` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '姓名',
  `pwd` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `code` varchar(20) DEFAULT NULL COMMENT '会员编号',
  `age` int(3) DEFAULT NULL COMMENT '年龄',
  `viplvl` int(11) DEFAULT '0' COMMENT '会员级别，0非会员，1普通，2银级，3金级，4钻级',
  `cardid` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `bankcard` varchar(20) DEFAULT NULL COMMENT '银行卡号',
  `phone` varchar(12) DEFAULT NULL COMMENT '手机号',
  `roleid` int(11) DEFAULT NULL COMMENT '角色ID',
  `parentid` int(11) DEFAULT NULL COMMENT '上级代理ID',
  `indate` date DEFAULT '0000-00-00' COMMENT '加入时间',
  `dr` int(1) DEFAULT '0' COMMENT '数据删除标识',
  `ts` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Data for table "users"
#

INSERT INTO `users` VALUES (1,'1','c4ca4238a0b923820dcc509a6f75849b','5590884',NULL,0,'1','1','1',1,1,'0000-00-00',0,'2016-09-23 17:10:37');

#
# Structure for table "wealth"
#

CREATE TABLE `wealth` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) DEFAULT NULL COMMENT '用户id',
  `parentid` int(11) DEFAULT NULL COMMENT '上级ID',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='财富架构表';

#
# Data for table "wealth"
#

