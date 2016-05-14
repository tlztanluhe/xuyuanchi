package rugal.sample.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import rugal.sample.common.UserUtil;
import rugal.sample.core.service.UserService;
import rugal.sample.core.vo.user.LoginUser;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/wish")
public class WishAction {

	@Autowired
	UserService userService;
	
	@RequestMapping("/toPublish")
	public String toPublish(HttpServletRequest request){
		
		LoginUser user = UserUtil.getLoginUser(request);
//		if(user ){
//			
//		}
		System.out.println("发布愿望");
		return "/wish/publish";
	}
	
}
