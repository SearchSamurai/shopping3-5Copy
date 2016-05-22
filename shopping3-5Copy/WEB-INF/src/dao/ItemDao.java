package dao;

import java.io.InputStream;
import java.util.List;

import logic.Item;

public interface ItemDao {

	List<Item> findAll();

	Item findByPrimaryKey(Integer itemId);

	InputStream getPicture(Integer itemId);
}