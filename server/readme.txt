# hilaosong
hi lao song
Plan A: there are two projects , one is client, two is server

Test Environment: http://182.92.195.126:8080/
loginID = userId = user_entity_id = customerId

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
#request： {"username":"13585947701","password":"123456"} （密码现在是明文）
#返回用户验证结果  
#成功response：   {"respInfo":{"msgName":"terminalUserLoginResp","resp_time":"2015-06-16 09:53:41","result_code":"300","result_desc":"success"},
				"loginID":"23675",
				"ssoid":"0100000064540217",
				"customerKey":"0100000064540217",
				"tabCount":"0",
				"mailCount":"0",
				"friendsCount":"0",
				"photoCount":"0"}
loginID就是userId
#失败response：   {"respInfo":{"msgName":"terminalUserLoginResp","resp_time":"2015-06-16 10:00:31","result_code":"311","result_desc":"user  defind"}}

5, 用户信息
http://localhost:8080/delivery/rest/getuserinfo?userId=23675
response：     {"respInfo":{"msgName":"getUserInfoResp","resp_time":"2015-06-24 10:32:29","result_code":"300","result_desc":"success"},
			"user":{"user_entity_id":23675,"hits":"","nickname":"大宝","sex":0,"mobile":"13585947701",
    				"usertype":0,"intro":"","area_code":0,"createtime":"2015-06-16 09:51:09","follow_num":1,
    				"followed_num":0,"report_num":0,"fav_num":0,"at_num":0,"waiter":"无","userDiscount":"",
    				"storeId":"","mealtimes":"0","individualNeeds":"","expoints":"0","userLevel":"1星客户",
    				"receivemsg":0,"address":"null","postCode":"null","hi_laobi":0,"isFriend":0,"taste":""}
    		}
#返回用户信息, user用户所有信息user_entity_id就是userId

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

9, 切换城市，以便获取不同城市的菜品
http://localhost:8080/delivery/rest/switchcity?cityId=130100

10， 下单菜品（正式生成订单之前，保持到session）
http://localhost:8080/delivery/rest/user/adddish?dishId=30043_020111
必须login（先登录， 才可以做后续的操作）， 
如果有阅读过订餐须知，就直接显示下单的结果(同一菜品添加2次)：{"address":{},"expenses":{},"packs":[],"dishes":[{"dishId":"30043_020111","dishNumber":2}]}
如果没有阅读过订餐须知，那么需要阅读， 前台处理： {"showNotice":"true","directURL":"/user/adddish?dishId\u003d30043_020111\u0026fromNotice\u003dtrue"}

11， 下单套餐（正式生成订单之前，保持到session）
http://localhost:8080/delivery/rest/user/addpack?packId=82192_0201
同上

12/13, 更改临时订单（session中，下单的内容，正式生成订单之前）的菜品/套餐数量
http://localhost:8080/delivery/rest/user/changenumofdish?dishId=30043_020111&num=0 去除所选菜品
http://localhost:8080/delivery/rest/user/changenumofpack?packId=82192_0201&num=3 订购3份一样的所选套餐

14，baidu map api test only: http://localhost:8080/delivery/rest/testbaidu


20, 获取用户的DIY锅底列表，支持分页
http://localhost:8080/delivery/rest/getgds?userId=222243

21, 获取DIY锅底默认名
http://localhost:8080/delivery/rest/getgdname?userId=222243

22, 创建DIY锅底, POST方式
http://localhost:8080/delivery/rest/creategd
传入:{"guodiName":"大宝的3号锅底","dishId":"10011_020119","dishId2":"10016_020115"}

22, 更新DIY锅底, POST方式
http://localhost:8080/delivery/rest/updategd
传入:{"guodiId":1521, "guodiName":"大宝的4号锅底","dishId":"10011_020119","dishId2":"10016_020115"}

23, 删除DIY锅底
http://localhost:8080/delivery/rest/deletegd?id=1122

