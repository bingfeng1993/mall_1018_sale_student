package com.gwz.mapper;

import java.util.List;
import java.util.Map;

import com.gwz.bean.DETAIL_T_MALL_SKU;
import com.gwz.bean.T_MALL_PRODUCT;
import com.gwz.bean.T_MALL_SKU;

public interface ItemMapper {

	DETAIL_T_MALL_SKU select_detail_sku(Map<Object, Object> map);

	List<T_MALL_SKU> select_sku_by_spu(int spu_id);

}
