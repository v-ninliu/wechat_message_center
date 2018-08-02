package com.oyo.wechat.message_center.service;

import javax.servlet.http.HttpServletRequest;

public interface IMessageManagementService {

  String composeMessage(String requestBody, HttpServletRequest request);

}
