package com.wsc.qa.testUtil;

public class TestThrowable {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub


		try {
			System.out.println("get try");
			throw new Throwable("abc");
		} finally {
			System.out.println("get finanly");
		}
	}

}
