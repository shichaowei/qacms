package com.tairanchina.fd.common.utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;

/**
 * 限流器（漏桶），也可使用 Guava 令牌桶
 *
 * @author HomJie
 *
 */
public class RateLimiter {

	private final int maxPermits;
	private Queue<Long> queue;
	private final long period;
	// 计数器
	private int counter = 0;

	/**
	 * @param maxPermits
	 *            最大通行数
	 * @param time
	 *            时间长度
	 * @param unit
	 *            时间单位，有效到微秒
	 */
	public RateLimiter(int maxPermits, long time, TimeUnit unit) {
		this.maxPermits = maxPermits;
		queue = new ConcurrentLinkedQueue<Long>();
		period = unit.toMillis(time);
	}

	/**
	 * 获取通行，会被阻塞
	 */
	public synchronized void acquire() {
		while (clearExpired())
			waitExpired();
		offer();
	}

	/**
	 * 快速获取通行
	 *
	 * @return 是否成功
	 */
	public synchronized boolean tryAcquire() {
		if (clearExpired())
			return false;
		offer();
		return true;
	}

	/**
	 * 在指定时间内获取通行
	 *
	 * @param timeout
	 *            时间长度
	 * @param unit
	 *            时间单位，有效到微秒
	 * @return 是否成功
	 */
	public synchronized boolean tryAcquire(long timeout, TimeUnit unit) {
		long millis = unit.toMillis(timeout);
		while (clearExpired()) {
			// 快速返回
			if (lastExpiredTime() > millis)
				return false;
			waitExpired();
		}
		offer();
		return true;
	}

	private void offer() {
		queue.offer(period + System.currentTimeMillis());
		counter++;
	}

	private boolean clearExpired() {
		if (counter < maxPermits)
			return false;
		long now = System.currentTimeMillis();
		Long t = queue.peek();
		while (t != null && t < now) {
			queue.poll();
			counter--;
			t = queue.peek();
		}
		// 没有过期则需要等待
		return counter == maxPermits;
	}

	private long lastExpiredTime() {
		System.out.println(queue.peek() - System.currentTimeMillis());
		return queue.peek() - System.currentTimeMillis();
	}

	private void waitExpired() {
		sleep(lastExpiredTime());
	}

	private void sleep(long sleep) {
		try {
			TimeUnit.MILLISECONDS.sleep(sleep);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

}
