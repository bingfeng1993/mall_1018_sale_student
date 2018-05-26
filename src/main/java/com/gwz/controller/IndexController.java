package com.gwz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwz.bean.OBJECT_T_MALL_ATTR;
import com.gwz.bean.OBJECT_T_MALL_SKU;
import com.gwz.service.AttrServiceInf;
import com.gwz.service.ListServiceInf;

@Controller
public class IndexController {

	
	@Autowired
	ListServiceInf listServiceInf;
	
	@RequestMapping("orderErr")
	public String orderErr() {
		return "orderErr";
	}
	
	@RequestMapping
	public String goto_logout(HttpSession session) {
		
		session.invalidate();
		return "redirect:/goto_login.do";
	}

	
	
	@RequestMapping("goto_login")
	public String goto_login(HttpServletRequest request, ModelMap map) {

		return "login";
	}
	
	@RequestMapping("goto_loginOrder")
	public String goto_loginOrder(HttpServletRequest request, ModelMap map) {
		
		return "loginOrder";
	}
	
	@RequestMapping("index")
	public String index(HttpServletRequest request, ModelMap map) {
		
//		String yh_mch = "";
//		Cookie[] cookies = request.getCookies();
//		if(cookies!=null&&cookies.length>0) {
//			for (int i = 0; i < cookies.length; i++) {
//				String name = cookies[i].getName();
//				if(name.equals("yh_mch")) {
//					yh_mch = cookies[i].getValue();
//				}
//			}
//		}
//		map.put("yh_mch", yh_mch);
		return "index";
	}
}
