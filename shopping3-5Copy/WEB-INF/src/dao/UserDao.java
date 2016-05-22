package dao;

import java.util.List;

import logic.User;

public interface UserDao {

	void create(User user);

	User findByUserIdAndPassword(String userId, String password);

	List<User> findByUserIdAndPasswordAll();

	List<User> findByUserId(String userId);

	void delete(String userId);

}