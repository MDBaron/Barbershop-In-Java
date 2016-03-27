package barber;

import java.util.*;

public class CustomerGenerator implements Runnable {
	
	final int startNum, numToGenerate,
			  serviceTimeMin, serviceTimeMax,
			  intervalTimeMin, intervalTimeMax;
	final Shop shop;
	Queue<Thread> customers = new LinkedList<Thread>();
	
	
	public CustomerGenerator(int startNum, int numToGenerate, int serviceTimeMin, 
			int serviceTimeMax, int intervalTimeMin, int intervalTimeMax, Shop shop) {
		this.startNum = startNum;
		this.numToGenerate = numToGenerate;
		this.serviceTimeMin = serviceTimeMin;
		this.serviceTimeMax = serviceTimeMax;
		this.intervalTimeMin = intervalTimeMin;
		this.intervalTimeMax = intervalTimeMax;
		this.shop = shop;
	}
	
	public void run() {
		try {
			for (int i = startNum; i < startNum+numToGenerate; i++) {
				customers.add( (new Thread(
						new Customer(i, 
								(new Random()).nextInt(serviceTimeMax-serviceTimeMin)+serviceTimeMin, shop))));
						customers.element().start();
				shop.addCustomer(customers.remove());
				Thread.sleep((new Random()).nextInt(intervalTimeMax-intervalTimeMin)+intervalTimeMin);
			}
		} catch (InterruptedException e) {}
	}
}
