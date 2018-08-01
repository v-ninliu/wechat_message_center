package com.oyo.wechat.message_center.controllers;

import com.oyo.wechat.message_center.models.MessageRecord;
import com.oyo.wechat.message_center.repositories.MessageRecordRepository;
import com.oyo.wechat.message_center.service.IMessageRecordService;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageRecordController {

  @Autowired
  @Qualifier("MessageRecordService")
  private IMessageRecordService messageRecordService;

  /***
   * getAllRecords
   * @return all records
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<MessageRecord> getAllMessageRecords() {

    return messageRecordService.getAllMessageRecords();
  }

  /**
   * getRecordById
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public MessageRecord getMessageRecordById(@PathVariable("id") ObjectId id) {
    return messageRecordService.getMessageRecordById(id);
  }

  /**
   * modifyRecordsById
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public MessageRecord modifyMessageRecordsById(@PathVariable("id") ObjectId id,
      @Valid @RequestBody MessageRecord record) {
    return messageRecordService.modifyMessageRecordsById(id, record);
  }

  /**
   * deleteMessageRecord
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void deleteMessageRecord(@PathVariable ObjectId id) {
    messageRecordService.deleteMessageRecord(id);
  }

  /**
   * createMessageRecord
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  public MessageRecord createMessageRecord(@RequestBody String requestBody,
      HttpServletRequest request) {
    return messageRecordService.createMessageRecord(requestBody, request);
  }
}
