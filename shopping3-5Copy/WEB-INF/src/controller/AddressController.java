package controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import logic.CheckBoxBean;
import logic.User;
import logic.UserCatalog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import utils.WebConstants;

@Controller
public class AddressController {

	@Autowired
	private UserCatalog ui;
	private CheckBoxBean check = new CheckBoxBean();


	@RequestMapping
	public ModelAndView userListView(User user, BindingResult bindingResult, HttpSession session) {
		// ユーザー一覧情報を取得
		List<User> userList = this.ui.getUserAllIdAndPassword();

		// モデルの作成
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("userList", userList);
		model.put("check" ,check);

		// 戻り値となるModelAndViewインスタンスを作成
		ModelAndView modelAndView = new ModelAndView("userlistview/userListView");
		modelAndView.addAllObjects(model);
		modelAndView.getModel().putAll(bindingResult.getModel());

		User loginUser = (User) session.getAttribute(WebConstants.USER_KEY);
		if (loginUser != null) {
			modelAndView.addObject("loginUser", loginUser);
		}
		return modelAndView;
	}



	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView search(String address, User user , BindingResult bindingResult , HttpSession session) {
		//this.userEntryValidator.validate(user, bindingResult);

		if (address == null || address.equals("")) {
			//
			return this.userListView(user , bindingResult , session);
		}

		List<User> userList = this.ui.getUserByUserId(address);
		if (userList == null || userList.isEmpty()) {
			//
			bindingResult.rejectValue("job", "error.required");
			bindingResult.reject("error.input.user");
			return this.userListView(user , bindingResult , session);

		}

		// 戻り値となるModelAndViewインスタンスを作成
		ModelAndView modelAndView = new ModelAndView("userlistview/userListView");
		modelAndView.addObject("userList", userList);

		return modelAndView;
	}


	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView delete(HttpServletRequest req , User user , BindingResult bindingResult , HttpSession session ) {
		check.setIds(req.getParameterValues("c"));
		if(check.getIds() != null){
			for(String s : check.getIds()){
				ui.delete(s);
			}
		}
		// 戻り値となるModelAndViewインスタンスを作成
		return this.userListView(user , bindingResult , session);
	}
}