package com.ts.pm;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.LocalDate;

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
import com.ts.pm.model.Project;
import com.ts.pm.model.Task;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = ProjectManagerApplication.class)  
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskControllerTDD {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${server.port}")   
    int port;
	
	//used to build /pm/task/1
	static StringBuilder sbForTaskIdPath=new StringBuilder("/pm/task/");
		
	//Newly added task, which will be used to test GET,UPDATE,DELETE 
	static long addedTaskid;
	
	@Before
	public void setBaseUri () {

		 RestAssured.port = port;
		 RestAssured.baseURI = "http://localhost"; 
		
	}
	
		
	@Test
	public void Test1postData() throws JsonParseException, JsonMappingException, IOException {
		
		Task task=new Task();
		task.setTask("Task-105");
		task.setStartDate(LocalDate.now());
		task.setEndDate(LocalDate.now().plusDays(1));
		task.setPriority(5);
		task.setStatus("Started");
		task.setProjectId(Long.valueOf(1));			
		task.setParentId(Long.valueOf(6));
		
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(task)
				.when()
				.post("/pm/task");
		
		LOGGER.info("POST Response\n" + response.asString());
		Task addedTask = ConvertResponseToTaskObject(response);
		
		addedTaskid=addedTask.getTaskId();
		LOGGER.info("POST Response addedTaskid=>" + addedTaskid);
		// tests
		response.then().body("task", equalTo("AaradhanaTask"));		
	}

	
	@Test
	public void Test2getData() throws JsonParseException, JsonMappingException, IOException {
				
		sbForTaskIdPath.append(addedTaskid);
		LOGGER.debug("this.sbForTaskIdPath=>"+sbForTaskIdPath.toString());
		Response response=get(sbForTaskIdPath.toString());
		LOGGER.info("GET Response\n" + response.asString());
		Task task = ConvertResponseToTaskObject(response);
		assertEquals(task.getTaskId(),Long.valueOf(addedTaskid));
			
	}
	
	@Test
	public void Test3PutData() {
		
		Task task=new Task();
		task.setTaskId(addedTaskid);
		task.setTask("AaradhanaTaskUpdated");
		task.setStartDate(LocalDate.now());
		task.setEndDate(LocalDate.now().plusDays(1));
		task.setPriority(5);
		
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(task)
				.when()
				.put("/pm/task");
		
		LOGGER.info("PUT Response\n" + response.asString());
		// tests
		response.then().body("task", equalTo("AaradhanaTaskUpdated"));		
	}
	
	
	
	@Test
	public void Test4ListAllData() {
		Response response= get("/pm/tasks");
		LOGGER.info("LIST Tasks Response\n" + response.asString());
		response.then().body("task", hasItems("AaradhanaTaskUpdated"));
	}
	
	
	@Test
	public void Test5SortByAttr() {
		Response response= get("/pm/tasks/sort/taskTitle");
		LOGGER.info("LIST Tasks Response\n" + response.asString());
		response.then().body("task", hasItems("AaradhanaTaskUpdated"));
	}

	@Test
	public void Test6deleteData() {
		LOGGER.info("DELETE path =>" + sbForTaskIdPath.toString());
		Response response = delete(sbForTaskIdPath.toString());
		
		LOGGER.info("DELETE Response=>" + response.asString());
		// tests
		response.then().statusCode(204);		
	}
	

	private Task ConvertResponseToTaskObject(Response response)
			throws IOException, JsonParseException, JsonMappingException {
		ResponseBody responseBody=response.body();
		ObjectMapper mapper = new ObjectMapper();		
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		Task addedTask=mapper.readValue(responseBody.asString(), Task.class);
		return addedTask;
	}

}
