package com.bookstore.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.entity.Configuration;
import com.bookstore.entity.Question_Table;
import com.bookstore.entity.User_Answers;


@Repository
public class QuestionDAO implements IQuestionDAO {
	
	@PersistenceContext
	private EntityManager entityManager;


	@SuppressWarnings("unchecked")
	@Override
	public List<Question_Table> getQuestions(int firstResult,int pageSize,boolean random,String subCode) {
	String hql=null; 
	if(subCode==null || subCode.equals("0")){
		hql = "FROM Question_Table as atcl where atcl.active=0";
	}else{
		hql = "FROM Question_Table as atcl where atcl.active=0 and subcode="+subCode;
	}
	
	if(random){
		hql+=" order by RAND()";
	}
	
		Query query=entityManager.createQuery(hql);
		query.setFirstResult(firstResult-1);
		query.setMaxResults(pageSize);
		return (List<Question_Table>) query.getResultList();
	}
	
	@Override
	public List<Question_Table> getQuestions_withQids(List<Integer> qlist) {
		String orderString=qlist.stream().map(String::valueOf)
		        .collect(Collectors.joining(","));
	    String hql=null; 
		hql = "FROM Question_Table q WHERE q.qid IN (:qlist) order by field(q.qid,"+orderString+")";
		Query query=entityManager.createQuery(hql);

		query.setParameter("qlist", qlist);
		return (List<Question_Table>) query.getResultList();
	}
	
	
	@Override
	public List<Configuration> loadConfiguration() {
		String hql = "FROM Configuration as c where c.active=0";
		return (List<Configuration>) entityManager.createQuery(hql).getResultList();
	}
	
	@Override
	public User_Answers loadUserAnswers(String userId) {
		String hql = "FROM User_Answers as c where c.userid=:userId";
		Query query=entityManager.createQuery(hql);
		query.setParameter("userId", userId);
		System.out.println(userId);
		List<User_Answers> useranslist=(List<User_Answers>) query.getResultList();
		if(useranslist.size()!=0)
		return useranslist.get(0);
		else
		return null;
	}
	
	
	@Override
   public String postUser_Answers(String userid,String answers) {
		Boolean inserted=false;
		User_Answers userans=new User_Answers();
		userans.setAnswers(answers);
		userans.setUserid(userid);
		try{
		entityManager.persist(userans);
		entityManager.flush();
		return userid;
		} catch(Exception e){
    	   e.printStackTrace();
       }
		inserted=true;
		return null;
	}
	
}
