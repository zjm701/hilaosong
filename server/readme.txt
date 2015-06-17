# hilaosong
hi lao song
Plan A: there are two projects , one is client, two is server

test url of web service
http://localhost:8082/delivery/rest/helloservice/sayHelloString?name=gg
http://localhost:8082/delivery/rest/helloservice/sayHelloJson?name=gg
http://localhost:8082/delivery/rest/helloservice/sayHelloXml?name=gg


##add oracle jdbc driver maven support
run 'mvn:clean' at first time, to install oracle jdbc jar to maven repository

#add backend api
1, get all support delivery cities:
request: http://localhost:8080/delivery/rest/getcities
request: http://182.92.195.126:8080/delivery/rest/getcities
response: [{"id":8221,"cityId":"110000","city":"北京市","provinceId":"110000"},{"id":9831,"cityId":"130100","city":"石家庄市","provinceId":"130000"}]

2, 菜品分类
request: http://localhost:8080/delivery/rest/getcategories
request: http://182.92.195.126:8080/delivery/rest/getcategories
response: [{"dishTypeId":"003","dishTypeName":"荤菜","parentId":"0","isRequired":"0"},{"dishTypeId":"004","dishTypeName":"素菜","parentId":"0","isRequired":"0"}]

3, 菜品分类 -> 菜品
request: http://localhost:8080/delivery/rest/getdishes?catId=003
request: http://182.92.195.126:8080/delivery/rest/getdishes?catId=003

4. 用户登录验证
post  request： http://localhost:8080/delivery/rest/login
test page： http://localhost:8082/delivery/views/test/login.jsp
http://182.92.195.126:8080/delivery/rest/login
http://182.92.195.126:8082/delivery/views/test/login.jsp

5. 用户信息
request: http://localhost:8080/delivery/rest/getuserinfo?userId=23675
request: http://182.92.195.126:8080/delivery/rest/getuserinfo?userId=23675

20. 历史订单