package io.chagchagchag.example.gateway_filter_example.display.application.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductDisplayController {
  @GetMapping("/hello")
  public String hello(){
    return "hello";
  }
}
