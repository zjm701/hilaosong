# hilaosong
hi lao song
Plan A: there are two projects , one is client, two is server

Test Environment: http://182.92.195.126:8080/

test url of web service
local: http://localhost:8082/delivery/rest/helloservice/sayHelloString?name=gg
local: http://localhost:8082/delivery/rest/helloservice/sayHelloJson?name=gg
local: http://localhost:8082/delivery/rest/helloservice/sayHelloXml?name=gg

##add oracle jdbc driver maven support
run 'mvn:clean' at first time, to install oracle jdbc jar to maven repository

#add backend api
1, get all support delivery cities:
http://localhost:8080/delivery/rest/getcities
response: [{"id":8221,"cityId":"110000","city":"北京市","provinceId":"110000"},{"id":9831,"cityId":"130100","city":"石家庄市","provinceId":"130000"}]
#返回支持外卖的城市列表，id主键， cityId城市id， provinceId省份直辖市id


2, 菜品分类
http://localhost:8080/delivery/rest/getcategories
response: [{"dishTypeId":"003","dishTypeName":"荤菜","parentId":"0","isRequired":"0"},{"dishTypeId":"004","dishTypeName":"素菜","parentId":"0","isRequired":"0"}]
#返回菜品分类列表， dishTypeId菜品分类id， dishTypeName菜品分类名， isRequired是否 为必选菜品分类

3, 菜品分类 -> 菜品
http://localhost:8080/delivery/rest/getdishes?catId=003
http://localhost:8080/delivery/rest/getdishes?catId=003&pageIndex=2：分页显示，显示第二页， 一页显示9个，如果不提供pageIndex默认为第一页
http://localhost:8080/delivery/rest/getdishes?catId=003&cityId=130100：显示石家庄的菜品信息，如果cityId字段不提供，那么就用默认北京市
response： [{"dishId":"30043_020111","storeDishId":"30043","storeDishName":"牛领肉","unitPrice":"60.0000","bigImageAddr":"http://172.16.254.91:9080/TzxRifImage/images/09010040_1.png","type":"1","halfStoreDishId":"30044","halfPrice":"30.0000"}]
#返回菜品列表， dishId菜品主键（菜品id+店id）， storeDishId菜品id（同一个菜品在不同的店是一样的）， storeDishName菜品名，unitPrice菜品单价， bigImageAddr菜品大图（地址不能用）， type菜品/套餐（1普通菜品，2套餐）， halfStoreDishId半份菜品对应的菜品id（有半份的菜品才显示）， halfPrice半份菜品的价格（有半份才显示）

4, 用户登录验证
http://localhost:8080/delivery/rest/login
test page： http://localhost:8082/delivery/views/test/login.jsp

5, 用户信息
http://localhost:8080/delivery/rest/getuserinfo?userId=23675

6, 套餐
http://localhost:8080/delivery/rest/getpacks?catId=012
response: [{"dishId":"82193_0201","storeDishId":"82193","storeDishName":"微特享套餐B","unitPrice":"488.0000","type":"2"},{"dishId":"82192_0201","storeDishId":"82192","storeDishName":"微特享套餐A","unitPrice":"288.0000","type":"2"}]
#返回套餐列表, dishId菜品/套餐主键， storeDishId菜品/套餐id，storeDishName菜品/套餐名， unitPrice价格， type菜品/套餐（1普通菜品，2套餐）

7, 套餐详情
http://localhost:8080/delivery/rest/getpackdishes?dishId=82192_0201
response: [{"packId":"82192_0201","dishId":"41006_0201","dishNumber":1,"innerId":"group10001_0201","innerNumber":1,"innerName":"素菜","dishName":"(B)木耳(半)","unitPrice":"10.0000"},{"packId":"82192_0201","dishId":"41024_0201","dishNumber":1,"innerId":"group10001_0201","innerNumber":1,"innerName":"素菜","dishName":"(B)茼蒿(半)","unitPrice":"9.0000"}]
#返回套餐详情， packId套餐id（套餐的dishId），dishId菜品的主键， dishNumber套餐内此菜品的数量，innerId套餐内组别id， innerName套餐内组别名， dishName菜品名， unitPrice套餐内此菜品原价 

8, 菜品详情
http://localhost:8080/delivery/rest/getdishdetail?dishId=30043_020111
response： {"dishId":"30043_020111","dishName":"牛领肉","unitPrice":"60.0000","description":"牛脖子部位的肉，肉质细嫩，牛肉味浓郁","isRequired":"0","dishUnit":"份","dishWeight":"0.00","dishShareType":"1","isRecommend":"0","type":"1","bigImageAddr":"http://172.16.254.91:9080/TzxRifImage/images/09010040_1.png","mediumImageAddr":"http://172.16.254.91:9080/TzxRifImage/images/09010040_2.png","storeDishId":"30043"}
#返回菜品详情， dishId菜品主键， dishName菜品名， unitPrice菜品单价， description菜品描述， isRequired是否必选， dishUnit菜品单位， dishWeight菜品分量， dishShareType菜品半份属性（1一份，2半份， 3没有特指）， isRecommend是否推荐， type是否是套餐， bigImageAddress菜品图片， storeDishId菜品id


20, 历史订单
http://localhost:8080/delivery/rest/gethistoryorders?userId=0100000053215312
http://localhost:8080/delivery/rest/gethistoryorders?userId=0100000053215312&pageIndex=2

21, 订单详情
http://localhost:8080/delivery/rest/getorderinfo?orderId=153376
http://localhost:8080/delivery/rest/getorderinfo?orderId=ZBJ182012060900024
