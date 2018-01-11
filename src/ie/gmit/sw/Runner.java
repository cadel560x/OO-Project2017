package ie.gmit.sw;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;




/**
 * 
 * Entry point of the application
 * 
 * @author Javier Mantilla
 *
 */
public class Runner {
//	Entry point
	public static void main(String[] args) {
		BlockingQueue<Shingle> q = new LinkedBlockingQueue<Shingle>();
		Scanner scanner = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		String file1, file2;
		int shingleSize;
		int poolSize;
		int k; // Amount of shingle samples
		
		sb.append("Object Oriented Programming Project 2017\n"
				+ "========================================\n\n"
				+ "Document similarity program using Jaccard index\n"
				+ "-----------------------------------------------\n\n"
				+ "Notice: Documents must be plain text files!!\n\n"
				+ "Please enter filepath of the first document: ");		
		System.out.print(sb.toString());
		
		file1 = scanner.nextLine();
		
		sb.setLength(0);
		sb.append("Please enter filepath of the second document: ");
		System.out.print(sb.toString());
		
		file2 = scanner.nextLine();
		
		sb.setLength(0);
		sb.append("Please enter the shingle size [in words]: ");
		System.out.print(sb.toString());
		shingleSize = Integer.parseInt(scanner.nextLine());
		
		sb.setLength(0);
		sb.append("Please enter the amount of minhashes: ");
		System.out.print(sb.toString());
		k = Integer.parseInt(scanner.nextLine());
		
		sb.setLength(0);
		sb.append("Please enter threadpool size: ");
		System.out.print(sb.toString());
		poolSize = Integer.parseInt(scanner.nextLine());
		
		scanner.close();
		System.out.println();
		
		Thread dp1 = new Thread(new DocumentParser(file1, shingleSize, q, 1), "DocumentParser1");
		Thread dp2 = new Thread(new DocumentParser(file2, shingleSize, q, 2), "DocumentParser2");
		Thread consumer = new Thread(new Consumer(q, k, poolSize), "Consumer");
		
		dp1.start();
		dp2.start();
		consumer.start();
		
		try {
			dp1.join();
			dp1.join();
			consumer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // try - catch

	} // main

} // class Runner
