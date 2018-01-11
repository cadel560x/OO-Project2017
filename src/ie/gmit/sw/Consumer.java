package ie.gmit.sw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class Consumer implements Runnable {
//  Fields
	private BlockingQueue<Shingle> queue;
	private int k;
	private int[] minHashes;
	private Map<Integer,List<Integer>>map = new HashMap<>();
	private ExecutorService pool;
	
	
	
	
//	Constructors
	public Consumer() {
		
	}
	
	public Consumer(BlockingQueue<Shingle> q, int k, int poolSize) {
		this.queue = q;
		this.k = k;
		this.pool = Executors.newFixedThreadPool(poolSize);
		init();
		
	}
	
	
	
	
//	Accessors and mutators
	
	
	
	
	
//	Methods
	public void init() {
		Random random = new Random();
		minHashes = new int[k];
		
		for (int i = 0; i < minHashes.length; i++) {
			minHashes[i] = random.nextInt();
		}
		
	} // init
	
	
	@Override
	public void run() {
		int docCount = 2;
		
		while( docCount > 0 ) {
			try {			
				Shingle s = queue.take();
				if ( s instanceof Poison ) {
					docCount--;
				}			
				else {
					pool.execute( new Runnable() {
						
						public void run() {
							for (int i = 0; i < minHashes.length; i++) {
								int value = s.getHashCode()^minHashes[i];
								List<Integer> list =  map.get(s.getDocId());
								
								if (list == null) {
									list = new ArrayList<Integer>(k);
									
									for ( int j = 0; j < list.size(); j++ ) {
										list.set(j, Integer.MAX_VALUE);
										
									} // for
									
									map.put(s.getDocId(), list);
								}
								else {
									if ( list.get(i) > value ) {
										list.set(i, value);
									}
								} // if - else
								
							} // for
						
						} // run
						
					}); // pool.execute( new Runnable()
				
				} // if - else

			} catch (InterruptedException e) {
				e.printStackTrace();
			} // try - catch
			
		} // while
		
		List<Integer> intersection = map.get(1);
		intersection.retainAll(map.get(2));
		float jacquard = (float) intersection.size()/(k*2-(float)intersection.size());
		
		System.out.println("Document matching index: " + jacquard + "%");
		
	} // run

} // class Consumer
