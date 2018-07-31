package com.oyo.wechat.message_center.controllers;

import com.oyo.wechat.message_center.models.MessageRecord;
import com.oyo.wechat.message_center.repositories.MessageRecordRepository;
import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
  private MessageRecordRepository repository;

  /***
   * getAllRecords
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<MessageRecord> getAllRecords() {
    return repository.findAll();
  }

  /**
   * getRecordById
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public MessageRecord getRecordById(@PathVariable("id") ObjectId id) {
    return repository.findBy_id(id);
  }

  /**
   * modifyRecordsById
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public void modifyRecordsById(@PathVariable("id") ObjectId id,
      @Valid @RequestBody MessageRecord pets) {
    pets.set_id(id);
    repository.save(pets);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public void deletePet(@PathVariable ObjectId id) {
    repository.delete(repository.findBy_id(id));
  }

  /**
   * createMessageRecord
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  public MessageRecord createMessageRecord(@RequestBody String requestBody,
      HttpServletRequest request) {

    JSONObject jsonRequest = null;
    MessageRecord messageRecord = new MessageRecord();
    try {
      jsonRequest = new JSONObject(requestBody);

      messageRecord.set_id(ObjectId.get());
      messageRecord.setCreatedDate(new Date());
      messageRecord.setName(jsonRequest.getString("name"));
      messageRecord.setCategory(jsonRequest.getString("category"));
      messageRecord.setType(jsonRequest.getString("type"));
      messageRecord.setKeywords(jsonRequest.getString("keywords"));

      String message = jsonRequest.getString("message");
      message.replace("\"", "\\\"");

      messageRecord.setMessage(message);
      repository.save(messageRecord);


    } catch (Exception e) {
      // TODO: add error message
    }

    return messageRecord;
  }
}
