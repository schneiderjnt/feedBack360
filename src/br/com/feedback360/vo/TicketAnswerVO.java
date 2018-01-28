package br.com.feedback360.vo;

import java.util.List;

public class TicketAnswerVO {
	
	private int id;
	private List<Integer> answers;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public List<Integer> getAnswers() {
		return answers;
	}
	public void setAnswers(List<Integer> answers) {
		this.answers = answers;
	}
	
}
