package com.wsc.qa.service;

import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

public interface DealEnvService {
	public  void fixenv(String zkAddress) ;
}
