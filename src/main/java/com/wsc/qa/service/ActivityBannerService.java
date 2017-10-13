package com.wsc.qa.service;

import java.util.List;

import com.wsc.qa.meta.ActivityBanner;

/**
 *
 * @author CBJ
 *
 */
public interface ActivityBannerService {

	List<ActivityBanner> getBanners(String type);
}
