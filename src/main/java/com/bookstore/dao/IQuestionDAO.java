package com.bookstore.dao;

import java.util.List;
import com.bookstore.entity.Configuration;
import com.bookstore.entity.Question_Table;
import com.bookstore.entity.User_Answers;

public interface IQuestionDAO {
	List<Configuration> loadConfiguration();
	List<Question_Table> getQuestions(int firstResult, int pageSize,boolean random,String subCode);
	String postUser_Answers(String userid, String answers);
	User_Answers loadUserAnswers(String userId);
	List<Question_Table> getQuestions_withQids(List<Integer> qlist);
}
