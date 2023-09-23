package com.bookstore.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.config.TableToJSONConverter;
import com.bookstore.entity.Question_Table;
import com.bookstore.services.IQuestionService;
import com.bookstore.vo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

@CrossOrigin
@RestController
@RequestMapping("api")
public class MCQController {
	@Autowired
	private IQuestionService service;

	@GetMapping("questions")
	public ResponseEntity<String> getQuestions(@RequestParam(required = false) String studCode) {
		String prettyString = "";
		try {
			if(studCode.equalsIgnoreCase("Synonyms"))
				studCode="103";
			if(studCode.equalsIgnoreCase("Antonyms"))
				studCode="104";
			if(studCode.equalsIgnoreCase("Direct-Indirect"))
				studCode="107";
			if(studCode.equalsIgnoreCase("Prepositions"))
				studCode="106";
			if(studCode.equalsIgnoreCase("Idioms"))
				studCode="108";
			if(studCode.equalsIgnoreCase("General"))
				studCode="102";
			

		List<Question_Table> question_Tablelist = service.getQuestions(studCode);
		TableToJSONConverter tableToJSONConverter = new TableToJSONConverter();
		JsonNode jsonnode = tableToJSONConverter.listToJson(question_Tablelist);
		ObjectMapper mapper = new ObjectMapper();
		prettyString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonnode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(prettyString, HttpStatus.OK);
	}

	@PostMapping("answers")
	public ResponseEntity<String> postAnswers(@RequestBody String answers) {
		System.out.println("answers-->" + answers);
		Gson gson = new Gson();

		JSONObject jsnobject = new JSONObject(answers);
		JSONArray ansArray = jsnobject.getJSONArray("answers");
		JSONArray userArray = jsnobject.getJSONArray("user");
		JSONObject usrjsonObject = userArray.getJSONObject(0);
		User usrObject = gson.fromJson(usrjsonObject.toString(), User.class);
		String usrstr = usrObject.getUser() + "_" + usrObject.getVal();
		String userId = service.postUser_Answers(usrstr, ansArray.toString());
		
		if(null==userId){
			userId="";
		}
		userId=gson.toJson(userId);

		return new ResponseEntity<>(userId, HttpStatus.OK);
	}

	@GetMapping("download")
	public ResponseEntity<String> downloadUserData(@RequestParam(required = false) String userstr) throws IOException {

		String base64String = null;
		userstr = userstr.replaceAll("\"", "");
		try {
			String[] headerRecord = { "Id", "Question", "Option1", "Option2", "Option3", "Option4" };
			List rows = service.loadUserAnswers(userstr);
			base64String = convertToBase64PdfString(userstr, headerRecord, rows);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(base64String, HttpStatus.OK);
	}

	@SuppressWarnings("unused")
	private String convertToBase64PdfString(String filename, String[] header, List rows) throws IOException {
		String base64Data = null;
		filename = "./"+filename+".pdf";
		Table table = new Table(new float[] { 0.5f, 5, 2, 2, 2, 2});
		table.setWidthPercent(100);
		PdfDocument pdfDoc = new PdfDocument(new PdfWriter(filename));
		Document doc = new Document(pdfDoc);
		Paragraph para = new Paragraph("User Answers").setFontColor(new DeviceRgb(8, 73, 117))
				.setTextAlignment(TextAlignment.CENTER).setFontSize(20f);
		doc.add(para);

		for (int j = 0; j < header.length; j++) {
			Cell cell = new Cell();
			Color headerBg = new DeviceRgb(0xA6, 0xCB, 0x0B);
			cell.setBackgroundColor(headerBg);
			table.addCell(cell.add(header[j]));
		}
		String[] row = new String[rows.size()];

		for (Object rowobj : rows) {
			row = (String[]) rowobj;
			for (int j = 0; j < header.length; j++) {
				String corAns=row[6];
				String wrongAns=row[7];
				Cell cell = new Cell();
				if (row[j] == null)
					row[j] = "";
				Color correctAnsBgcolr = new DeviceRgb(60, 179, 113);
				Color wrongAnsBgcolr = new DeviceRgb(255, 0, 0);
            /* Color changing on error or ans  */
				if(row[6].equals(row[7])){
					if(j==Integer.valueOf(corAns)+1)
					cell.setBackgroundColor(correctAnsBgcolr);
				}else{
					if(j==Integer.valueOf(corAns)+1)
						cell.setBackgroundColor(correctAnsBgcolr);
					if(j==Integer.valueOf(wrongAns)+1)
						cell.setBackgroundColor(wrongAnsBgcolr);
				}
				
				table.addCell(cell.add(row[j]));
			}
		}
		doc.add(table);
		doc.close();

	
		byte[] inFileBytes = Files.readAllBytes(Paths.get(filename));
		byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);
		System.out.println("PDF generated..");
		Files.delete(Paths.get(filename));

		return new String(encoded);

	}
}
