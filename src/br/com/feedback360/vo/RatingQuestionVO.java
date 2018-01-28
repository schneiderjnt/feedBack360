package br.com.feedback360.vo;

import java.util.List;

public class RatingQuestionVO {
	
	private int id;
	private String text;
	private List<RatingOptionVO> options;
	
	
	public RatingQuestionVO(int id, String text, List<RatingOptionVO> options) {
		super();
		this.id = id;
		this.text = text;
		this.options = options;
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
	
	public List<RatingOptionVO> getOptions() {
		return options;
	}
	public void setOptions(List<RatingOptionVO> options) {
		this.options = options;
	}

	
}
