package logic;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.UserDao;

@Service
public class UserCatalogImpl implements UserCatalog {

	@Autowired
	private UserDao userDao;



	public void entryUser(User user) {
		this.userDao.create(user);
	}

	public User getUserByUserIdAndPassword(String userId, String password) {
		return this.userDao.findByUserIdAndPassword(userId, password);
	}

	public List<User> getUserAllIdAndPassword() {
		return this.userDao.findByUserIdAndPasswordAll();
	}

	public List<User> getUserByUserId(String userId){
		List<User> result =this.userDao.findByUserId(userId);
		for (User u : result) {
			u.getBirthDay();
		}
		return result;
	}

	public void delete(String userId){
		this.userDao.delete(userId);
	}


}