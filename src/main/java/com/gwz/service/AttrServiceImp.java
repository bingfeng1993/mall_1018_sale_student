package com.gwz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwz.bean.OBJECT_T_MALL_ATTR;
import com.gwz.mapper.AttrMapper;

@Service
public class AttrServiceImp implements AttrServiceInf {

	@Autowired
	AttrMapper attrMapper;
	
	@Override
	public void save_attr(int flbh2, List<OBJECT_T_MALL_ATTR> list_attr) {

		for(int i = 0;i <list_attr.size();i++) {
			//插入属性，返回主键
			attrMapper.insert_attr(flbh2, list_attr.get(i));
			
			//获得返回主键批量插入属性值
			attrMapper.insert_values(list_attr.get(i).getId(), list_attr.get(i).getList_value());
		}
	}

	@Override
	public List<OBJECT_T_MALL_ATTR> get_attr_list(int flbh2) {
		return attrMapper.select_attr_list(flbh2);
	}

}
