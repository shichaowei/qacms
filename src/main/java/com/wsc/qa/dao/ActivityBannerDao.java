package com.wsc.qa.dao;

import java.util.List;

import com.wsc.qa.meta.ActivityBanner;

/**
 *
 * @author CBJ
 *
 */
public interface ActivityBannerDao {



	public ActivityBanner selectByPrimaryKey(String id);


	public List<ActivityBanner> selectBanners(String type);
}