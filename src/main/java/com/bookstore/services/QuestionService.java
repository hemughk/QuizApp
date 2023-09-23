package com.bookstore.services;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.dao.IQuestionDAO;
import com.bookstore.entity.Configuration;
import com.bookstore.entity.Question_Table;
import com.bookstore.entity.User_Answers;
import com.bookstore.vo.Answer;
import com.google.gson.Gson;

@Service
public class QuestionService implements IQuestionService {

	@Autowired
	private IQuestionDAO dao;

	int recordCount = 10;
	int recordFrom = 1;
	boolean random = false;

	@Override
	public List<Question_Table> getQuestions(String subCode) {
		loadConfiguration();
		return dao.getQuestions(recordFrom, recordCount, random, subCode);
	}

	@Override
	public void loadConfiguration() {
		List<Configuration> configData = dao.loadConfiguration();
		for (Configuration configRecord : configData) {
			switch (configRecord.getProperty()) {
			case "count":
				recordCount = Integer.parseInt(configRecord.getVal());
				break;
			case "random":
				random = Boolean.parseBoolean(configRecord.getVal());
				break;
			case "recordnofrom":
				recordFrom = Integer.parseInt(configRecord.getVal());
				break;
			default:
				break;
			}

		}
	}

	@Override
	@Transactional
	public String postUser_Answers(String userid, String answers) {
		return dao.postUser_Answers(userid, answers);
	}

	@Override
	public List<String[]> loadUserAnswers(String userId) {
		List<String[]> csvRows = new ArrayList<String[]>();
		User_Answers User_Answers = dao.loadUserAnswers(userId);
		JSONArray ansArray = new JSONArray(User_Answers.getAnswers());
		Gson gson = new Gson();
		List<Integer> qlist = new ArrayList<Integer>();
		List alist = new ArrayList();
		for (int i = 0; i < ansArray.length(); i++) {
			JSONObject ansjsonObject = ansArray.getJSONObject(i);
			Answer ansObject = gson.fromJson(ansjsonObject.toString(), Answer.class);
			qlist.add(Integer.valueOf(ansObject.getqNo()));
			alist.add(Integer.valueOf(ansObject.getAns()));
		}

		if(qlist.size()>0){
		List<Question_Table> questions = dao.getQuestions_withQids(qlist);
		for (int i = 0; i < questions.size(); i++) {
			Question_Table qrow = questions.get(i);
			String[] sheetRow = new String[8];
			sheetRow[0] = (i + 1) + "";
			sheetRow[1] = qrow.getQtext();
			sheetRow[2] = qrow.getOption1();
			sheetRow[3] = qrow.getOption2();
			sheetRow[4] = qrow.getOption3();
			sheetRow[5] = qrow.getOption4();
			sheetRow[6] = qrow.getAns();
			sheetRow[7] = alist.get(i) + "";
			// sheetRow[8]=qrow.getExplanation();
			csvRows.add(sheetRow);
		}
	}	
		return csvRows;	
		}

}
