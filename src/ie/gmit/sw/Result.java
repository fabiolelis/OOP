package ie.gmit.sw;

/*
 * Result class
 * Represents each attempt to break the cypher
 */


public class Result {
	private int key;
	private String text;
	private Double score;
	
	public Result(){
		super();
	}
	
	public Result(int key, String text, Double score) {
		super();
		this.key = key;
		this.text = text;
		this.score = score;
	}
	
	@Override
	public String toString(){
		return "Result {"+
				"\nkey: "+ key +
				"\ntext: "+ text +
				"\nscore: " + score;
	}
	
	
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
	
}
