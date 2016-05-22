package controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpSession;

import logic.Cart;
import logic.Item;
import logic.ItemSet;
import logic.Shop;
import logic.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import utils.WebConstants;

@Controller
public class CartController {

	@Autowired
	private Shop shopService;

	@RequestMapping(value = "/cart/cartAdd")
	public ModelAndView add(Integer itemId, Integer quantity, HttpSession session) {

		// 追加商品情報を取得
		Item selectedItem = this.shopService.getItemByItemId(itemId);


		String st;

		String ster;

		// カートを取得
		Cart cart = (Cart) session.getAttribute(WebConstants.CART_KEY);
		if (cart == null) {
			cart = this.shopService.getCart();
			session.setAttribute(WebConstants.CART_KEY, cart);
		}

		// カートに商品を追加
		cart.push(new ItemSet(selectedItem, quantity));

		// カート情報とメッセージを設定
		ModelAndView modelAndView = new ModelAndView("cart/cart");
		modelAndView.addObject("message", selectedItem.getItemName() + "を" + quantity + "個、カートに追加しました。");
		modelAndView.addObject("cart", cart);

		// ログインユーザを取得
		User loginUser = (User) session.getAttribute(WebConstants.USER_KEY);
		if (loginUser != null) {
			modelAndView.addObject("loginUser", loginUser);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/cart/cartClear")
	public ModelAndView clear(HttpSession session) {

		// カートを取得
		Cart cart = (Cart) session.getAttribute(WebConstants.CART_KEY);
		if (cart == null) {
			cart = this.shopService.getCart();
			session.setAttribute(WebConstants.CART_KEY, cart);
		}

		// カートをクリア
		cart.clearAll();

		// メッセージを設定
		ModelAndView modelAndView = new ModelAndView("cart/cart");
		modelAndView.addObject("message", "カートを空にしました。");

		// ログインユーザを取得
		User loginUser = (User) session.getAttribute(WebConstants.USER_KEY);
		if (loginUser != null) {
			modelAndView.addObject("loginUser", loginUser);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/cart/cartConfirm")
	public ModelAndView confirm(HttpSession session) {

		// カートを取得
		Cart cart = (Cart) session.getAttribute(WebConstants.CART_KEY);
		if (cart == null) {
			cart = this.shopService.getCart();
			session.setAttribute(WebConstants.CART_KEY, cart);
		}


		BigDecimal bd;


		// カート情報を設定
		ModelAndView modelAndView = new ModelAndView("cart/cart");
		modelAndView.addObject("cart", cart);

		// ログインユーザを取得
		User loginUser = (User) session.getAttribute(WebConstants.USER_KEY);
		if (loginUser != null) {
			modelAndView.addObject("loginUser", loginUser);
		}
		return modelAndView;
	}
}