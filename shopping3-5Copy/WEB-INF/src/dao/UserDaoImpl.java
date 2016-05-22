package dao;

import java.util.List;

import javax.sql.DataSource;

import logic.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	private static final String SELECT_BY_USERID_PASSWORD = "SELECT user_id, password, user_name, postcode,"
			+ " address, email, job, birthday FROM USER_ACCOUNT WHERE user_id = ? AND password = ?";

	private static final String INSERT = "INSERT INTO USER_ACCOUNT (user_id, user_name, password, postcode, address, email, job, birthday)"
			+ " VALUES(:userId, :userName, :password, :postCode, :address, :email, :job, :birthDay)";

	private static final String SELECT_ALL_USERID_PASSWORD = "SELECT * FROM USER_ACCOUNT ORDER BY user_id";

	private static final String SELECT = "SELECT user_id, password, user_name, postcode,"
			+ " address, email, job, birthday FROM USER_ACCOUNT WHERE user_id LIKE ? ";

	private static final String DELETE = "DELETE FROM USER_ACCOUNT WHERE user_id = ?";

	private static final String UPDATE = "UPDATE USER_ACCOUNT SET password = ? WHERE user_id = ?";


	private static final String p = "%";

	private SimpleJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
	}

	public User findByUserIdAndPassword(String userId, String password) {
		RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
		return this.template.queryForObject(SELECT_BY_USERID_PASSWORD, mapper, userId, password);
	}

	public List<User> findByUserIdAndPasswordAll() {
		RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
		return this.template.query(SELECT_ALL_USERID_PASSWORD , mapper);
	}


	public List<User> findByUserId(String userId){
		RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
		return this.template.query(SELECT , mapper ,p + userId + p);
	}

	public void delete(String userId){
		RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
		//this.template.query(DELETE , mapper ,userId);
		//本当に削除したらまずいので代理でアップデート
		//this.template.query(UPDATE , mapper , Double.toString(Math.random()*3444+1000) , userId);
        //アップデートもできないっぽい。
	}

	public void create(User user) {
		//SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
		RowMapper<User> mapper = new BeanPropertyRowMapper<User>(User.class);
		this.template.update(UserDaoImpl.INSERT, mapper );
	}
}
