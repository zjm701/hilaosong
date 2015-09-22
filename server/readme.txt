# hilaosong
hi lao song
Plan A: there are two projects , one is client, two is server

Test Environment: http://182.92.195.126:8080/
loginID = userId = user_entity_id = customerId

test url of web service
local: http://localhost:8082/hilaosong/rest/helloservice/sayHelloString?name=gg
local: http://localhost:8082/hilaosong/rest/helloservice/sayHelloJson?name=gg
local: http://localhost:8082/hilaosong/rest/helloservice/sayHelloXml?name=gg

##add oracle jdbc driver maven support
run 'mvn:clean' at first time, to install oracle jdbc jar to maven repository

#add backend api

1, 用户登录验证
http://localhost:8080/hilaosong/rest/login
test page： http://localhost:8082/hilaosong/views/test/login.jsp
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

2, 用户信息
http://localhost:8080/hilaosong/rest/getuserinfo?loginId=23675
response：     {"respInfo":{"msgName":"getUserInfoResp","resp_time":"2015-06-24 10:32:29","result_code":"300","result_desc":"success"},
			"user":{"user_entity_id":23675,"hits":"","nickname":"大宝","sex":0,"mobile":"13585947701",
    				"usertype":0,"intro":"","area_code":0,"createtime":"2015-06-16 09:51:09","follow_num":1,
    				"followed_num":0,"report_num":0,"fav_num":0,"at_num":0,"waiter":"无","userDiscount":"",
    				"storeId":"","mealtimes":"0","individualNeeds":"","expoints":"0","userLevel":"1星客户",
    				"receivemsg":0,"address":"null","postCode":"null","hi_laobi":0,"isFriend":0,"taste":""}
    		}
#返回用户信息, user用户所有信息user_entity_id就是userId

3，获得当前登录用户
http://localhost:8080/hilaosong/rest/getcurrentuser
如果用户未登录或超时，返回空 []，否则返回{"nickname":"大宝","userId":"23675"}

9, get all support hilaosong cities:
http://localhost:8080/hilaosong/rest/getcities
response: [{"id":8221,"cityId":"110000","city":"北京市","provinceId":"110000"},{"id":9831,"cityId":"130100","city":"石家庄市","provinceId":"130000"}]
#返回支持外卖的城市列表，id主键， cityId城市id， provinceId省份直辖市id

10, 获得所选城市的默认片区门店
http://localhost:8080/hilaosong/rest/getareastore?cityId=110000

11, 菜品分类
http://localhost:8080/hilaosong/rest/getcategories?storeId=020111    (也可传片区店Id: 0201)
response: [{"dishTypeId":"003","dishTypeName":"荤菜","parentId":"0","isRequired":"0"},{"dishTypeId":"004","dishTypeName":"素菜","parentId":"0","isRequired":"0"}]
#返回菜品分类列表， dishTypeId菜品分类id， dishTypeName菜品分类名， isRequired是否 为必选菜品分类

12, 菜品分类 -> 菜品的分页信息
http://localhost:8080/hilaosong/rest/cntdishes?storeId=020111&catId=003
{"totalRowsCount":42, "totalPagesCount":5}

13, 菜品分类 -> 菜品
http://localhost:8080/hilaosong/rest/getdishes?storeId=020111&catId=003
http://localhost:8080/hilaosong/rest/getdishes?storeId=020111&catId=003&pageIndex=2
http://localhost:8080/hilaosong/rest/getdishes?storeId=020111&catId=003&pageIndex=2&pageSize=5
分页显示，显示第二页， 一页显示5个，如果不提供pageIndex默认为第一页，如果不提供pageSize默认为9
response： [{"dishId":"30043_020111","storeDishId":"30043","storeDishName":"牛领肉","unitPrice":"60.0000","bigImageAddr":"http://172.16.254.91:9080/TzxRifImage/images/09010040_1.png","type":"1","halfStoreDishId":"30044","halfPrice":"30.0000"}]
#返回菜品列表， dishId菜品主键（菜品id+店id）， storeDishId菜品id（同一个菜品在不同的店是一样的）， storeDishName菜品名，unitPrice菜品单价， bigImageAddr菜品大图（地址不能用）， type菜品/套餐（1普通菜品，2套餐）， halfStoreDishId半份菜品对应的菜品id（有半份的菜品才显示）， halfPrice半份菜品的价格（有半份才显示）

14, 菜品分类 -> 套餐的分页信息
http://localhost:8080/hilaosong/rest/cntpacks?storeId=020111&catId=012&pageSize=5
{"totalRowsCount":5, "totalPagesCount":1}

