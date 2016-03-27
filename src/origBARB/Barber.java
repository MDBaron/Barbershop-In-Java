package origBARB;

public class Barber implements Runnable {
	int id;
	final int maxWorkTime;
	final Shop shop;
	long start; 
	boolean sleeping;
	final int maxServiceTime;
	Thread crem;
	
	
	public Barber(int id, int maxWorkTime, Shop shop, int maxServiceTime) {
		
		this.id = id;
		this.maxWorkTime = maxWorkTime;
		this.shop = shop;
		this.maxServiceTime = maxServiceTime;
	}//constructor
	
	public void run() {
		
		Thread barb = new Thread();
		barb.start();
		start = System.currentTimeMillis();
		while((System.currentTimeMillis() - start) < maxWorkTime){
			while(shop.waiting == 0){
				sleeping = true;
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}//try catch
				sleeping = false;
			}//while
			crem = shop.hailCustomer();
			if(crem != null){
			try {
				barb.sleep(maxServiceTime);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}//catch
			
			//barb.sleep(1000);
			
			System.out.println("Barber " + id + " starts serving " + crem );
			try {
				shop.jobFinished(barb);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}//catch
			shop.addBarber(barb);
			System.out.println("Barber " + id + " finishes serving " + crem );
			//this.notify();
			} else {
				try {
					barb.sleep(maxServiceTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}//while
		System.out.println("Barber " + id  + " quits");
		shop.isRunning = false;
	}//run
}//Barber Class
