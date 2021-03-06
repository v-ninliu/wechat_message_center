# WeChat Message Center

wechat_message_center is a Web Service which provides APIs to manage WeChat messages saved in MongoDB. 

It also provides APIs to compose or to send message with saved message as template, and with run-time values.   

In order to send correct message to users at run-time, we need to prepare, preview and save message in advance.

Messages could be hotel promotional message, booking confirmation message, payment success message, ect,...

An Application (Hotel Booking Web application for example) can retrieve these message at run-time, set values (such as hotel name, price...), and send message to the user.

In saved message record, we use keywords, placeholders to reserve places for the run-time values.


## [translation]

这是一个Web service，它提供APIs来管理MongoDB中保存的微信消息。

它还提供APIs用来发送消息，利用存好的消息并赋予真实的运行值。

为了在运行时向用户发送正确的消息，我们需要提前准备，预览和保存消息。

消息可以是酒店促销信息，预订确认消息，付款成功消息等，...

应用程序（例如酒店预订官网应用程序）可以在运行时取出这些消息，赋予值（例如酒店名称，价格...），并向用户发送消息。

在保存的消息记录中，我们使用关键字，占位符来保留运行时值的位置。

### Tools Used in this Project

1. Spring Boot
2. Maven
3. MongoDB
4. Node.js (UI)
5. React (UI)

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
http://localhost:8080/message/record

Body:

```json
{
   "name":"default-HOTEL_BOOKING_SUCCESS-OFFICIAL_ACCOUNT_TEMPLATE_MESSAGE",
   "category":"OFFICIAL_ACCOUNT_TEMPLATE_MESSAGE",
   "type":"HOTEL_BOOKING_SUCCESS",
   "keywords":"first===HEADER_1&&&hotelName===HOTEL_NAME&&&roomName===ROOM_NAME&&&pay===HOTEL_PRICE",
   "placeholders":"#BookingId#===BOOKING_ID&&&#Phone#===CUSTOMER_SERVICE_PHONE",
   "message":{  
      "toUser":"o7peR0ijJGmmDW719M89uRZfFhTg",
      "officialAccount":"OYORooms",
      "url":"oyorooms.com",
      "templateId":"1WuhB-snbuN-gGpxdQ3LvsxTaViA4UCURct4RIswfig",
      "data":[  
         {  
            "name":"first",
            "value":"恭喜你预定成功",
            "color":"#FF0000"
         },
         {  
            "name":"hotelName",
            "value":"OYO1008酒店",
            "color":"#173177"
         },
         {  
            "name":"roomName",
            "value":"标准大床",
            "color":"#173177"
         },
         {  
            "name":"pay",
            "value":"199元",
            "color":"#173177"
         },
         {  
            "name":"date",
            "value":"2018年6月17日",
            "color":"#173177"
         },
         {  
            "name":"remark",
            "value":"\n订单号：#BookingId#\n如有疑问，请咨询#Phone#",
            "color":"#173177"
         }
      ]
   }
}
```

where: name must be unique.

### Show all records
GET
http://localhost:8080/message/record

### Find a record by id
GET
http://localhost:8080/message/5b6039a2e29edf2a0c22171e

### Find a record by name
GET
http://localhost:8080/message/record/name/default-HOTEL_BOOKING_SUCCESS-OFFICIAL_ACCOUNT_TEMPLATE_MESSAGE

### Modify a record by id
PUT
http://localhost:8080/message/5b6039a2e29edf2a0c22171e/record

Body: [same as for create record]

### Delete a record by id
DELETE
http://localhost:8080/message/5b6039a2e29edf2a0c22171e/record

## How to send a message with run-time values

### Send message
POST
http://localhost:8080/message/send

Body:

```json
{
  "name":"default-HOTEL_BOOKING_SUCCESS-OFFICIAL_ACCOUNT_TEMPLATE_MESSAGE",
   "toUser":"o7peR0ijJGmmDW719M89uRZfFhTg",
   "parameters":"HEADER_1===感谢您预订我们的酒店&&&HOTEL_NAME===OYO Hotel888L&&&ROOM_NAME===small room&&&HOTEL_PRICE===236&&&BOOKING_ID===5578999&&&CUSTOMER_SERVICE_PHONE===13717636255"
}
```

#### Where:
"name": name of the message record you wanty to use

"toUser": user's openid 

"parameters": list of key-value pairs. use "===" beteween key and value; use "&&&" to separate each pair.

### compose a message

The message is composed with key-values, ready to be sent.

POST
http://localhost:8080/message/compose

Body:

[same as for send message]

## Front-end  setup

If you will work on front-end (UI), you need to do:

1. Download and install node.js
https://nodejs.org/en/download/

Following steps are required deponding on your environement.

2. Install React

cd [your project root]

npm install -g create-react-app

3. Install modules

cd ./frontend

npm install --save react-router-dom

npm install --save-dev bootstrap

npm install --save axios

### Build
each time you modify a file for front-end, you need to stop server, and run:

cd ./frontend

npm run build

Then, start server.

### make Tests for UI

localhost:8080