15, 菜品分类 -> 套餐
http://localhost:8080/hilaosong/rest/getpacks?storeId=020111&catId=012
http://localhost:8080/hilaosong/rest/getpacks?storeId=020111&catId=012&pageIndex=2：分页显示，显示第二页， 一页显示9个，如果不提供pageIndex默认为第一页
response: [{"dishId":"82193_0201","storeDishId":"82193","storeDishName":"微特享套餐B","unitPrice":"488.0000","type":"2"},{"dishId":"82192_0201","storeDishId":"82192","storeDishName":"微特享套餐A","unitPrice":"288.0000","type":"2"}]
#返回套餐列表, dishId菜品/套餐主键， storeDishId菜品/套餐id，storeDishName菜品/套餐名， unitPrice价格， type菜品/套餐（1普通菜品，2套餐）

16, 套餐详情
http://localhost:8080/hilaosong/rest/getpackdishes?dishId=82192_0201
response: [{"packId":"82192_0201","dishId":"41006_0201","dishNumber":1,"innerId":"group10001_0201","innerNumber":1,"innerName":"素菜","dishName":"(B)木耳(半)","unitPrice":"10.0000"},{"packId":"82192_0201","dishId":"41024_0201","dishNumber":1,"innerId":"group10001_0201","innerNumber":1,"innerName":"素菜","dishName":"(B)茼蒿(半)","unitPrice":"9.0000"}]
#返回套餐详情， packId套餐id（套餐的dishId），dishId菜品的主键， dishNumber套餐内此菜品的数量，innerId套餐内组别id， innerName套餐内组别名， dishName菜品名， unitPrice套餐内此菜品原价 

17, 菜品详情
http://localhost:8080/hilaosong/rest/getdishdetail?dishId=30043_020111
response： {"dishId":"30043_020111","dishName":"牛领肉","unitPrice":"60.0000","description":"牛脖子部位的肉，肉质细嫩，牛肉味浓郁","isRequired":"0","dishUnit":"份","dishWeight":"0.00","dishShareType":"1","isRecommend":"0","type":"1","bigImageAddr":"http://172.16.254.91:9080/TzxRifImage/images/09010040_1.png","mediumImageAddr":"http://172.16.254.91:9080/TzxRifImage/images/09010040_2.png","storeDishId":"30043"}
#返回菜品详情， dishId菜品主键， dishName菜品名， unitPrice菜品单价， description菜品描述， isRequired是否必选， dishUnit菜品单位， dishWeight菜品分量， dishShareType菜品半份属性（1一份，2半份， 3没有特指）， isRecommend是否推荐， type是否是套餐， bigImageAddress菜品图片， storeDishId菜品id

20， 下单菜品（正式生成订单之前，保持到session）
http://localhost:8080/hilaosong/rest/user/adddish?dishId=30043_020111
必须login（先登录， 才可以做后续的操作）， 
如果有阅读过订餐须知，就直接显示下单的结果(同一菜品添加2次)：{"address":{},"expenses":{},"packs":[],"dishes":[{"dishId":"30043_020111","dishNumber":2}]}
如果没有阅读过订餐须知，那么需要阅读， 前台处理： {"showNotice":"true","directURL":"/user/adddish?dishId\u003d30043_020111\u0026fromNotice\u003dtrue"}

21， 下单套餐（正式生成订单之前，保持到session）
http://localhost:8080/hilaosong/rest/user/addpack?packId=82192_0201
同上

22/23, 更改临时订单（session中，下单的内容，正式生成订单之前）的菜品/套餐数量
http://localhost:8080/hilaosong/rest/user/changenumofdish?dishId=30043_020111&num=0 去除所选菜品
http://localhost:8080/hilaosong/rest/user/changenumofpack?packId=82192_0201&num=3 订购3份一样的所选套餐

24，baidu map api test only: http://localhost:8080/hilaosong/rest/testbaidu

30, 获取用户的DIY锅底列表分页信息
http://localhost:8080/hilaosong/rest/cntgds?userId=23675
{"totalRowsCount":7, "totalPagesCount":1}

31, 获取用户的DIY锅底列表
http://localhost:8080/hilaosong/rest/getgds?userId=23675
http://localhost:8080/hilaosong/rest/getgds?userId=23675&&pageIndex=2

32, 获取DIY锅底默认名
http://localhost:8080/hilaosong/rest/getgdname?userId=222243

