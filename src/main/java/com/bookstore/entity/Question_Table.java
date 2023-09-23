package com.bookstore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="question_table")
public class Question_Table {

@Id
@GeneratedValue(strategy=GenerationType.AUTO)	
  private Integer qid;
  private String ans;
  private String qtext;
  private String option1;
  private String option2;
  private String option3;
  private String option4;
  private String explanation;
  private String subcode;
  private boolean active;
  
public Integer getQid() {
	return qid;
}
public String getAns() {
	return ans;
}
public String getQtext() {
	return qtext;
}
public String getOption1() {
	return option1;
}
public String getOption2() {
	return option2;
}
public String getOption3() {
	return option3;
}
public String getOption4() {
	return option4;
}
public String getExplanation() {
	return explanation;
}
public boolean isActive() {
	return active;
}
public void setQid(Integer qid) {
	this.qid = qid;
}
public void setAns(String ans) {
	this.ans = ans;
}
public void setQtext(String qtext) {
	this.qtext = qtext;
}
public void setOption1(String option1) {
	this.option1 = option1;
}
public void setOption2(String option2) {
	this.option2 = option2;
}
public void setOption3(String option3) {
	this.option3 = option3;
}
public void setOption4(String option4) {
	this.option4 = option4;
}
public void setExplanation(String explanation) {
	this.explanation = explanation;
}
public String getSubcode() {
	return subcode;
}
public void setSubcode(String subcode) {
	this.subcode = subcode;
}
public void setActive(boolean active) {
	this.active = active;
}


}
