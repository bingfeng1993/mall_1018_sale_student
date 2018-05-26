package com.gwz.server;

import javax.jws.WebService;

import com.gwz.bean.T_MALL_USER_ACCOUNT;

@WebService
public interface LoginServerInf {
	public String login(T_MALL_USER_ACCOUNT user);
	public String login2(T_MALL_USER_ACCOUNT user);
}
