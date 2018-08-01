package com.oyo.wechat.message_center.service;

import com.oyo.wechat.message_center.models.MessageRecord;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;

public interface IMessageRecordService {

  MessageRecord createMessageRecord(String requestBody, HttpServletRequest request);

  void deleteMessageRecord(ObjectId id);

  MessageRecord modifyMessageRecordsById(ObjectId id, MessageRecord record);

  MessageRecord getMessageRecordById(ObjectId id);

  MessageRecord getMessageRecordByName(String name);

  List<MessageRecord> getAllMessageRecords();

}
