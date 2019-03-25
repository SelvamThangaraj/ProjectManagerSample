package com.ts.pm;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.ts.pm.model.ParentTask;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = ProjectManagerApplication.class)  
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParentTaskControllerTDD {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${server.port}")   
    int port;
	
	//used to build /pm/task/1
	static StringBuilder sbForParentTaskIdPath=new StringBuilder("/pm/parenttask/");
		
	//Newly added task, which will be used to test GET,UPDATE,DELETE 
	static long addedParentTaskid;
	
	@Before
	public void setBaseUri () {

		 RestAssured.port = port;
		 RestAssured.baseURI = "http://localhost"; 
		
	}
	
		
	@Test
	public void Test1postData() throws JsonParseException, JsonMappingException, IOException {
		
		ParentTask task=new ParentTask();
		task.setParentTask("AaradhanaParentTask");				
		task.setParentId(Long.valueOf(1));
		
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(task)
				.when()
				.post("/pm/parenttask");
		
		LOGGER.info("POST Response\n" + response.asString());
		ParentTask addedParentTask = ConvertResponseToParentTaskObject(response);
		
		addedParentTaskid=addedParentTask.getParentId();
		LOGGER.info("POST Response addedParentTaskid=>" + addedParentTaskid);
		// tests
		response.then().body("parentTask", equalTo("AaradhanaParentTask"));		
	}

	
	@Test
	public void Test2getData() throws JsonParseException, JsonMappingException, IOException {
				
		sbForParentTaskIdPath.append(addedParentTaskid);
		LOGGER.debug("this.sbForParentTaskIdPath=>"+sbForParentTaskIdPath.toString());
		Response response=get(sbForParentTaskIdPath.toString());
		LOGGER.info("GET Response\n" + response.asString());
		ParentTask task = ConvertResponseToParentTaskObject(response);
		assertEquals(task.getParentId(),Long.valueOf(addedParentTaskid));
			
	}
	
	@Test
	public void Test3PutData() {
		
		ParentTask task=new ParentTask();		
		task.setParentTask("AaradhanaParentTaskUpdated");
		
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(task)
				.when()
				.put("/pm/parenttask");
		
		LOGGER.info("PUT Response\n" + response.asString());
		// tests
		response.then().body("parentTask", equalTo("AaradhanaParentTaskUpdated"));		
	}
	
	
	
	@Test
	public void Test4ListAllData() {
		Response response= get("/pm/parenttasks");
		LOGGER.info("LIST ParentTasks Response\n" + response.asString());
		response.then().body("parentTask", hasItems("AaradhanaParentTaskUpdated"));
	}
	
	@Test
	public void Test6deleteData() {
		LOGGER.info("DELETE path =>" + sbForParentTaskIdPath.toString());
		Response response = delete(sbForParentTaskIdPath.toString());
		
		LOGGER.info("DELETE Response=>" + response.asString());
		// tests
		response.then().statusCode(204);		
	}
	

	private ParentTask ConvertResponseToParentTaskObject(Response response)
			throws IOException, JsonParseException, JsonMappingException {
		ResponseBody responseBody=response.body();
		ObjectMapper mapper = new ObjectMapper();		
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		ParentTask addedParentTask=mapper.readValue(responseBody.asString(), ParentTask.class);
		return addedParentTask;
	}

}
