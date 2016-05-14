package rugal.sample.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rugal.sample.core.entity.User;
import rugal.sample.core.vo.user.LoginUser;

public class UserUtil {

	/****
	 * 获取登录用户
	 * @param request
	 * @return
	 */
	public static LoginUser getLoginUser(HttpServletRequest request){
		LoginUser user = null;
		HttpSession session = request.getSession();
		if(session != null){
			user = (LoginUser) session.getAttribute("login_user");
		}
		return user;
	}
	
}
