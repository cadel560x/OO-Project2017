package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Launcher {
	public void Launch(String f1, String f2, int  shingleSize, int k, int poolSize) throws InterruptedException {
		BlockingQueue<Shingle> q = new LinkedBlockingQueue<>();
		
		Thread t1 = new Thread(new DocumentParser(f1, q, shingleSize, 1), "T1");
		Thread t2 = new Thread(new DocumentParser(f2, q, shingleSize, 2), "T2");
		Thread t3 = new Thread(new Consumer(q, k, poolSize), "T3");
		
		t1.start();
		t2.start();
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
		
	} // Launch
	
} // class Launcher
