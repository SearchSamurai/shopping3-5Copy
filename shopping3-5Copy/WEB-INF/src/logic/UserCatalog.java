package logic;

import java.util.List;

public interface UserCatalog {

	void entryUser(User user);

	User getUserByUserIdAndPassword(String userId, String password);

	List<User> getUserAllIdAndPassword();

	List<User> getUserByUserId(String userId);

	void delete(String userId);
}