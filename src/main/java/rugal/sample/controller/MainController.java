package rugal.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/mainPage")
public class MainController {

	/***
	 * 主页跳转action，可以做一些处理
	 * @return
	 */
	@RequestMapping("/toMain.action")
	public String toMain(){
		return "main";
	}
}
