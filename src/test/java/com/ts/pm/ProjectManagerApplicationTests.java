package com.ts.pm;

import static com.jayway.restassured.RestAssured.delete;
import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import com.ts.pm.model.User;

@RunWith(SpringJUnit4ClassRunner.class)   
@ContextConfiguration(classes = ProjectManagerApplication.class)  
@TestPropertySource(value={"classpath:application.properties"})
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ProjectManagerApplicationTests {

	@Value("${server.port}")   
    int port;
	
	@Before
	public void setBaseUri () {

		 RestAssured.port = port;
		 RestAssured.baseURI = "http://localhost"; 
	}
	
		
	@Test
	public void postDataTest() {
		
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
		
		System.out.println("POST Response\n" + response.asString());
	
		// tests
		response.then().body("firstName", equalTo("Aaradhana"));		
	}
	
	@Test
	public void getDataTest() {
		//System.out.println(get("/pm/user/1").asString());
		//get("/pm/user/1").then().assertThat().body("firstName", equalTo("Parthiban"));
		get("/pm/user/1").then().assertThat().body("userId", equalTo(1));
		//get("/pm/user/1").then().assertThat().body("userId", equalTo("1"));
		//given().request().body("some body").post()		
	}
	
	@Test
	public void putDataTest() {
		
		User user=new User();
		user.setUserId(5);
		user.setFirstName("Aaradhana");
		user.setLastName("SelvamSubathra");
		user.setEmployeeId("25345");
		
		Response response = given().
				contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.body(user)
				.when()
				.put("/pm/user");
		
		System.out.println("PUT Response\n" + response.asString());
		// tests
		response.then().body("lastName", equalTo("SelvamSubathra"));		
	}
	
	@Test
	public void deleteDataTest() {
		Response response = delete("/pm/user/9");
		
		System.out.println("DELETE Response\n" + response.asString());
		// tests
		response.then().statusCode(204);
		
	}
	
	@Test
	public void listAllDataTest() {
		Response response= get("/pm/users");
		System.out.println("LIST Users Response\n" + response.asString());
		response.then().body("userId", hasItems(1, 2));
	}

	

}
