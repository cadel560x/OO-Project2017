package ie.gmit.sw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;




/**
 * 
 * This class gets shingles from a BlockingQueue and throws them into threadpool.
 * The workers in the threadpool calculate the minhash for each shingle.
 * After all minhashes are computed, the Jaccard similarity index is calculated.
 * 
 * @author Javier Mantilla
 *
 */
public class Consumer implements Runnable {
//  Fields
	private BlockingQueue<Shingle> queue;
	private int k;
	private int[] minHashes;
	private ConcurrentMap<Integer,List<Integer>> map = new ConcurrentHashMap<Integer, List<Integer>>();
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
							List<Integer> list =  map.get(s.getDocId());
							
							for (int i = 0; i < minHashes.length; i++) {
								int value = s.getHashCode()^minHashes[i];
								if (list == null) {
									list = new ArrayList<Integer>(Collections.nCopies(k, Integer.MAX_VALUE));
									map.put(s.getDocId(), list);
								}
								else {
									if ( list.get(i) > value ) {
										list.set(i, value);
									}
								} // if - else
								
							} // for
							
							map.put(s.getDocId(), list);
						
						} // run
						
					}); // pool.execute( new Runnable()
				
				} // if - else

			} catch (InterruptedException e) {
				e.printStackTrace();
			} // try - catch
			
		} // while
		
		pool.shutdown();
		try {
			pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		List<Integer> intersection = map.get(1);
		intersection.retainAll(map.get(2));
		float jaccard = (float) intersection.size()/(k*2-(float)intersection.size());
		
		System.out.println("Document matching index: " + jaccard + "%");
		
	} // run

} // class Consumer
