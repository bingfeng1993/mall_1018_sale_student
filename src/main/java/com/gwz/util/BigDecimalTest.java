package com.gwz.util;

import java.math.BigDecimal;

public class BigDecimalTest {

	public static void main(String[] args) {
		//初始化
		BigDecimal b1 = new BigDecimal("0.02");
		BigDecimal b2 = new BigDecimal(0.02d);
		BigDecimal b3 = new BigDecimal(0.02f);
		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);
		
		//比较大小
		System.out.println(b2.compareTo(b3));
		
		//运算
		BigDecimal b4 = new BigDecimal("6");
		BigDecimal b5 = new BigDecimal("7");
		System.out.println(b4.add(b5));
		System.out.println(b4.subtract(b5));
		System.out.println(b4.multiply(b5));
//		System.out.println(b4.divide(b5));
		
		//取舍
		System.out.println(b4.divide(b5, 4, BigDecimal.ROUND_HALF_DOWN));
		BigDecimal setScale = b2.add(b3).setScale(4, BigDecimal.ROUND_HALF_DOWN);
		System.out.println(setScale);
	}

}