30, 最近的订单地址
http://localhost:8080/delivery/rest/getlatestaddress?userId=0200000045250449
response:  {"addressId":203113,"customerPhone":"15201007041","provinceId":"110000","cityId":"110000","detailAddress":"十店测试","village":"朝阳 十店 十店测试"}
#返回最近的订单地址

31, 支持外卖的门店列表
http://localhost:8082/delivery/rest/getstores0?cityId=110000
http://localhost:8080/delivery/rest/getstores0?cityId=110000&address=上海市人民大道
带address参数，则返回带distance的数据，并且列表按distance升序排列
不带address参数，则返回不带distance的数据，并且列表按storeId升序排列
[{"storeId":"020113","storeName":"翠微路店","storeAddress":"海淀区翠微路凯德MALL（原嘉茂购物中心）商场四层","storeTele":"01068218532,01068216579","storeCode":"BJ13","storeType":"2","provinceId":"110000","cityId":"110000","coordinate":"116.308843,39.91883","baiduIid":"1","deptType":"4","deptId":"020113","deptName":"北京十三店","distance":1073.529164594415205125118112735169340777},
 {"storeId":"020111","storeName":"石景山店","storeAddress":"石景山区石景山路乙18号万达广场c栋4楼(近1号线八宝山地铁站) ","storeTele":"01088689558,01088689559","storeCode":"BJ11","storeType":"2","provinceId":"110000","cityId":"110000","coordinate":"116.231073,39.911262","baiduIid":"1","deptType":"4","deptId":"020111","deptName":"北京十一店","distance":1075.856886647553187460427803271185192897}]

32, 支持上门自取的门店列表
http://localhost:8080/delivery/rest/getstores2?cityId=110000
[{"storeId":"020111","storeName":"石景山店","storeAddress":"石景山区石景山路乙18号万达广场c栋4楼(近1号线八宝山地铁站) ","storeTele":"01088689558,01088689559","storeCode":"BJ11","storeType":"2","provinceId":"110000","cityId":"110000","coordinate":"116.231073,39.911262","baiduIid":"1","deptType":"4","deptId":"020111","deptName":"北京十一店"},
 {"storeId":"020113","storeName":"翠微路店","storeAddress":"海淀区翠微路凯德MALL（原嘉茂购物中心）商场四层","storeTele":"01068218532,01068216579","storeCode":"BJ13","storeType":"2","provinceId":"110000","cityId":"110000","coordinate":"116.308843,39.91883","baiduIid":"1","deptType":"4","deptId":"020113","deptName":"北京十三店"}]

33, 计算外送费
http://localhost:8080/delivery/rest/calcdeliveryfee?storeId=020101&address=北京市朝阳区平房路12号
返回{"deliveryFee":"10"}

34, 下单
http://localhost:8080/delivery/rest/createorder
test page： http://localhost:8082/delivery/views/test/createorder.jsp 
#必须先登录
输入样例：	{"storeId":"020102",
			 "contactName":"Jimmy",
			 "contactPhone":"13585947701",
			 "participantNumber":"4",
			 "dinningTime":"2015-07-02 20:30:00",
			 "recieptDept":"***",
			 "custMemo":"***",
			 "payChannel":"0",
			 "potNumber":"1",
			 "orderType":"0",
			 "address":{"provinceId":"110000","cityId":"110105","detailAddress":"***"},
			 "expenses":{"waiterFee":"10","deliveryFee":"20","dishPrice":"88","totalPrice":"118"},
			 "packs":[ {"packId":"82192_0201",
			 			"packCount":"1",
			 			"dishes":[  {"dishId":"10001_0201"},
			 						{"dishId":"61008_0201"},
			 						{"dishId":"20014_0201"},
			 						{"dishId":"32014_0201"},
			 						{"dishId":"41006_0201"},
			 						{"dishId":"20026_0201"} ]
			 			} ],
			 "dishes":[ {"dishId":"20014_0201","dishNumber":"1"},
			 			{"dishId":"30012_0201","dishNumber":"2"},
			 			{"dishId":"41066_0201","dishNumber":"3"} ]
			 }