33, 创建DIY锅底, POST方式
http://localhost:8080/hilaosong/rest/creategd
传入:{"guodiName":"大宝的3号锅底","dishId":"10011_020119","dishId2":"10016_020115"}

34, 更新DIY锅底, POST方式
http://localhost:8080/hilaosong/rest/updategd
传入:{"guodiId":1521, "guodiName":"大宝的4号锅底","dishId":"10011_020119","dishId2":"10016_020115"}

35, 删除DIY锅底
http://localhost:8080/hilaosong/rest/deletegd?id=1122

40, 最近的订单地址
http://localhost:8080/hilaosong/rest/getlatestaddress?userId=0200000045250449
response:  {"addressId":203113,"customerPhone":"15201007041","provinceId":"110000","cityId":"110000","detailAddress":"十店测试","village":"朝阳 十店 十店测试"}
#返回最近的订单地址

41, 支持外卖的门店列表
http://localhost:8080/hilaosong/rest/getstores0?provinceId=110000&address=北京市朝阳区平房路12号
http://localhost:8080/hilaosong/rest/getstores0?cityId=320500&address=北京市朝阳区平房路12号
http://localhost:8080/hilaosong/rest/getstores0?provinceId=110000&cityId=110000&address=北京市朝阳区平房路12号
http://localhost:8080/hilaosong/rest/getstores0?provinceId=320000&cityId=320500&address=北京市朝阳区平房路12号
带address参数，则返回带distance的数据，并且列表按distance升序排列
不带address参数，则返回不带distance的数据，并且列表按storeId升序排列
[{"storeId":"020113","storeName":"翠微路店","storeAddress":"海淀区翠微路凯德MALL（原嘉茂购物中心）商场四层","storeTele":"01068218532,01068216579","storeCode":"BJ13","storeType":"2","provinceId":"110000","cityId":"110000","coordinate":"116.308843,39.91883","baiduIid":"1","deptType":"4","deptId":"020113","deptName":"北京十三店","distance":1073.529164594415205125118112735169340777},
 {"storeId":"020111","storeName":"石景山店","storeAddress":"石景山区石景山路乙18号万达广场c栋4楼(近1号线八宝山地铁站) ","storeTele":"01088689558,01088689559","storeCode":"BJ11","storeType":"2","provinceId":"110000","cityId":"110000","coordinate":"116.231073,39.911262","baiduIid":"1","deptType":"4","deptId":"020111","deptName":"北京十一店","distance":1075.856886647553187460427803271185192897}]

42, 支持上门自取的门店列表
http://localhost:8080/hilaosong/rest/getstores2?provinceId=110000
http://localhost:8080/hilaosong/rest/getstores2?cityId=320500
http://localhost:8080/hilaosong/rest/getstores2?provinceId=110000&cityId=110000
http://localhost:8080/hilaosong/rest/getstores2?provinceId=320000&cityId=320500
[{"storeId":"020111","storeName":"石景山店","storeAddress":"石景山区石景山路乙18号万达广场c栋4楼(近1号线八宝山地铁站) ","storeTele":"01088689558,01088689559","storeCode":"BJ11","storeType":"2","provinceId":"110000","cityId":"110000","coordinate":"116.231073,39.911262","baiduIid":"1","deptType":"4","deptId":"020111","deptName":"北京十一店"},
 {"storeId":"020113","storeName":"翠微路店","storeAddress":"海淀区翠微路凯德MALL（原嘉茂购物中心）商场四层","storeTele":"01068218532,01068216579","storeCode":"BJ13","storeType":"2","provinceId":"110000","cityId":"110000","coordinate":"116.308843,39.91883","baiduIid":"1","deptType":"4","deptId":"020113","deptName":"北京十三店"}]

43, 计算外送费
http://localhost:8080/hilaosong/rest/calcdeliveryfee?storeId=020101&address=北京市朝阳区平房路12号
返回{"deliveryFee":"10"}

44, 获取起送金额标准
http://localhost:8080/hilaosong/rest/getdeliverylimitmoney?storeId=020115
返回{"deliveryLimitMoney":"276"}

45, 获取起送所有限制
http://localhost:8080/hilaosong/rest/getdeliverylimit?storeId=020115
{"deliveryLimitMoney":"276","startDay":"1","endDay":"60","firstDayStartTime":"17:00:00","startTime":"16:00:00","endTime":"17:00:00","message":"","busyTimes":[{"start":"2015-01-08 01:15:00","end":"2015-01-08 03:30:00"}],"closeTimes":[{"start":"2015-01-21 09:45:00","end":"2015-01-24 00:00:00"}]}
50, 下单
http://localhost:8080/hilaosong/rest/createorder
test page： http://localhost:8082/hilaosong/views/test/createorder.jsp 
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

