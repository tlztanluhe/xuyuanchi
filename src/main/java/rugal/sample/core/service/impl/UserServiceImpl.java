package rugal.sample.core.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonIOException;

import rugal.sample.common.DateUtil;
import rugal.sample.core.dao.BaseDao;
import rugal.sample.core.entity.User;
import rugal.sample.core.service.UserService;
import rugal.sample.core.vo.user.LoginUser;
import rugal.sample.exception.BusinessException;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	
    @Autowired
    BaseDao baseDao;

	@Override
	public Integer save(User user) throws BusinessException {
		
		List<User> userList = this.getUserListByName(user.getName());
		if(userList != null && userList.size() > 0){
			throw new BusinessException("当前用户名已存在！");
		}
		user.setRegTime(DateUtil.yyyy_MM_dd_HH_mm_ss.format(new Date()));
		return (Integer) this.baseDao.save(user);
	}

	@Transactional(readOnly = true)
	@Override
	public LoginUser login(User user) {
		LoginUser loginUser = null;
		String password = user.getPassword();
		List<User> userList = this.getUserListByName(user.getName());
		for(User tempUser:userList){
			if(tempUser.getPassword().equals(password)){
				loginUser = new LoginUser();
				loginUser.setUserName(user.getName());
				loginUser.setUserPass(user.getPassword());
				loginUser.setPhone(user.getPhone());
				loginUser.setEmail(user.getEmail());
				break;
			}
		}
		return loginUser;
	}
    public List<User> getUserListByName(String userName){
    	String nameQueryHql = "from User where name = '%s'";
    	nameQueryHql = String.format(nameQueryHql,userName);
    	List<User> userList = this.baseDao.getListByHql(nameQueryHql);
    	return userList;
    }
    // @Transactional(readOnly = true)
    
   
}
