package com.ari3program.ticketmachine.liff.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {

	@GetMapping("/liff")
	  public String hello(Model model) {
	    // [[${test}]] の部分を Hello... で書き換えて、liff.htmlを表示する
	    model.addAttribute("test", "Hello Tymeleaf!");
	    return "liff/top";
	  }

}
