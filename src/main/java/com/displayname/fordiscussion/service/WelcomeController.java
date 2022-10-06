package com.displayname.fordiscussion.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.displayname.fordiscussion.util.Constants;
import com.displayname.fordiscussion.core.exception.ApplicationLogicError;
import com.displayname.fordiscussion.model.SunbirdApiRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@RestController
public class WelcomeController {
	
	

		@GetMapping("/welcome")
		public String welcome() {	
			return "Hello Spring Boot"; 	
		}
		
		@PostMapping(path= "/getUsers", consumes = "application/json", produces = "application/json")
		public void getUsers(){ 
		    final String uri = "http://localhost:9000/private/user/v1/search";
			Map<String, Object>  response=null;
		    RestTemplate restTemplate = new RestTemplate();
			Map<String, Object> ret = new HashMap<>();
			Map<String, String> headersValue = new HashMap<>();
			headersValue.put("Content-Type", "application/json");
			headersValue.put("Authorization", "apiKey");
			//headersValue.put("X-Authenticated-User-Token", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2V1JvWndCZTRJU0U1c2lkWlY5YW55NTZXTmp3clk0eWlPTy0zUC1uY3A4In0.eyJqdGkiOiIyYWM4MGE0Ny00ZjkxLTQwOGQtODc2ZC04MDlhYTdlZjA0OGMiLCJleHAiOjE2NjM3MzU1NDcsIm5iZiI6MCwiaWF0IjoxNjYzNjQ5MTQ3LCJpc3MiOiJodHRwczovL2lnb3RrYXJtYXlvZ2kuZ292LmluL2F1dGgvcmVhbG1zL3N1bmJpcmQiLCJzdWIiOiJmOmVmMmRjZWRmLWVkMmItNDhmYS04NWU5LWVlYWI5YTI3ODMyNToxYTYzY2I4ZC05YTgyLTQyMWEtYmRlYS05N2FhMGI5OWVkNjQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhZG1pbi1jbGkiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI3YzUxMTc3OS1mNmM5LTQ5ZTAtOWQzZS0yMmZkNzUwODZlMzMiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInNjb3BlIjoiIiwibmFtZSI6Ik5hbGluaSBTaGFybWEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJuYWxpbmluYXV0aXlhbF94MWpnIiwiZ2l2ZW5fbmFtZSI6Ik5hbGluaSIsImZhbWlseV9uYW1lIjoiU2hhcm1hIiwiZW1haWwiOiJuYSoqKipAbmljLmluIn0.EdKy_tlXuwZ68oKQ4GGDswt80Bx-PEfUkiXhFbPKdyqDrJjEcf-52FEA_s-UsfsyotveQoDq8NwSMUPbmqGsbDQSMlTdAvXcDBYX0DLG4BKCNafM4yZhW3hQ5mKOJV3vZv5x0xKyF6fmDU7AM0iIG-sQ6m3nAN56A8AUymFaUWZslyvwsk6oNwQVl96DaehY9f8EXixcyP4iDtiuZ6lk96msyHZ-U45XUcy7UEUYUZYtiyjN9RsHecZ7pmaD2DHMsBIvpT4vZYVT2nuZmdozkaUUohLE-fEW472iK4jo78xLawh5yKvNyfKIMyhO0JizOH-hF20i2PTO6IqRk_NCcw");

		/*   HttpHeaders headers = new HttpHeaders();
		    headers.set("Authorization", "Bearer  eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI5a04xTW1TcGVuVTAyam8zVHg1U2p0amhTOFVXeGVSUiJ9.LWAgFust4e0wntxqY8_MQjf5WQ9RSD6Hg45jX_NoCXY");    
		    headers.set("X-Authenticated-User-Token", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI2V1JvWndCZTRJU0U1c2lkWlY5YW55NTZXTmp3clk0eWlPTy0zUC1uY3A4In0.eyJqdGkiOiIyYWM4MGE0Ny00ZjkxLTQwOGQtODc2ZC04MDlhYTdlZjA0OGMiLCJleHAiOjE2NjM3MzU1NDcsIm5iZiI6MCwiaWF0IjoxNjYzNjQ5MTQ3LCJpc3MiOiJodHRwczovL2lnb3RrYXJtYXlvZ2kuZ292LmluL2F1dGgvcmVhbG1zL3N1bmJpcmQiLCJzdWIiOiJmOmVmMmRjZWRmLWVkMmItNDhmYS04NWU5LWVlYWI5YTI3ODMyNToxYTYzY2I4ZC05YTgyLTQyMWEtYmRlYS05N2FhMGI5OWVkNjQiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJhZG1pbi1jbGkiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiI3YzUxMTc3OS1mNmM5LTQ5ZTAtOWQzZS0yMmZkNzUwODZlMzMiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInNjb3BlIjoiIiwibmFtZSI6Ik5hbGluaSBTaGFybWEiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJuYWxpbmluYXV0aXlhbF94MWpnIiwiZ2l2ZW5fbmFtZSI6Ik5hbGluaSIsImZhbWlseV9uYW1lIjoiU2hhcm1hIiwiZW1haWwiOiJuYSoqKipAbmljLmluIn0.EdKy_tlXuwZ68oKQ4GGDswt80Bx-PEfUkiXhFbPKdyqDrJjEcf-52FEA_s-UsfsyotveQoDq8NwSMUPbmqGsbDQSMlTdAvXcDBYX0DLG4BKCNafM4yZhW3hQ5mKOJV3vZv5x0xKyF6fmDU7AM0iIG-sQ6m3nAN56A8AUymFaUWZslyvwsk6oNwQVl96DaehY9f8EXixcyP4iDtiuZ6lk96msyHZ-U45XUcy7UEUYUZYtiyjN9RsHecZ7pmaD2DHMsBIvpT4vZYVT2nuZmdozkaUUohLE-fEW472iK4jo78xLawh5yKvNyfKIMyhO0JizOH-hF20i2PTO6IqRk_NCcw");      
		    headers.set("Content-Type", "application/json");
		     */ 


		 // set `accept` header
		 SunbirdApiRequest requestObj = new SunbirdApiRequest();
		Map<String, Object> reqMap = new HashMap<>();
		reqMap.put(Constants.FILTERS,new HashMap<String, Object>() {
			
		});
		reqMap.put(Constants.FIELDS, new HashMap<String, Object>() {
			{
				
			}
		});
		reqMap.put(Constants.SORT_BY, new HashMap<String, Object>() {
			{
				put(Constants.CREATED_DATE, "Desc");
			}
		});requestObj.setRequest(reqMap);

		    JsonParser jp = new JsonParser(); 
		    JsonObject jsonObject = (JsonObject) jp.parse("{\"request\": {\"filters\": {},\"fields\": [],\"sortBy\": {\"createdDate\": \"Desc\"}}}"); 
		    HttpHeaders headers = new HttpHeaders();
			if (!CollectionUtils.isEmpty(headersValue)) {
				headersValue.forEach((k, v) -> headers.set(k, v));
			}
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<Object> entity = new HttpEntity<>(requestObj, headers);
			
		    response = restTemplate.postForObject(uri, jsonObject, Map.class);
			if (response != null && "OK".equalsIgnoreCase((String) response.get("responseCode"))) {
				Map<String, Object> map = (Map<String, Object>) response.get("result");
				if (map.get("response") != null) {
					Map<String, Object> responseObj = (Map<String, Object>) map.get("response");
					List<Map<String, Object>> contentObj = (List<Map<String, Object>>) responseObj.get("content");
					
					for (Map<String,Object> userRecord : contentObj) {
						String firstName = userRecord.get("firstName").toString();
					String lastName = userRecord.get("lastName").toString();
					String userName = userRecord.get("userName").toString();
					ret.put("name", (Object) (firstName+" "+lastName));
					System.out.println(userName+" : "+ firstName+" "+ lastName);
					}
					
				}
			}
			//ResponseEntity<Map<String,Object>> resp = (ResponseEntity<Map<String,Object>>) ret;
		    //return resp;

		    // convert your result into json
		}
		

		

		@GetMapping(value="/countries")
		private List<Object> getCountries()
		{
			String uri = "https://restcountries.eu/rest/v2/all";
			RestTemplate rt= new RestTemplate() ; 
			Object[] countries  = rt.getForObject(uri, Object[].class); 
			return Arrays.asList(countries)  ; 
		}
		/*	@GetMapping("/userList")
		private String userList()
		{
			String uri = "https://igotkarmayogi.gov.in/api/user/v1/search";
			RestTemplate rt= new RestTemplate() ; 
			String msg = rt.getForObject(uri, String.class); 
			return msg+"This is from Welcome call" ; 
		}
		*/
}
