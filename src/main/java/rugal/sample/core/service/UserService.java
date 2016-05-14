package rugal.sample.core.service;

import rugal.sample.core.entity.Student;
import rugal.sample.core.entity.User;
import rugal.sample.core.vo.user.LoginUser;
import rugal.sample.exception.BusinessException;

public interface UserService {

	/**
	 * 保存用户
	 * @param user
	 * @return
	 * @throws BusinessException 
	 */
	public Integer save(User user) throws BusinessException;

	/***
	 * 用户登录逻辑
	 * @param user
	 * @return
	 */
	public LoginUser login(User user);
}
