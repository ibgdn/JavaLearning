-- MySQL
CREATE USER 'cart'@'%' IDENTIFIED BY 'cart';
CREATE DATABASE cart;
GRANT  ALL  PRIVILEGES  ON  cart.*  TO  'cart'@'%';


DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `price` float DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into product values(1,'黑',500);
insert into product values(2,'充',2500);
insert into product values(3,'皮',180);
insert into product values(4,'蜡',0.20);


create table user(
id int,
name varchar(50),
password varchar(50)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into user values(1,'tom','123');


create table order_(
  id int AUTO_INCREMENT,
  uid int,
  primary key(id)
);

create table orderitem(
  id int AUTO_INCREMENT,
  pid int,
  num int,
  oid int,
  primary key(id)
);