package com.gwz.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.rmi.activation.ActivationMonitor;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gwz.bean.T_MALL_SHOPPINGCAR;
import com.gwz.bean.T_MALL_USER_ACCOUNT;
import com.gwz.mapper.LoginMapper;
import com.gwz.server.LoginServerInf;
import com.gwz.server.TestServerInf;
import com.gwz.service.CartServiceInf;
import com.gwz.util.MyJsonUtil;
import com.gwz.util.MyPropertiesUtil;
import com.gwz.util.MyWsFactoryBean;

@Controller
public class loginController {

	@Autowired
	CartServiceInf cartServiceInf;

	@Autowired
	LoginServerInf loginServerInf; 
	
	@Autowired
	TestServerInf testServerInf;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	ActiveMQQueue queueDestination;

	@RequestMapping("login")
	public String goto_login(@RequestParam(value="redirect", required=false) String redirect,
			@CookieValue(value = "list_cart_cookie", required = false) String list_cart_cookie,
			HttpServletResponse response, HttpSession session, T_MALL_USER_ACCOUNT user, HttpServletRequest request,
			String dataSource_type, ModelMap map) {

		T_MALL_USER_ACCOUNT select_user = new T_MALL_USER_ACCOUNT();// loginMapper.select_user(user);

		// 登录远程认证接口
		String loginJson = "";
		if(dataSource_type.equals("1")){
			loginJson = loginServerInf.login(user);
			testServerInf.ping("hello");
		}else if(dataSource_type.equals("2")) {
			loginJson = loginServerInf.login2(user);
//			testServerInf.ping("hello");
		}
		
		select_user = MyJsonUtil.json_to_object(loginJson, T_MALL_USER_ACCOUNT.class);

		if (select_user == null) {
			return "redirect:/login.do";
		} else {
			
			//异步调用消息队列，发布日志的消息
			try {
				final String message = select_user.getId()+"_"+select_user.getYh_mch()+"_"+"登录";
				jmsTemplate.send(queueDestination, new MessageCreator() {
					
					@Override
					public Message createMessage(Session session) throws JMSException {
						
						return session.createTextMessage(message);
					}
				});
			} catch (JmsException e1) {
				e1.printStackTrace();
			}
			
			session.setAttribute("user", select_user);

			// 在客户端存储用户个性化信息，方便用户下次再次登录使用
			try {
				Cookie cookie = new Cookie("yh_mch", URLEncoder.encode(select_user.getYh_mch(), "UTF-8"));

				cookie.setMaxAge(60 * 60 * 24);
				response.addCookie(cookie);

				Cookie cookie2 = new Cookie("yh_nch", URLEncoder.encode("周润发", "utf-8"));
				// cookie.setPath("/");
				cookie2.setMaxAge(60 * 60 * 24);
				response.addCookie(cookie2);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			// 同步购物车数据
			combine_cart(select_user, response, session, list_cart_cookie);

		}

		if(StringUtils.isBlank(redirect)) {
			
			return "redirect:/index.do";
		}else {
			
			
			
			return "redirect:/"+redirect;
		}
	}

	private void combine_cart(T_MALL_USER_ACCOUNT select_user, HttpServletResponse response, HttpSession session,
			String list_cart_cookie) {
		List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>();
		// 判断cookie中是否有数据
		if (StringUtils.isBlank(list_cart_cookie)) {
			// 没有数据

		}
		// 有数据
		else {
			// 判断db是否为空
			// 根据用户查询购物车数据
			List<T_MALL_SHOPPINGCAR> list_cart_db = cartServiceInf.get_list_cart_by_user(select_user);
			list_cart = MyJsonUtil.json_to_list(list_cart_cookie, T_MALL_SHOPPINGCAR.class);

			for (int i = 0; i < list_cart.size(); i++) {
				T_MALL_SHOPPINGCAR cart = list_cart.get(i);
				cart.setYh_id(select_user.getId());
				boolean b = cartServiceInf.if_cart_exists(list_cart.get(i));

				if (b) {
					// 更新
					for (int j = 0; j < list_cart_db.size(); j++) {
						if (cart.getSku_id() == list_cart_db.get(j).getSku_id()) {

							list_cart_db.get(j).setTjshl(list_cart_db.get(j).getTjshl() + cart.getTjshl());
							list_cart_db.get(j).setHj(list_cart_db.get(j).getHj() + cart.getTjshl() * cart.getSku_jg());

							// cart.setTjshl(cart.getTjshl() + list_cart_db.get(j).getTjshl());
							// cart.setHj(cart.getTjshl() * cart.getSku_jg());
							// 老车，更新
							cartServiceInf.update_cart(list_cart_db.get(j));
						}
					}
				} else {
					// 添加
					cartServiceInf.add_cart(cart);
				}
			}
		}
		// 同步session
		session.setAttribute("list_cart_session", cartServiceInf.get_list_cart_by_user(select_user));
		// 清空cookie
		response.addCookie(new Cookie("list_cart_cookie", ""));
	}

	/*
	 * private void combine_cart(T_MALL_USER_ACCOUNT select_user,
	 * HttpServletResponse response, HttpSession session, String list_cart_cookie) {
	 * List<T_MALL_SHOPPINGCAR> list_cart = new ArrayList<T_MALL_SHOPPINGCAR>(); //
	 * 判断cookie中是否有数据 if (StringUtils.isBlank(list_cart_cookie)) { // 没有数据 } // 有数据
	 * else { // 判断db是否为空 // 根据用户查询购物车数据 List<T_MALL_SHOPPINGCAR> list_cart_db =
	 * cartServiceInf.get_list_cart_by_user(select_user);
	 * 
	 * list_cart = MyJsonUtil.json_to_list(list_cart_cookie,
	 * T_MALL_SHOPPINGCAR.class); if (list_cart_db == null || list_cart_db.size() ==
	 * 0) { // 将当前cookie中的数据添加到db中 for (int i = 0; i < list_cart.size(); i++) {
	 * list_cart.get(i).setYh_id(select_user.getId());
	 * cartServiceInf.add_cart(list_cart.get(i)); } } //db中不为空 else { //判断是否重复 for
	 * (int i = 0; i < list_cart.size(); i++) { boolean b =
	 * if_new_cart(list_cart_db, list_cart.get(i));
	 * 
	 * if(b) { list_cart.get(i).setYh_id(select_user.getId());
	 * cartServiceInf.add_cart(list_cart.get(i)); }else { for (int j = 0; j <
	 * list_cart_db.size(); j++) { if (list_cart.get(j).getSku_id() ==
	 * list_cart_db.get(j).getSku_id()) {
	 * list_cart.get(j).setTjshl(list_cart.get(j).getTjshl() +
	 * list_cart_db.get(j).getTjshl());
	 * list_cart.get(j).setHj(list_cart.get(j).getTjshl() *
	 * list_cart.get(j).getSku_jg()); // 老车，更新
	 * cartServiceInf.update_cart(list_cart.get(j)); } } } } } } }
	 */
	private boolean if_new_cart(List<T_MALL_SHOPPINGCAR> list_cart, T_MALL_SHOPPINGCAR cart) {
		boolean b = true;
		for (int i = 0; i < list_cart.size(); i++) {
			if (list_cart.get(i).getSku_id() == cart.getSku_id()) {
				b = false;
			}
		}
		return b;
	}
}
