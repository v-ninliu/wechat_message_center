# wechat_message_center

This is a Web Service which provides APIs to manage WeChat messages saved in MongoDB.

In order to send correct message to users at run-time, we need to prepare, preview and save message in advance.

Messages could be hotel promotional message, booking confirmation message, payment success message, ect,...

An Application (Hotel Booking Web application for example) can retrieve these message at run-time, set values (such as hotel name, price...), and send message to the user.

In saved message record, we use keywords, placeholders to reserve places for the run-time values.

[translation]
这是一个Web service，它提供APIs来管理MongoDB中保存的微信消息。

为了在运行时向用户发送正确的消息，我们需要提前准备，预览和保存消息。

消息可以是酒店促销信息，预订确认消息，付款成功消息等，...

应用程序（例如酒店预订官网应用程序）可以在运行时取出这些消息，赋予值（例如酒店名称，价格...），并向用户发送消息。

在保存的消息记录中，我们使用关键字，占位符来保留运行时值的位置。

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

### Create a record
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

