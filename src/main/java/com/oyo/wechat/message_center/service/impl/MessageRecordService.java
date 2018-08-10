package com.oyo.wechat.message_center.service.impl;

import com.oyo.wechat.message_center.exception.MessageRecordHandleException;
import com.oyo.wechat.message_center.models.MessageRecord;
import com.oyo.wechat.message_center.repositories.MessageRecordRepository;
import com.oyo.wechat.message_center.service.IMessageRecordService;
import java.util.List;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service("MessageRecordService")
public class MessageRecordService implements IMessageRecordService {

  @Autowired
  private MessageRecordRepository repository;

  @Override
  public MessageRecord createMessageRecord(String requestBody,
      HttpServletRequest request) {

    JSONObject jsonRequest = null;
    MessageRecord messageRecord = new MessageRecord();
    try {
      jsonRequest = new JSONObject(requestBody);

      messageRecord.set_id(ObjectId.get());
      messageRecord.setCreatedDate(new Date());

      String category = jsonRequest.getString("category");
      String type = jsonRequest.getString("type");
      String name = jsonRequest.getString("name");

      if( repository.findByName(name) != null ) {
        throw (new MessageRecordHandleException("The record with name = " + name + " exists already. name must be unique"));
      }

      messageRecord.setName(name);
      messageRecord.setCategory(category);
      messageRecord.setType(type);

      if( jsonRequest.has("keywords") ) {
        messageRecord.setKeywords(jsonRequest.getString("keywords"));
      }
      if( jsonRequest.has("placeholders") ) {
        messageRecord.setPlaceholders(jsonRequest.getString("placeholders"));
      }

      String message = jsonRequest.getString("message");
      message.replace("\"", "\\\"");

      messageRecord.setMessage(message);
      repository.save(messageRecord);

    } catch (MessageRecordHandleException e) {
      throw(e);
    } catch (Exception e) {
      return null;
    }

    return messageRecord;
  }

  @Override
  public void deleteMessageRecord(ObjectId id) {
    repository.delete(repository.findBy_id(id));
  }

  @Override
  public MessageRecord modifyMessageRecordsById(ObjectId id, MessageRecord record) {
    record.set_id(id);
    MessageRecord existedRecord = repository.findByName(record.getName());
    if( existedRecord != null && (!existedRecord.get_id().equalsIgnoreCase(record.get_id())) ) {
      throw (new MessageRecordHandleException("The record with name = " + record.getName() + " exists already. name must be unique"));
    }
    record.setCreatedDate(new Date());
    repository.save(record);
    return record;
  }

  @Override
  public MessageRecord getMessageRecordById(ObjectId id) {
    return repository.findBy_id(id);
  }

  @Override
  public MessageRecord getMessageRecordByName(String name) {
    return repository.findByName(name);
  }

  @Override
  public List<MessageRecord> getAllMessageRecords() {
    return repository.findAll();
  }

}
