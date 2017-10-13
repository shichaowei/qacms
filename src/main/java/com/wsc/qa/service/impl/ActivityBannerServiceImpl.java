package com.wsc.qa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wsc.qa.dao.ActivityBannerDao;
import com.wsc.qa.meta.ActivityBanner;
import com.wsc.qa.service.ActivityBannerService;

@Service
public class ActivityBannerServiceImpl implements ActivityBannerService {

	@Resource
	private ActivityBannerDao activityBannerDao;

	@Override
	public List<ActivityBanner> getBanners(String type) {
		return activityBannerDao.selectBanners(type);
	}

}