35, 历史订单
http://localhost:8080/delivery/rest/gethistoryorders?userId=0200000045250449
http://localhost:8080/delivery/rest/gethistoryorders?userId=0200000045250449&pageIndex=2分页显示，显示第二页， 一页显示9个，如果不提供pageIndex默认为第一页
response:  [{"serialId":"2013091805003","orderId":"WBJ162013091805003","customerId":"0100000053215312","storeId":"020116","storeName":"方庄店","contactName":"刘志江","contactPhone":"13167315255","participantNumber":7,"dinningTime":"2013-09-19 18:00:00","status":"7","orderType":"1","deliveryType":"0","custMemo":"请王龙飞为我们服务。谢谢。","createdDt":"2013-09-18 21:22:37","totalPrice":2273,"address":{},"expenses":{},"packs":[],"dishes":[]},
			{"serialId":"2013083103588","orderId":"WBJ162013083103588","customerId":"0100000053215312","storeId":"020116","storeName":"方庄店","contactName":"刘志江","contactPhone":"13167315255","participantNumber":7,"dinningTime":"2013-09-01 18:00:00","status":"2","orderType":"1","deliveryType":"0","custMemo":"找王龙飞服务 网订 L","createdDt":"2013-08-31 18:08:18","totalPrice":2273,"address":{},"expenses":{},"packs":[],"dishes":[]}]
#返回历史订单列表, serialId流水号，orderId订单号，customerId用户号（就是userId），storeId门店号，status：订单状态（0-待审核1-待打印2-待配菜3-已派送4-待收锅5-已收锅6-已删除7-作废8-待下发9-下发失败），orderType：订单类型：（0表示外送,1表示订座,2表示外带），deliveryType外送种类（0-外送 1-自助 2-豪华 3-上门自取）

36, 订单详情
http://localhost:8080/delivery/rest/getorderinfo?orderId=153376
http://localhost:8080/delivery/rest/getorderinfo?orderId=ZBJ182012060900024
response:  {"serialId":"2012060900024","orderId":"ZBJ182012060900024","customerId":"0100000051165276","storeId":"020119","storeName":"潮青汇海底捞","contactName":"系统升级测试","contactPhone":"186581011351","dinningTime":"2012-06-09 23:40:00","status":"7","potStatus":"0","orderType":"0","deliveryType":"0","createdDt":"2012-06-09 06:32:19",
			"address":{"addressId":199516,"cityId":"110000","regionId":"110101","detailAddress":"系统升级测试","village":"系统升级测试"},
			"expenses":{"expensesId":259773,"deliveryFee":"0","dishPrice":1120,"totalPrice":1120},
			"packs":[
				{"packId":"82124_0201","packName":"海底捞套餐E","packPrice":"878.0000","packCount":"1",
				 "dishes":[
				 	{"packId":"82124_0201","innerId":"1","dishId":"10001_0201","dishName":"鸳鸯火锅","unitPrice":"49.0000","dishNumber":1,"dishType":"1"},
				 	{"packId":"82124_0201","innerId":"2","dishId":"61008_0201","dishName":"麻酱","unitPrice":"5.0000","dishNumber":10,"dishType":"1"}]
				}],
			"dishes":[
				{"dishId":"20015_0201","dishName":"捞派毛肚","unitPrice":"36.0000","dishNumber":1,"dishType":"0"},
				{"dishId":"82061_0201","dishName":"专人服务","unitPrice":"99.0000","dishNumber":1,"dishType":"0"}]
			}
#返回订单详情, 支持多套餐，address地址信息，expenses金额信息，packs套餐列表{某个pack的dishes:套餐内的菜品列表}，dishes订单中的非套餐菜品列表

