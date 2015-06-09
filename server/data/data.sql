insert into Category (id, name, required) values (1, '套餐', 0);
insert into Category (id, name, required) values (2, '锅底', 1);
insert into Category (id, name, required) values (3, '小料', 1);
insert into Category (id, name, required) values (4, '专人服务', 0);
insert into Category (id, name, canHalf) values (5, '特色菜', 1);
insert into Category (id, name, canHalf) values (6, '荤菜', 1);
insert into Category (id, name, canHalf) values (7, '素菜', 1);
insert into Category (id, name, canHalf) values (8, '小吃', 1);
insert into Category (id, name, canHalf) values (9, '酒水', 0);

insert into Product (id, CategoryId, name, imageUrl, price, isPackage, isAvalible)values(1, 1, '2人套餐', 'static/images/test1.jpg', 100, 1, 1);
insert into Product (id, CategoryId, name, imageUrl, price, isPackage, isAvalible)values(2, 6, '牛肉', 'static/images/test1.jpg', 50, 0, 1);
insert into Product (id, CategoryId, name, imageUrl, price, isPackage, isAvalible)values(3, 6, '五花肉', 'static/images/test1.jpg', 30, 0, 1);
insert into Product (id, CategoryId, name, imageUrl, price, isPackage, isAvalible)values(4, 7, '生菜', 'static/images/test1.jpg', 10, 0, 1);

insert into ProductPackage (id, ProductId)values(1, 1);

insert into ProductPackageDetail(ProductPackageId, ProductId, number, hasHalf)values(1, 2, 1, 0);
insert into ProductPackageDetail(ProductPackageId, ProductId, number, hasHalf)values(1, 3, 2, 0);
insert into ProductPackageDetail(ProductPackageId, ProductId, number, hasHalf)values(1, 4, 2, 0);


insert into User (name, password, role) values ('admin@163.com', 'admin', 'ROLE_ADMIN');
insert into User (name, password, role) values ('user@163.com', 'user', 'ROLE_USER');