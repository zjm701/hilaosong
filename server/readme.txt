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
response: [{"id":8221,"cityId":"110000","city":"北京市","provinceId":"110000"},{"id":9831,"cityId":"130100","city":"石家庄市","provinceId":"130000"}]