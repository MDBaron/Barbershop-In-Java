package origBARB;

import java.util.*;
import java.math.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Shop {

	int waiting;
	int left;
	int served;
	int shopSeatCap;
	boolean isRunning = false;
	long buffer;
	long bumper;
	Queue<Thread> barberQueue = new ConcurrentLinkedQueue<Thread>();
	Queue<Thread> customerQueue = new ConcurrentLinkedQueue<Thread>();
	
	
	public Shop(int seatCap, int minInterval, int maxInterval) {
		
		this.shopSeatCap = seatCap;
		isRunning = true;
		this.buffer = minInterval;
		this.bumper = maxInterval;
		
	}
	
	public synchronized Thread hailBarber(){
		return barberQueue.remove();
	}//hailBarber
	
	public synchronized Thread hailCustomer(){
		Thread hold = customerQueue.remove();
		notifyAll();
		
		return hold;
	}//hailCustomer
	
	
	
	public synchronized void addBarber(Thread barb){
		barberQueue.add(barb);
		notifyAll();
	}//addBarber
	
	public synchronized void addCustomer(Thread cust){
		customerQueue.add(cust);
	
		notifyAll();
	}//addCustomer
	public synchronized void chopAndAShave(Thread cust) throws InterruptedException{
		if(!(barberQueue.isEmpty())){
			Thread barbUp = hailBarber();
			barbUp.notify();
		}
		
	}//chopAndAShave
	public synchronized Thread chopAndAShave() throws InterruptedException{
		if(!(customerQueue.isEmpty())){
			Thread Crem = customerQueue.remove();
			--waiting;
			++served;
			//Thread.currentThread().sleep(1000);
			notifyAll();
			return Crem;
		} else {
			return Thread.currentThread();
		}//else
	}//we'll take you now....
	
	public synchronized void jobFinished(Thread barb) throws InterruptedException{
		++left;
		++served;
		addBarber(barb);
		//Thread.currentThread().sleep(1000);
		Random rand = new Random();
		int segment = rand.nextInt((int)(bumper - buffer));
		//this.sleep(segment);
		notifyAll();
	}//jobFinished
	
	public synchronized void noVacancy(){
		Thread.currentThread().interrupt();
		++left;
	}//noVacancy
	
	public synchronized boolean customerEnters(){
		if(customerQueue.size() < shopSeatCap){
			++waiting;
			notifyAll();
			return true;
		} else {
			++left;
			notifyAll();
			return false;
		}//else
		
	}
	
	public synchronized Boolean isInService() {
		
		return isRunning;
	}
}
