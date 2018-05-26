package com.gwz.mapper;

import java.util.List;
import java.util.Map;

import com.gwz.bean.OBJECT_T_MALL_SKU;

public interface ListMapper {

	List<OBJECT_T_MALL_SKU> select_list_by_flbh2(int flbh2);

	List<OBJECT_T_MALL_SKU> select_list_by_attr(Map<Object, Object> map);

}
