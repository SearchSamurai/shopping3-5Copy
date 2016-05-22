package dao;

import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import logic.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

@Repository
public class ItemDaoImpl implements ItemDao {

	@Autowired
	private LobHandler lobHandler;

	private SimpleJdbcTemplate template;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.template = new SimpleJdbcTemplate(dataSource);
	}

	private static final String SELECT_ALL = "SELECT item_id, item_name, price, description, picture_url FROM item";

	public List<Item> findAll() {
		RowMapper<Item> mapper = new BeanPropertyRowMapper<Item>(Item.class);
		return this.template.query(ItemDaoImpl.SELECT_ALL, mapper);
	}

	private static final String SELECT_BY_PRIMARY_KEY = "SELECT item_id, item_name, price, description, picture_url FROM item WHERE item_id = ?";

	public Item findByPrimaryKey(Integer itemId) {
		RowMapper<Item> mapper = new BeanPropertyRowMapper<Item>(Item.class);
		return this.template.queryForObject(SELECT_BY_PRIMARY_KEY, mapper, itemId);
	}

	//public InputStream getPicture(Integer itemId) {
	//	return this.template.queryForObject("SELECT picture FROM item WHERE item_id = ?", new ItemDaoImpl(), itemId);
	//}

	//@Override
	//public InputStream mapRow(ResultSet rs, int i) throws SQLException {
	//	return lobHandler.getBlobAsBinaryStream(rs, "picture");
	//}

	public InputStream getPicture(Integer itemId) {
		return this.template.queryForObject("SELECT picture FROM item WHERE item_id = ?", new RowMapper<InputStream>() {
			public InputStream mapRow(ResultSet rs, int i) throws SQLException {
				return lobHandler.getBlobAsBinaryStream(rs, "picture");
			}
		}, itemId);
	}
}