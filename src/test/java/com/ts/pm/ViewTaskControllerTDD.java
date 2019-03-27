package com.ts.pm;

import static com.jayway.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.hasItems;
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
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.ts.pm.model.ProjectWithTaskDetails;
import com.ts.pm.model.Task;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = ProjectManagerApplication.class)  
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ViewTaskControllerTDD {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${server.port}")   
    int port;
	
	
	@Before
	public void setBaseUri () {

		 RestAssured.port = port;
		 RestAssured.baseURI = "http://localhost"; 
		 
	}

	
	@Test
	public void Test1GetTasksByProjectId() throws JsonParseException, JsonMappingException, IOException {
				
		String path="/pm/viewtask/1";
		LOGGER.debug("this.sbForTaskIdPath=>"+path);
		Response response=get(path);
		LOGGER.info("GET Response\n" + response.asString());
		ProjectWithTaskDetails projectWithTaskDetails = ConvertResponseToTaskObject(response);
		assertEquals(projectWithTaskDetails.getProject().getProjectId(),Long.valueOf(1));
			
	}
	
	
	
	@Test
	public void Test2SortByAttr() throws JsonParseException, JsonMappingException, IOException {
		String path="/pm/viewtask/1/sort/startDate";
		LOGGER.debug("this.SortByAttr() path=>"+path);
		
		Response response= get(path);
		LOGGER.info("LIST Tasks Response\n" + response.asString());
		ProjectWithTaskDetails projectWithTaskDetails = ConvertResponseToTaskObject(response);
		assertEquals(projectWithTaskDetails.getProject().getProjectId(),Long.valueOf(1));
		
	}
	
	
	private ProjectWithTaskDetails ConvertResponseToTaskObject(Response response)
			throws IOException, JsonParseException, JsonMappingException {
		ResponseBody responseBody=response.body();
		ObjectMapper mapper = new ObjectMapper();		
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        ProjectWithTaskDetails addedTask=mapper.readValue(responseBody.asString(), ProjectWithTaskDetails.class);
		return addedTask;
	}

}
