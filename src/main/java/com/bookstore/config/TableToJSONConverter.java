package com.bookstore.config;

import java.util.List;

import com.bookstore.entity.Question_Table;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * 
 * @author javacodepoint.com
 *
 */
public class TableToJSONConverter {

	private ObjectMapper mapper = new ObjectMapper();

	
	public JsonNode listToJson(List<Question_Table> question_Tablelist) {
		ObjectNode questionDataNode = mapper.createObjectNode();
		ArrayNode questionDataArray = mapper.createArrayNode();
		ObjectNode optionDataNode = mapper.createObjectNode();
		
		
		for (int i = 0; i< question_Tablelist.size(); i++) {
			ArrayNode arrayOptionData = mapper.createArrayNode();
			ObjectNode questionData = mapper.createObjectNode();
			Question_Table question_Table=question_Tablelist.get(i);
			
			 int answer=Integer.parseInt(question_Table.getAns());
			 
            //setting question data
			questionData.put("questionId", question_Table.getQid());
			questionData.put("questionText", question_Table.getQtext());
			questionData.put("explanation", question_Table.getExplanation());
			
			ObjectNode optionData1 = mapper.createObjectNode();
			optionData1.put("option_id",1 );
			optionData1.put("text", question_Table.getOption1());
			if(answer==1)
				optionData1.put("correct", true);	
			arrayOptionData.add(optionData1);
			
			ObjectNode optionData2 = mapper.createObjectNode();
			optionData2.put("option_id",2 );
			optionData2.put("text", question_Table.getOption2());
			if(answer==2)
				optionData2.put("correct", true);
			arrayOptionData.add(optionData2);
			
			ObjectNode optionData3 = mapper.createObjectNode();
			optionData3.put("option_id",3);
			optionData3.put("text", question_Table.getOption3());
			if(answer==3)
				optionData3.put("correct", true);
			arrayOptionData.add(optionData3);
			
			ObjectNode optionData4 = mapper.createObjectNode();
			optionData4.put("option_id",4 );
			optionData4.put("text", question_Table.getOption4());
			if(answer==4)
				optionData4.put("correct", true);
			arrayOptionData.add(optionData4);
			
			optionDataNode.set("options", arrayOptionData);
			questionData.setAll(optionDataNode);
			questionDataArray.add(questionData);
		}
		questionDataNode.set("questions", questionDataArray);

		
		return questionDataNode;
	}
	
	
}
