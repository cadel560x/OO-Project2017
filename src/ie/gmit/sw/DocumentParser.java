package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;




public class DocumentParser implements Runnable {
//  Fields
	private BlockingQueue<Shingle> queue;
	private int docId;
	private String file;
	private int ss;
	private Deque<String> buffer = new LinkedList<>();
	
	
	
	
//	Constructors
	public DocumentParser(String file, BlockingQueue<Shingle> q, int ss, int docId) {
		this.queue = q;
		this.file = file;
		this.ss = ss;
		this.docId = docId;
	}
	
	
	
	
//	Methods
	@Override
	public void run() {
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			
			while ( (line = br.readLine()) != null ) {
				String uLine = line.toUpperCase();
				String [] words = uLine.split(" ");
				addWordsToBuffer(words);
				Shingle s = getNextShingle();
				queue.put(s);
			} // while
			
			flushBuffer();
			br.close();
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} // try - catch
		
	} // run
	
	
	private void addWordsToBuffer(String [] words) {
		for (String s: words) {
			buffer.add(s);
		}
	} // addWordsToBuffer
	
	
	private Shingle getNextShingle() {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		
		while ( counter < ss ) {
			if (buffer.peek() != null ) {
				sb.append(buffer.poll());
			}
			
			counter++;
		} // while
		
		if (sb.length() > 0) {
			return ( new Shingle(docId, sb.toString().hashCode()) );
		}
		else {
			return null;
		} // if - else
		
	} // getNextShingle
	
	
	private void flushBuffer() throws InterruptedException {
		while (buffer.size() > 0 ) {
			Shingle s = getNextShingle();
			
			if ( s != null ) {
				queue.put(s);
			}
			else {
				queue.put(new Poison(docId, 0));
			} // if - else
			
		} // while
		
	} // flushBuffer
	
} // class DocumentParser
