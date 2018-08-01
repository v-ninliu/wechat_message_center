package com.oyo.wechat.message_center.repositories;

import com.oyo.wechat.message_center.models.MessageRecord;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRecordRepository extends MongoRepository<MessageRecord, String> {
    MessageRecord findBy_id(ObjectId _id);

    MessageRecord findByName(String name);
}
