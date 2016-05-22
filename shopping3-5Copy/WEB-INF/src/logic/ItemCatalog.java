package logic;

import java.io.InputStream;
import java.util.List;

public interface ItemCatalog {

	List<Item> getItemList();

	Item getItemByItemId(Integer itemId);

	InputStream getPicture(Integer itemId);
}