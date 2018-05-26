package com.gwz.mapper;

import java.util.List;

import com.gwz.bean.T_MALL_SHOPPINGCAR;

public interface CartMapper {

	void insert_Cart(T_MALL_SHOPPINGCAR cart);

	void update_Cart(T_MALL_SHOPPINGCAR cart);

	int select_cart_exists(T_MALL_SHOPPINGCAR cart);

	List<T_MALL_SHOPPINGCAR> select_list_cart_by_user(int id);

}
