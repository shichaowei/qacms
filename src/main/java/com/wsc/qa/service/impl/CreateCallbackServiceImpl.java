package com.wsc.qa.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.jayway.jsonpath.JsonPath;
import com.wsc.qa.constants.CommonConstants.ErrorCode;
import com.wsc.qa.exception.BusinessException;
import com.wsc.qa.service.CreateCallbackService;

@Service
public class CreateCallbackServiceImpl implements CreateCallbackService {

	public class Item {
		private String orderId;
		private double amount;
		private String status;
		private String userParams;

		public Item(String orderId, double amount, String status, String userParams) {
			this.orderId = orderId;
			this.amount = amount;
			this.status = status;
			this.userParams = userParams;
		}

		public Item(double amount, String status) {
			this.amount = amount;
			this.status = status;
		}

		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public double getAmount() {
			return amount;
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getUserParams() {
			return userParams;
		}

		public void setUserParams(String userParams) {
			this.userParams = userParams;
		}

	}

	@Override
	public String genCallbackStr(String remark) {
//		System.out.println(remark);
		if (StringUtils.isEmpty(remark)) {
			throw new BusinessException(ErrorCode.ERROR_ILLEGAL_PARAMTER, "remark为空数据非法");
		}
		List<String> orderId = JsonPath.read(remark, "$..billId");
		List<Double> amount = JsonPath.read(remark, "$..outAmount");
		List<String> userParams = JsonPath.read(remark, "$..customJson");
		System.out.println(orderId.size());
		ArrayList<Item> items = new ArrayList<>();
		for (int i = 0; i < orderId.size(); i++) {
			if (orderId.get(i) != null) {
				items.add(new Item(orderId.get(i), amount.get(i), "PAY_SUCCESS", userParams.get(i)));
			} else {
				items.add(new Item(amount.get(i), "PAY_SUCCESS"));
			}
		}
		System.out.println(items);
		return JSON.toJSONString(items);
	}

}
