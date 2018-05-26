package com.gwz.service;

import com.gwz.bean.OBJECT_T_MALL_ORDER;
import com.gwz.bean.T_MALL_ADDRESS;
import com.gwz.exception.OverSaleException;

public interface OrderServiceInf {

	void save_order(T_MALL_ADDRESS get_address, OBJECT_T_MALL_ORDER order);

	void pay_success(OBJECT_T_MALL_ORDER order)throws OverSaleException ;

}
