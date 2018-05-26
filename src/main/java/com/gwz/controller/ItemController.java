package com.gwz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gwz.bean.DETAIL_T_MALL_SKU;
import com.gwz.bean.T_MALL_SKU;
import com.gwz.service.ItemServiceInf;

@Controller
public class ItemController {
	
	@Autowired
	ItemServiceInf itemServiceInf;

	@RequestMapping("goto_sku_detail")
	public String goto_sku_detail(int sku_id,int spu_id,ModelMap map) {
		
		// 查询商品详细信息
		DETAIL_T_MALL_SKU obj_sku = itemServiceInf.get_sku_detail(sku_id);
		
		// 查询同spu下的相同的其他sku信息
		List<T_MALL_SKU> list_sku = itemServiceInf.get_sku_by_spu(spu_id);
		
		map.put("obj_sku", obj_sku);
		map.put("list_sku", list_sku);
		return "skuDetail";
	}
}
