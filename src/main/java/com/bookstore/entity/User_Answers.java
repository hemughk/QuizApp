package com.bookstore.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_answers")
public class User_Answers {

@Id
@GeneratedValue(strategy=GenerationType.AUTO) 
private Integer ansid;	
  private String userid;
  private String answers;
  private Timestamp start_date;
  
  
public Integer getAnsid() {
	return ansid;
}
public void setAnsid(Integer ansid) {
	this.ansid = ansid;
}
public String getUserid() {
	return userid;
}
public String getAnswers() {
	return answers;
}
public Timestamp getStart_date() {
	return start_date;
}
public void setUserid(String userid) {
	this.userid = userid;
}
public void setAnswers(String answers) {
	this.answers = answers;
}
public void setStart_date(Timestamp start_date) {
	this.start_date = start_date;
}
}
