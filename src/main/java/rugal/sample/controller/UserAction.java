package rugal.sample.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rugal.sample.common.Const;
import rugal.sample.common.DateUtil;
import rugal.sample.common.Message;
import rugal.sample.common.ResultConstData;
import rugal.sample.common.UserUtil;
import rugal.sample.core.entity.User;
import rugal.sample.core.service.UserService;
import rugal.sample.core.vo.BaseRes;
import rugal.sample.core.vo.user.LoginUser;
import rugal.sample.exception.BusinessException;

@Controller
@RequestMapping(value = "/user")
public class UserAction {

	@Autowired
	UserService userService;
	
	@RequestMapping("/toLogin.action")
	public String toLogin(HttpServletRequest request ){
		LoginUser user = UserUtil.getLoginUser(request);
		if(user == null){
			return "/user/login";
		}
		//许愿池发布页面
		return "/wish/publish";
	}
	
	@RequestMapping("/toReg.action")
	public String toReg(HttpServletRequest request){
		return "/user/reg";
	}
	
	/***
	 * 用户注册
	 * @return
	 */
	@RequestMapping("/reg.action")
	@ResponseBody
	public BaseRes reg(HttpServletRequest reuqest,User user){
		BaseRes res = new BaseRes();
		try {
			this.userService.save(user);
			res.setCode(ResultConstData.MSG_SUCCESS_CODE).setContent("注册成功，请登录！");
		} catch (BusinessException e) {
			e.printStackTrace();
			res.setCode(ResultConstData.MSG_FAIL_CODE).setContent(e.getMessage());
		} catch (Exception e){
			e.printStackTrace();
			res.setCode(ResultConstData.MSG_FAIL_CODE).setContent("系统错误，注册失败");
		}
		return res;
	}
	
	/****
	 * 用户登录逻辑
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login.action",method = RequestMethod.POST)
	@ResponseBody
	public BaseRes login(HttpServletRequest request , User user){
		BaseRes baseRes = new BaseRes();
		LoginUser loginUser = this.userService.login(user);
		if(loginUser != null){
			loginUser.setLoginTime(DateUtil.yyyy_MM_dd_HH_mm_ss.format(new Date()));
			loginUser.setLoginIp(request.getRemoteHost());
			HttpSession session = request.getSession();
			session.setAttribute(Const.user_login_key,loginUser);
			baseRes.setCode(ResultConstData.MSG_SUCCESS_CODE).setContent("用户登录成功!");
		}else{
			baseRes.setCode(ResultConstData.MSG_FAIL_CODE).setContent("用户名或密码无效！");
		}
		return baseRes;
	}
	
}
