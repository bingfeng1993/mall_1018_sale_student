package com.gwz.util;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.apache.wss4j.dom.handler.WSHandlerConstants;


public class MyCallback implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		//配置username和password的代码
		WSPasswordCallback ws = (WSPasswordCallback) callbacks[0];
		
		ws.setIdentifier("cxf");
		String password = "123";//数据库或者配置文件
		ws.setPassword(password);
	}

}
