package br.com.feedback360.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

public class RatingQuestionnaireVO {
	
	private int id;
	private int count;
	private String title;
	private String description;
	private List<RatingQuestionVO> questions;
	
	@JsonIgnore
	private Map<Integer,RatingOptionVO> mapOption;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<RatingQuestionVO> getQuestions() {
		return questions;
	}
	public void setQuestions(List<RatingQuestionVO> questions) {
		this.questions = questions;
	}
	
	public void incrementOption(int optionId){
		
		if(this.mapOption==null){
			this.mapOption = new HashMap<Integer,RatingOptionVO>();
			
			for(RatingQuestionVO quest:this.questions){
				for(RatingOptionVO option:quest.getOptions()){
					this.mapOption.put(option.getId(), option);
				}
			}
		}
		this.mapOption.get(optionId).incrementCount();
	}
	
	public void incrementCount(){
		this.count++;
	}
	
}
