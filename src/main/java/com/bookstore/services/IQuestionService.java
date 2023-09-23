package com.bookstore.services;

import java.util.List;

import com.bookstore.entity.Question_Table;

public interface IQuestionService {
	List<Question_Table> getQuestions(String subCode);
	void loadConfiguration();
	String postUser_Answers(String userid, String answers);
	List<String[]> loadUserAnswers(String userId);


}
