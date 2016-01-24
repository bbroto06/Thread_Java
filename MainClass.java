import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainClass {
	public static int threadCount = 4;
	public static int stop = 800000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Spawning threads ...");
		ArrayList<Thread> threads = new ArrayList<Thread>();
		int incrementAmount = stop/threadCount;
		int starting = 1;
		for(int i=0; i<threadCount; i++){
			if(!((i+1) == threadCount)){
				threads.add(new Thread(new PrimeThread(starting, starting+incrementAmount, i + ".txt", false)));
				starting += incrementAmount;
			}
			else{
				threads.add(new Thread(new PrimeThread(starting, starting+incrementAmount, i + ".txt", true)));
			}
		}
		
		for(int i=0; i<threads.size(); i++){
			threads.get(i).start();
		}
		
		for(int i=0; i<threads.size(); i++){
			try {
				threads.get(i).join();
				System.out.println("Thread " + i + " finished");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ArrayList<Integer> primes = new ArrayList<Integer>();
		for (int i = 0; i < threads.size(); i++) {
			try {
				File f = new File(i+".txt");
				Scanner scan = new Scanner(f);
				while(scan.hasNextInt()){
					primes.add(scan.nextInt());
				}
				scan.close();
				f.delete();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			PrintWriter print = new PrintWriter(new File("primes.txt"));
			for (int i = 0; i < primes.size(); i++) {
				print.println(primes.get(i));
			}
			print.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
