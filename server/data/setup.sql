DROP TABLE User;
CREATE TABLE User (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  password varchar(50) NOT NULL,
  role varchar(20) NOT NULL,
  readed tinyint(1) DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE UserAddress;
CREATE TABLE UserAddress (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  userId int(11) NOT NULL,
  name varchar(50) NOT NULL,
  address varchar(1024) NOT NULL DEFAULT '',
  phone varchar(50) NOT NULL,
  isDefault tinyint(1) DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE Category;
CREATE TABLE Category (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(50) NOT NULL,
  description varchar(512),
  required tinyint(1) DEFAULT 0,
  canHalf tinyint(1) DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE Product;
CREATE TABLE Product (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  CategoryId int(11) NOT NULL,
  name varchar(50) NOT NULL,
  description varchar(512),
  imageurl varchar(100) NOT NULL,
  price double DEFAULT 0,
  isPackage tinyint(1) DEFAULT 0,
  isAvalible tinyint(1) DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE ProductPackage;
CREATE TABLE ProductPackage (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  ProductId int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE ProductPackageDetail;
CREATE TABLE ProductPackageDetail (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  ProductPackageId int(11) NOT NULL,
  ProductId int(11) NOT NULL,
  number int(11) NOT NULL,
  hasHalf tinyint(1) DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE ShoppingCar;
CREATE TABLE ShoppingCar (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  UserId int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE ShoppingCarDetail;
CREATE TABLE ShoppingCarDetail (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  ShoppingCarId int(11) NOT NULL,
  ProductId int(11) NOT NULL,
  number int(11) NOT NULL,
  hasHalf tinyint(1) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE OrderInfo;
CREATE TABLE OrderInfo (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  UIId varchar(25) NOT NULL,
  UserId int(11) NOT NULL,
  totalPrice double NOT NULL,
  promoPrice double NOT NULL,
  status tinyint(1) NOT NULL,
  comment varchar(500) DEFAULT '',
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE OrderDetail;
CREATE TABLE OrderDetail (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  OrderInfoId int(11) NOT NULL,
  ProductId int(11) NOT NULL,
  count int(11) NOT NULL,
  hasHalf tinyint(1) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

commit;