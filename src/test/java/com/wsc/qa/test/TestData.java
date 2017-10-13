package com.wsc.qa.test;

import java.util.Date;

import com.wsc.qa.utils.FormateDateUtil;


public class TestData {

	public static void main(String[] args) {
		System.out.println("date -s '"+FormateDateUtil.format(new Date())+"'");
	}

}
