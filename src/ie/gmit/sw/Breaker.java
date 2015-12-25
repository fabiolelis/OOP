package ie.gmit.sw;

/*
 * Breaker class.
 * runs a set of decrypters threads and put the results in a blocking queue
 * runs a consumer taker that give back the maximum score
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Breaker {
	
	private String cypherText;
	private BlockingQueue<Result> resultsQueue;
	private int poolSize;
	public static QuadGramMap qgmap; 
	public static boolean poison = false;
	
	public Breaker(String cypherText) {
		super();
		this.cypherText = cypherText;
		this.poolSize = this.cypherText.length()/2-1;
		resultsQueue = new ArrayBlockingQueue<Result>(this.poolSize);
		
		qgmap = new QuadGramMap();
		qgmap.importContent();
	}
	
	public Result breakit(){
		
		ExecutorService breakerExecutor = Executors.newFixedThreadPool(this.poolSize);
		ExecutorService takerExecutor = Executors.newFixedThreadPool(1);

		Runnable taker = new Taker();
		takerExecutor.execute(taker);
		for (int i = 2; i < this.poolSize+2; i++) {
			
			Runnable worker = new Decrypter(i);
			breakerExecutor.execute(worker);
		}

		breakerExecutor.shutdown();
		poison = true;
		
		takerExecutor.shutdown();
		
		return ((Taker)taker).getMaxResult();		
		
	}
	
	
	class Taker implements Runnable{

		private double maxScore = 0;
		private Result maxResult;
		
		@Override
		public void run() {
			while(!poison){
				Result r;
				try {
					r = resultsQueue.take();
					System.out.println("tookkey " + r.getKey());

					if(r.getScore() > maxScore){
						maxResult = r;
						maxScore = r.getScore();
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
		public Result getMaxResult(){
			return maxResult;
		}
	}

	class Decrypter implements Runnable{

		private int key;
		
		public Decrypter(int key){
			this.key = key;
		}
		
		@Override
		public void run() {
			String text = decrypt(key);
			TextScorer ts = new TextScorer(Breaker.qgmap.getContent());
			
			try {
				System.out.println("putkey " + key);
				resultsQueue.put(new Result(key, text, ts.getScore(text)));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//***** Decrypt a String cypherText using an integer key ***** 
		public String decrypt(int key){
			//Declare a 2D array of key rows and text length columns
			char[][] matrix = new char[key][cypherText.length()]; //The array is filled with chars with initial values of zero, i.e. '0'.
			
			//Fill the array
			int targetRow = 0;
			int index = 0;
			do{
				int row = 0; //Used to keep track of rows		
				boolean down = true; //Used to zigzag
				for (int i = 0; i < cypherText.length(); i++){ //Loop over the plaintext
					if (row == targetRow){
						matrix[row][i] = cypherText.charAt(index); //Add the next character in the plaintext to the array
						index++;
					}
					
					if (down){ //If we are moving down the array
						row++;
						if (row == matrix.length){ //Reached the bottom
							row = matrix.length - 2; //Move to the row above
							down = false; //Switch to moving up
						} 
					}else{ //We are moving up the array
						row--;
						
						if (row == -1){ //Reached the top
							row = 1; //Move to the first row
							down = true; //Switch to moving down
						}
					}
				}

				targetRow++;
			}while (targetRow < matrix.length);
			
			//printMatrix(matrix); //Output the matrix (debug)
			
			//Extract the cypher text
			StringBuffer sb = new StringBuffer(); //A string buffer allows a string to be built efficiently
			int row = 0;
			boolean down = true; //Used to zigzag
			for (int col = 0; col < matrix[row].length; col++){ //Loop over each column in the matrix
				sb.append(matrix[row][col]); //Extract the character at the row/col position if it's not 0.
				
				if (down){ //If we are moving down the array
					row++;
					if (row == matrix.length){ //Reached the bottom
						row = matrix.length - 2; //Move to the row above
						down = false; //Switch to moving up
					} 
				}else{ //We are moving up the array
					row--;
					
					if (row == -1){ //Reached the top
						row = 1; //Move to the first row
						down = true; //Switch to moving down
					}
				}

			}
			
			return sb.toString(); //Convert the StringBuffer into a String and return it
		}
		
	}
	
	
}
