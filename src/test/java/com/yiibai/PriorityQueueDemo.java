package com.yiibai;

import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;

import com.tairanchina.fd.common.utils.RateLimiter;

public class PriorityQueueDemo {
	private static RateLimiter limiter = new RateLimiter(2, 1, TimeUnit.SECONDS);
   public static void main(String args[]) {
      // create priority queue
      PriorityQueue < Integer >  prq = new PriorityQueue < Integer > ();

      // insert values in the queue
      for ( int i = 3; i  <  10; i++ ){
         prq.add (new Integer (i)) ;
      }

      System.out.println ( "Initial priority queue values are: "+ prq);

      // get the head from the queue
      Integer head = prq.peek();

      System.out.println ( "Head of the queue is: "+ head);
      for (int i = 0; i < 100; i++) {
    	  limiter.acquire();

	}

   }
}