# Host: localhost  (Version: 5.1.40-community)
# Date: 2016-09-21 17:43:25
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
  `parentid` int(10) DEFAULT NULL COMMENT '父节点ID',
  `orders` tinyint(2) DEFAULT NULL COMMENT '节点排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
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
  `name` varchar(20) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色表';

#
# Data for table "roles"
#


#
# Structure for table "rolespowers"
#

CREATE TABLE `rolespowers` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `rid` int(10) DEFAULT NULL COMMENT '角色ID',
  `nid` int(10) DEFAULT NULL COMMENT '节点ID',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COMMENT='角色权限表';

#
# Data for table "rolespowers"
#


#
# Structure for table "users"
#

CREATE TABLE `users` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '姓名',
  `pwd` varchar(30) NOT NULL DEFAULT '' COMMENT '密码',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

#
# Data for table "users"
#


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

