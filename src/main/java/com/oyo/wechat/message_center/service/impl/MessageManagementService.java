package com.oyo.wechat.message_center.service.impl;

import com.oyo.wechat.message_center.constants.GlobalConstants;
import com.oyo.wechat.message_center.models.MessageRecord;

import com.oyo.wechat.message_center.service.IMessageManagementService;
import com.oyo.wechat.message_center.service.IMessageRecordService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("MessageManagementService")
public class MessageManagementService implements IMessageManagementService {

  @Autowired
  @Qualifier("MessageRecordService")
  private IMessageRecordService messageRecordService;

  @Override
  public String composeMessage(String requestBody,
      HttpServletRequest request) {

    JSONObject jsonRequest = null;
    MessageRecord messageRecord = null;
    JSONObject jsonMessage = null;
    String composeMessage = null;

    try {
      jsonRequest = new JSONObject(requestBody);

      String name = jsonRequest.getString("name");
      String parameters = jsonRequest.getString("parameters");
      String toUser = jsonRequest.getString("toUser");

      messageRecord = messageRecordService.getMessageRecordByName(name);
      String message = messageRecord.getMessage();
      //message = message.replace("\\", "");
      jsonMessage = new JSONObject(message);
      jsonMessage.put("toUser", toUser);

      composeMessage = jsonMessage.toString();

      if (parameters != null) {
        Map<String, String> parametersMap = createKeyValueMapFromString(parameters,
            GlobalConstants.KEY_VALUE_SEPARATOR,
            GlobalConstants.ITEM_SEPARATOR);

        // first handle keywords
        String keywords = messageRecord.getKeywords();
        if (keywords != null) {
          handleKeywords(jsonMessage, keywords, parametersMap);
          composeMessage = jsonMessage.toString();
        }

      }

    } catch (Exception e) {
      // TODO: add error message log
    }

    return composeMessage;
  }

  private void handleKeywords(JSONObject jsonMessage, String keywords,
      Map<String, String> parametersMap) {

    try {
      Map<String, String> keywordsMap = createKeyValueMapFromString(keywords,
          GlobalConstants.KEY_VALUE_SEPARATOR,
          GlobalConstants.ITEM_SEPARATOR);

      JSONArray dataArray = jsonMessage.getJSONArray("data");

      for (Map.Entry<String, String> entry : keywordsMap.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue();
        String realValue = GlobalConstants.EMPTY_STRING;
        for (Map.Entry<String, String> entryParam : parametersMap.entrySet()) {
          String keyParam = entryParam.getKey();
          if (value.equalsIgnoreCase(keyParam)) {
            realValue = entryParam.getValue();
            break;
          }
        }
        setRealValue(key, realValue, dataArray);
      }
    } catch (Exception e) {
      // TODO: add error message log
    }
  }

  private void setRealValue(String key, String realValue, JSONArray dataArray) {

    try {
      for (int i = 0, size = dataArray.length(); i < size; i++) {
        JSONObject objectInArray = dataArray.getJSONObject(i);
        String name = objectInArray.getString("name");
        if (name.equalsIgnoreCase(key)) {
          objectInArray.put("value", realValue);
        }
      }

    } catch (Exception e) {
      // TODO: add error message log
    }
  }

  private Map<String, String> createKeyValueMapFromString(String input, String keyValueSeparator,
      String itemSeparator) {

    Map<String, String> map = new HashMap<String, String>();

    for (String keyValue : input.split(itemSeparator)) {
      String[] pairs = keyValue.split(keyValueSeparator, 2);
      map.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
    }

    return map;
  }
}
