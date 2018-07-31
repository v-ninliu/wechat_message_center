package com.oyo.wechat.message_center.models;

import java.util.Date;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class MessageRecord {

  @Id
  public ObjectId _id;

  public Date createdDate;
  public String name;
  public String category;
  public String type;
  public String keywords;
  public String message;

  // Constructors
  public MessageRecord() {
  }

  public MessageRecord(ObjectId _id, String name, Date createdDate, String category, String type, String keywords,
      String message) {
    this._id = _id;
    this.createdDate = createdDate;
    this.name = name;
    this.category = category;
    this.type = type;
    this.keywords = keywords;
    this.message = message;

  }

  // ObjectId needs to be converted to string
  public String get_id() {
    return _id.toHexString();
  }

  public void set_id(ObjectId _id) {
    this._id = _id;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getKeywords() {
    return keywords;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
