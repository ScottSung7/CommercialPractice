//package com.example.chatapi.web.controller;
//
//import com.example.chatapi.model.basics.Greeting;
//import com.example.chatapi.model.basics.HelloMessage;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.util.HtmlUtils;
//
//@Controller
//public class GreetingController {
//
//
//  @MessageMapping("/hello")
//  @SendTo("/topic/greetings")
//  public Greeting greeting(HelloMessage message) throws Exception {
//    Thread.sleep(1000); // simulated delay
//    System.out.println("GreetingController.greeting");
//    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//  }
//
//  @MessageMapping("/hello/{id}")
//  @SendTo("/topic/greetings")
//  public Greeting greeting2(@DestinationVariable Long id, HelloMessage message) throws Exception {
//    Thread.sleep(1000); // simulated delay
//    System.out.println("Getting Id");
//    System.out.println(id);
//    System.out.println(message.getName());
//    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//  }
//
//  @MessageMapping("/hello/{id}/{task}")
//  @SendTo("/topic/greetings")
//  public Greeting greeting2(@DestinationVariable Long id,@DestinationVariable String task, HelloMessage message) throws Exception {
//    Thread.sleep(1000); // simulated delay
//    System.out.println("Getting Id");
//    System.out.println(id);
//    System.out.println(task);
//    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//  }
//
//}