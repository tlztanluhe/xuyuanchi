package rugal.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
public class TestController {

	@RequestMapping("/toMain")
	@ResponseBody
	public String toMain(){
		return "main2244";
	}
	
	@RequestMapping("/toMain2.action")
	public String toMain2(){
		return "test";
	}
}
