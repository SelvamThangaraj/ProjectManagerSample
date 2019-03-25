package com.ts.pm;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.hamcrest.Matchers;
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
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.ts.pm.model.User;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = ProjectManagerApplication.class)  
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserControllerTDD {
	
	private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@Value("${server.port}")   
    int port;
	
	//used to build /pm/user/1
	static StringBuilder sbForUserIdPath=new StringBuilder("/pm/user/");
		
	
	
	//Newly added user, which will be used to test GET,UPDATE,DELETE 
	static long addedUserid;
	
	@Before
	public void setBaseUri () {

		 RestAssured.port = port;
		 RestAssured.baseURI = "http://localhost"; 
		
	}
	
		
	@Test
	public void Test1postData() throws JsonParseException, JsonMappingException, IOException {
		
		User user=new User();
		user.setFirstName("Aaradhana");
		user.setLastName("Selvam");
		user.setEmployeeId("25345");
		
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(user)
				.when()
				.post("/pm/user");
		
		LOGGER.info("POST Response\n" + response.asString());
		User addedUser = ConvertResponseToUserObject(response);
		
		addedUserid=addedUser.getUserId();
		LOGGER.info("POST Response addedUserid=>" + addedUserid);
		// tests
		response.then().body("firstName", equalTo("Aaradhana"));
		
	}

	
	@Test
	public void Test2getData() throws JsonParseException, JsonMappingException, IOException {
		//System.out.println(get("/pm/user/1").asString());
		//get("/pm/user/1").then().assertThat().body("firstName", equalTo("Parthiban"));
		
		sbForUserIdPath.append(addedUserid);
		LOGGER.debug("this.sbForUserIdPath=>"+sbForUserIdPath.toString());
		Response response=get(sbForUserIdPath.toString());
		LOGGER.info("GET Response\n" + response.asString());
		User user = ConvertResponseToUserObject(response);
		assertEquals(user.getUserId(),addedUserid);
		//response.then().assertThat().body(String.valueOf("userId").trim(), equalTo(String.valueOf(addedUserid).trim()));
		//get("/pm/user/1").then().assertThat().body("userId", equalTo("1"));
		//given().request().body("some body").post()		
	}
	
	@Test
	public void Test3PutData() {
		
		User user=new User();
		user.setUserId(addedUserid);
		user.setFirstName("Aaradhana");
		user.setLastName("SelvamSubathra");
		user.setEmployeeId("25345");
		
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(user)
				.when()
				.put("/pm/user");
		
		LOGGER.info("PUT Response\n" + response.asString());
		// tests
		response.then().body("lastName", equalTo("SelvamSubathra"));		
	}
	
	
	
	@Test
	public void Test4ListAllData() {
		Response response= get("/pm/users");
		LOGGER.info("LIST Users Response\n" + response.asString());
		response.then().body("lastName", hasItems("SelvamSubathra"));
	}
	
	
	@Test
	public void Test5SortByAttr() {
		Response response= get("/pm/users/sort/firtName");
		LOGGER.info("LIST Users Response\n" + response.asString());
		response.then().body("lastName", hasItems("SelvamSubathra"));
	}

	@Test
	public void Test6deleteData() {
		Response response = delete(sbForUserIdPath.toString());
		
		LOGGER.info("DELETE Response\n" + response.asString());
		// tests
		response.then().statusCode(204);		
	}
	

	private User ConvertResponseToUserObject(Response response)
			throws IOException, JsonParseException, JsonMappingException {
		ResponseBody responseBody=response.body();
		ObjectMapper mapper = new ObjectMapper();
		User addedUser=mapper.readValue(responseBody.asString(), User.class);
		return addedUser;
	}

}
