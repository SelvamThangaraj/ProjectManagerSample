package com.ts.pm;

import static com.jayway.restassured.RestAssured.given;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.delete;
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
import com.ts.pm.model.Project;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = ProjectManagerApplication.class)  
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProjectControllerTDD {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${server.port}")   
    int port;
	
	//used to build /pm/project/1
	static StringBuilder sbForProjectIdPath=new StringBuilder("/pm/project/");
		
	//Newly added project, which will be used to test GET,UPDATE,DELETE 
	static long addedProjectid;
	
	@Before
	public void setBaseUri () {

		 RestAssured.port = port;
		 RestAssured.baseURI = "http://localhost"; 
		
	}
	
		
	@Test
	public void Test1postData() throws JsonParseException, JsonMappingException, IOException {
		
		Project project=new Project();
		project.setProjectTitle("AaradhanaProject");
		project.setStartDate(LocalDate.now());
		project.setEndDate(LocalDate.now().plusDays(1));
		project.setPriority(5);
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(project)
				.when()
				.post("/pm/project");
		
		LOGGER.info("POST Response\n" + response.asString());
		Project addedProject = ConvertResponseToProjectObject(response);
		
		addedProjectid=addedProject.getProjectId();
		LOGGER.info("POST Response addedProjectid=>" + addedProjectid);
		// tests
		response.then().body("projectTitle", equalTo("AaradhanaProject"));		
	}

	
	@Test
	public void Test2getData() throws JsonParseException, JsonMappingException, IOException {
		//System.out.println(get("/pm/project/1").asString());
		//get("/pm/project/1").then().assertThat().body("firstName", equalTo("Parthiban"));
		
		sbForProjectIdPath.append(addedProjectid);
		LOGGER.debug("this.sbForProjectIdPath=>"+sbForProjectIdPath.toString());
		Response response=get(sbForProjectIdPath.toString());
		LOGGER.info("GET Response\n" + response.asString());
		Project project = ConvertResponseToProjectObject(response);
		assertEquals(project.getProjectId(),Long.valueOf(addedProjectid));
		//response.then().assertThat().body(String.valueOf("projectId").trim(), equalTo("AaradhanaProject"));
		//get("/pm/project/1").then().assertThat().body("projectId", equalTo("1"));
		//given().request().body("some body").post()		
	}
	
	@Test
	public void Test3PutData() {
		
		Project project=new Project();
		project.setProjectId(addedProjectid);
		project.setProjectTitle("AaradhanaProjectUpdated");
		project.setStartDate(LocalDate.now());
		project.setEndDate(LocalDate.now().plusDays(1));
		project.setPriority(5);
		
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(project)
				.when()
				.put("/pm/project");
		
		LOGGER.info("PUT Response\n" + response.asString());
		// tests
		response.then().body("projectTitle", equalTo("AaradhanaProjectUpdated"));		
	}
	
	
	
	@Test
	public void Test4ListAllData() {
		Response response= get("/pm/projects");
		LOGGER.info("LIST Projects Response\n" + response.asString());
		response.then().body("projectTitle", hasItems("AaradhanaProjectUpdated"));
	}
	
	
	@Test
	public void Test5SortByAttr() {
		Response response= get("/pm/projects/sort/projectTitle");
		LOGGER.info("LIST Projects Response\n" + response.asString());
		response.then().body("projectTitle", hasItems("AaradhanaProjectUpdated"));
	}

	@Test
	public void Test6deleteData() {
		LOGGER.info("DELETE path =>" + sbForProjectIdPath.toString());
		Response response = delete(sbForProjectIdPath.toString());
		
		LOGGER.info("DELETE Response=>" + response.asString());
		// tests
		response.then().statusCode(204);		
	}
	

	private Project ConvertResponseToProjectObject(Response response)
			throws IOException, JsonParseException, JsonMappingException {
		ResponseBody responseBody=response.body();
		ObjectMapper mapper = new ObjectMapper();		
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		Project addedProject=mapper.readValue(responseBody.asString(), Project.class);
		return addedProject;
	}

}
