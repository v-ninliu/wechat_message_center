# wechat_message_center

This is a Web Service which provides APIs to manage WeChat messages saved in MongoDB.

## Project setup
Clone the project

in IntelliJ

import the project
File->New->Project from existing source

build
View->Tool windows->Maven Projects

Click package

This will build the application

## MongoDB setup

1.Create database:
use message_center

2.Create collection:
db.createCollection("messageRecord");

## Run the server
run the class:
MessageCenterApplication

This will start the server


## Making Tests

### Add a new record
POST:
http://localhost:8080/message

Body:

{
	"name" : "HotelBookingSuccess2018",
	"category": "OFFICIAL_ACCOUNT_TEMPLATE_MESSAGE",
    "type": "HOTEL_BOOKING_SUCCESS",
	"keywords":"first:HEADER_1,hotelName:HOTEL_NAME,roomName:ROOM_NAME,pay:HOTEL_PRICE",
	"placeholders":"#header2#:HEADER_2,#hotelName#:HOTEL_NAME,#roomName#:ROOM_NAME",
"message": { "toUser": "o7peR0ijJGmmDW719M89uRZfFhTg", "officialAccount": "OYORooms", "url":"oyorooms.com", "templateId":"1WuhB-snbuN-gGpxdQ3LvsxTaViA4UCURct4RIswfig", "data":[ { "name":"first", "value":"恭喜你预定成功", "color":"#FF0000" }, { "name":"hotelName", "value":"OYO1008酒店", "color":"#173177" }, { "name":"roomName", "value":"标准大床", "color":"#173177" }, { "name":"pay", "value":"199元", "color":"#173177" }, { "name":"date", "value":"2018年6月17日", "color":"#173177" }, { "name":"remark", "value":"\n订单号：hh52284f962100224544\n如有疑问，请咨询13912345678", "color":"#173177" } ]}
}

### Show all records
GET
http://localhost:8080/message

### Find a record by id
GET
http://localhost:8080/message/5b6039a2e29edf2a0c22171e

### Modify a record by id
PUT
http://localhost:8080/message/5b6039a2e29edf2a0c22171e

Body: [same as for create record]

### Delete a record by id
DELETE
http://localhost:8080/message/5b6039a2e29edf2a0c22171e

