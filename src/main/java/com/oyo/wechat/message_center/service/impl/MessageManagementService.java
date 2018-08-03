package com.oyo.wechat.message_center.service.impl;

import com.oyo.wechat.message_center.client.SimpleHttpClient;
import com.oyo.wechat.message_center.constants.GlobalConstants;
import com.oyo.wechat.message_center.enums.MessageCategoryEnum;
import com.oyo.wechat.message_center.models.MessageRecord;
import com.oyo.wechat.message_center.utils.StringUtils;
import com.oyo.wechat.message_center.constants.RemoteAPIConstants;

import com.oyo.wechat.message_center.service.IMessageManagementService;
import com.oyo.wechat.message_center.service.IMessageRecordService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service("MessageManagementService")
public class MessageManagementService implements IMessageManagementService {

  @Autowired
  @Qualifier("MessageRecordService")
  private IMessageRecordService messageRecordService;

  @Autowired
  private SimpleHttpClient simpleHttpClient;

  @Value("${platform-wechat-service.host}")
  private String platformWechatServiceBaseUrl;

  @Value("${platform-wechat-service.token}")
  private String platformWechatServiceToken;

  @Override
  public String composeMessage(String requestBody,
      HttpServletRequest request) {

    try {
      JSONObject jsonRequest = new JSONObject(requestBody);
      String name = jsonRequest.getString("name");
      MessageRecord messageRecord = messageRecordService.getMessageRecordByName(name);
      return composeMessageWithRealValues(jsonRequest, messageRecord);
    } catch (Exception e) {
      // TODO: add error message log
      return( e.getMessage());
    }
  }

  @Override
  public String sendMessage(String requestBody,
      HttpServletRequest request) {

    try {
      JSONObject jsonRequest = new JSONObject(requestBody);
      String name = jsonRequest.getString("name");
      MessageRecord messageRecord = messageRecordService.getMessageRecordByName(name);
      String composedImage = composeMessageWithRealValues(jsonRequest, messageRecord);

      // find sending message URL by category
      String category = messageRecord.getCategory();
      MessageCategoryEnum categoryEnum = MessageCategoryEnum.valueOf(category);
      String urlAPI = null;

      switch (categoryEnum) {
        case OFFICIAL_ACCOUNT_TEXT_MESSAGE:
          urlAPI = RemoteAPIConstants.WECHAT_SEND_TEXT_MESSAGE;
          break;

        case OFFICIAL_ACCOUNT_PICTURE_MESSAGE:
          urlAPI = RemoteAPIConstants.WECHAT_SEND_PICTURE_MESSAGE;
          break;

        case OFFICIAL_ACCOUNT_TEMPLATE_MESSAGE:
          urlAPI = RemoteAPIConstants.WECHAT_SEND_TEMPLATE_MESSAGE;
          break;

        default:
          return("Message category is unkown. Cannot send message");
      }

      String url = platformWechatServiceBaseUrl + urlAPI;
      String accessToken = platformWechatServiceToken;
      HttpHeaders headers = simpleHttpClient.getJsonHeaders();
      headers.set("access_token", accessToken);
      String result = simpleHttpClient.post(composedImage, url,headers).getBody();
      return result;
    } catch (Exception e) {
      return( e.getMessage());
    }
  }

  private String composeMessageWithRealValues(JSONObject jsonRequest,
      MessageRecord messageRecord) {

    JSONObject jsonMessage = null;
    String composeMessage = null;

    try {
      String name = jsonRequest.getString("name");
      String parameters = jsonRequest.getString("parameters");
      String toUser = jsonRequest.getString("toUser");

      String message = messageRecord.getMessage();

      jsonMessage = new JSONObject(message);
      jsonMessage.put("toUser", toUser);

      composeMessage = jsonMessage.toString();

      if (parameters != null) {
        Map<String, String> parametersMap = StringUtils.createKeyValueMapFromString(parameters,
            GlobalConstants.KEY_VALUE_SEPARATOR,
            GlobalConstants.ITEM_SEPARATOR);

        // first handle keywords
        String keywords = messageRecord.getKeywords();
        if (keywords != null) {
          handleKeywords(jsonMessage, keywords, parametersMap);
          composeMessage = jsonMessage.toString();
        }

        // second handle placeholders
        String placeholders = messageRecord.getPlaceholders();
        if (placeholders != null) {
          composeMessage = handlePlaceholders(composeMessage, placeholders, parametersMap);
        }

      }

    } catch (Exception e) {
      // TODO: add error message log
    }

    return composeMessage;
  }

  private String handlePlaceholders(String message, String placeholders,
      Map<String, String> parametersMap) {

    String outMessage = message;
    try {
      Map<String, String> placeholdersMap = StringUtils.createKeyValueMapFromString(placeholders,
          GlobalConstants.KEY_VALUE_SEPARATOR,
          GlobalConstants.ITEM_SEPARATOR);

      for (Map.Entry<String, String> entry : placeholdersMap.entrySet()) {
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
        outMessage = outMessage.replace(key, realValue);
      }
    } catch (Exception e) {
      // TODO: add error message log
    }

    return outMessage;
  }

  private void handleKeywords(JSONObject jsonMessage, String keywords,
      Map<String, String> parametersMap) {

    try {
      Map<String, String> keywordsMap = StringUtils.createKeyValueMapFromString(keywords,
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
}
