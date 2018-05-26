package com.gwz.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwz.bean.DETAIL_T_MALL_SKU;
import com.gwz.bean.T_MALL_SKU;
import com.gwz.mapper.ItemMapper;

@Service
public class ItemServiceImp implements ItemServiceInf {
	
	@Autowired
	ItemMapper itemMapper;

	@Override
	public DETAIL_T_MALL_SKU get_sku_detail(int sku_id) {
		Map<Object, Object> map = new HashMap<Object,Object>();
		map.put("sku_id", sku_id);
		DETAIL_T_MALL_SKU obj_sku = itemMapper.select_detail_sku(map);
		return obj_sku;
	}

	@Override
	public List<T_MALL_SKU> get_sku_by_spu(int spu_id) {
		return itemMapper.select_sku_by_spu(spu_id);
	}

}