51, 历史订单的分页信息
http://localhost:8080/hilaosong/rest/cnthistoryorders?userId=0200000045250449
{"totalRowsCount":21797, "totalPagesCount":2422}

52, 历史订单
http://localhost:8080/hilaosong/rest/gethistoryorders?userId=0200000045250449
http://localhost:8080/hilaosong/rest/gethistoryorders?userId=0200000045250449&pageIndex=2分页显示，显示第二页， 一页显示9个，如果不提供pageIndex默认为第一页
response:  [{"serialId":"2013091805003","orderId":"WBJ162013091805003","customerId":"0100000053215312","storeId":"020116","storeName":"方庄店","contactName":"刘志江","contactPhone":"13167315255","participantNumber":7,"dinningTime":"2013-09-19 18:00:00","status":"7","orderType":"1","deliveryType":"0","custMemo":"请王龙飞为我们服务。谢谢。","createdDt":"2013-09-18 21:22:37","totalPrice":2273,"address":{},"expenses":{},"packs":[],"dishes":[]},
			{"serialId":"2013083103588","orderId":"WBJ162013083103588","customerId":"0100000053215312","storeId":"020116","storeName":"方庄店","contactName":"刘志江","contactPhone":"13167315255","participantNumber":7,"dinningTime":"2013-09-01 18:00:00","status":"2","orderType":"1","deliveryType":"0","custMemo":"找王龙飞服务 网订 L","createdDt":"2013-08-31 18:08:18","totalPrice":2273,"address":{},"expenses":{},"packs":[],"dishes":[]}]
#返回历史订单列表, serialId流水号，orderId订单号，customerId用户号（就是userId），storeId门店号，status：订单状态（0-待审核1-待打印2-待配菜3-已派送4-待收锅5-已收锅6-已删除7-作废8-待下发9-下发失败），orderType：订单类型：（0表示外送,1表示订座,2表示外带），deliveryType外送种类（0-外送 1-自助 2-豪华 3-上门自取）

53, 订单详情
http://localhost:8080/hilaosong/rest/getorderinfo?orderId=153376
http://localhost:8080/hilaosong/rest/getorderinfo?orderId=ZBJ182012060900024
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

60, 获得优惠劵详情，必须先登录
http://localhost:8080/hilaosong/rest/getcouponinfo?couponId=***

61, 使用优惠劵，必须先登录
http://localhost:8080/hilaosong/rest/usecoupon?orderId=***&couponId=***

70, 获取支付渠道
http://localhost:8080/hilaosong/rest/user/getpaychannel?orderId=215603&test=1
可省略orderId,默认用之前保存在session中的订单（即之前创建的订单）； test参数为测试用
返回样例：
获得信息成功:
{
     "respCode":"1000",
"respMsg":"查询成功！",                                                                                                                    
 "payChannelList":[
{
  "channelNo":"tenPay",
  "channelName":"caifutong"
}
   ]
}
如果请求失败（根据具体情况返回以下四种中的其中一种）:
{
  respode:”2000”，respsg:”暂无支付渠道！”}
}    
{
  respode:”1001”，respMsg:”查询出错！”}
}
{
  respode:”1002”，respMsg:”请求参数出错！”}
}
{
  respode:”1003”，respMsg:”查询异常！”}
}

71, 获取支付链接
http://localhost:8080/hilaosong/rest/user/getpayurl?channelNo=tenPay&orderId=215603&testPrice=1
参数必填：channelNo， orderId可省略，默认为之前创建的订单， testPrice为测试用参数，默认1分钱
返回样例：
获得信息成功:
{
	 "respCode":"1000",
	 "respMsg":"初始化成功",
	"wapPayRequestUrl":"https://wap.tenpay.com/cgi-bin/wappayv2.0/wappay_gate.cgi?token_id=b29e36dc018c73985037995914f72fd6"
}

如果请求失败（根据具体情况返回以下两种中的其中一种）:
{
  respode:”2000”，respsg:”暂无支付渠道！”}
}    
{
  respode:”1001”，respMsg:”初始化出错！”}
}
{
  respode:”1002”，respMsg:”请求参数出错！”}
}
{
  respode:”1003”，respMsg:”初始化异常！”}
}

目前除tenPay外，其他pay channel测试的返回数据都是2000支付订单初始化失败， 此接口调用的是海底捞的支付网关接口
tenPay的返回需前台额外处理，可参考orderPayWayWap.js, weixin.js
