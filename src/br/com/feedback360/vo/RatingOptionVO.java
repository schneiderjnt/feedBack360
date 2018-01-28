package br.com.feedback360.vo;

public class RatingOptionVO implements Comparable<RatingOptionVO>{
	
	private int id;
	private String text;
	private int count;
	private String rate;
			
	public RatingOptionVO(int id, String text, int count) {
		super();
		this.id = id;
		this.text = text;
		this.count = count;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public void incrementCount(){
		this.count++;
	}

	@Override
	public int compareTo(RatingOptionVO o) {
		return ((Integer) this.count).compareTo(o.getCount());
	}

	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
}