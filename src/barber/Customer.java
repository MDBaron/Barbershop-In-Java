package barber;

public class Customer implements Runnable {
	
	int id;
	int serviceLengthMSec;
	final Shop shop;
	
	boolean waiting;
	public Customer(int id, int serviceLengthMSec, Shop shop) {
		
		this.id = id;
		this.serviceLengthMSec = serviceLengthMSec;
		this.shop = shop;
	}
	
	public void run() {
	
		Thread cust = new Thread();
		cust.start();
		while(shop.barberQueue.isEmpty()){
			if(shop.customerEnters()){
				waiting = true;
				try {
					
					shop.addCustomer(cust);
					//wait();
					Thread.currentThread().sleep(100);
					System.out.println("Customer " + id + " starts waiting at position " + shop.customerQueue.size()  );
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}//catch
				waiting = false;
				//notify();
			
			} else {
				shop.noVacancy();
				System.out.println("Customer " + id +" leaves without service");
				break;
			}//else2
		} while(!(shop.barberQueue.isEmpty())) {
			Thread barbHold = shop.hailBarber();
			try {
				if(barbHold != null){
					shop.chopAndAShave(Thread.currentThread());
				Thread.currentThread().interrupt();
				} else {
					Thread.currentThread().sleep(100);
				}//else
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}//catch
			
		}//else1
	}//run
}
