package com.oyo.wechat.message_center.controllers;

import com.oyo.wechat.message_center.service.IMessageManagementService;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageManagementController {

  @Autowired
  @Qualifier("MessageManagementService")
  private IMessageManagementService messageManagementService;


  /**
   * composeMessage
   */
  @RequestMapping(value = "/compose", method = RequestMethod.POST)
  public String composeMessage(@RequestBody String requestBody,
      HttpServletRequest request) {
    return messageManagementService.composeMessage(requestBody, request);
  }

  /**
   * sendMessage
   */
  @RequestMapping(value = "/send", method = RequestMethod.POST)
  public String sendMessage(@RequestBody String requestBody,
      HttpServletRequest request) {
    return messageManagementService.sendMessage(requestBody, request);
  }
}
