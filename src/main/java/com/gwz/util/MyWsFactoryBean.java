package com.gwz.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.FactoryBean;

import com.gwz.server.LoginServerInf;

public class MyWsFactoryBean<T> implements FactoryBean<T> {

	private String url;
	private Class<T> t;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}

	public static <T> T getMyWs(String url, Class<T> t) {
		JaxWsProxyFactoryBean jwfb = new JaxWsProxyFactoryBean();
		jwfb.setAddress(url);
		jwfb.setServiceClass(t);

		// 加入安全协议
		if ("TestServerInf".equals(t.getSimpleName())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
			map.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordText");
			map.put("user", "username");
			map.put(WSHandlerConstants.PW_CALLBACK_CLASS, MyCallback.class.getName());

			WSS4JOutInterceptor wss4jOutInterceptor = new WSS4JOutInterceptor(map);
			jwfb.getOutInterceptors().add(wss4jOutInterceptor);
		}
		T bean = (T) jwfb.create();

		return bean;
	}

	@Override
	public T getObject() throws Exception {
		// TODO Auto-generated method stub
		return getMyWs(url, this.t);
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return this.t;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return false;
	}

}
