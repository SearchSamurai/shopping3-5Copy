package controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.Item;
import logic.ItemCatalog;
import logic.Shop;
import logic.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import utils.WebConstants;

@Controller
public class DetailController {

	@Autowired
	private Shop shopService;

	@Autowired
	private ItemCatalog itemCatalog;

	@RequestMapping
	public ModelAndView detail(Integer itemId, HttpSession session) {
		// 選択された商品IDから商品情報を取得
		Item item = this.shopService.getItemByItemId(itemId);

		// モデルの作成
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("item", item);

		// 戻り値となるModelAndViewインスタンスを作成
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addAllObjects(model);

		// ログインユーザを取得
		User loginUser = (User) session.getAttribute(WebConstants.USER_KEY);
		if (loginUser != null) {
			modelAndView.addObject("loginUser", loginUser);
		}
		return modelAndView;
	}


	@RequestMapping
	public void image(Integer itemId, HttpServletResponse response) {
		response.setContentType("image/jpeg");
		InputStream picture = null;
		OutputStream os = null;
		BufferedInputStream bis = null;
		try {
			picture = this.itemCatalog.getPicture(itemId);
			os = response.getOutputStream();
			bis = new BufferedInputStream(picture);
			int data;
			while ((data = bis.read()) != -1) {
				os.write(data);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (picture != null) {
					picture.close();
					os.close();
					bis.close();
				}
			} catch (IOException e) {
				// closeできなかっただけなので無視する
			}
		}
	}
}