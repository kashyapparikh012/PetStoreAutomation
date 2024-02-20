package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {

	Faker faker;
	User userPayLoad;
	String username;
	public Logger logger;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayLoad = new User();
		userPayLoad.setId(faker.idNumber().hashCode());
		userPayLoad.setUsername(faker.name().username());
		userPayLoad.setFirstname(faker.name().firstName());
		userPayLoad.setLastname(faker.name().lastName());
		userPayLoad.setEmail(faker.internet().safeEmailAddress());
		userPayLoad.setPassword(faker.internet().password(5, 10));
		userPayLoad.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority=1)
	public void testPostUser() {
		
		logger.info("*******************Creating user*****************");
		Response response = UserEndPoints2.createUser(userPayLoad);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("*******************User is created*****************");
				
	}
	
	@Test(priority=2)
	public void tetsGetUserByName() {
		
		logger.info("*******************Reading user info*****************");
		Response response = UserEndPoints2.getUser(this.userPayLoad.getUsername());
		
		//System.out.println("Original email is: " + response.jsonPath().get(".email").toString());
		JSONObject jo = new JSONObject(response.asString());
		String createdUsername = jo.get("username").toString();
		System.out.println("***************Created username is: " + createdUsername);
		System.out.println("*************Original email is: " + jo.get("email").toString());
		
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(createdUsername, userPayLoad.getUsername());
		logger.info("*******************User info is displayed*****************");
		
	}
	
	@Test(priority=3)
	public void testUpdateUserByName() {
		logger.info("*******************Updating user info*****************");
		//Update data using payload
		userPayLoad.setFirstname(faker.name().firstName());
		userPayLoad.setLastname(faker.name().lastName());
		userPayLoad.setEmail(faker.internet().safeEmailAddress());
		
		Response responseAfterUpdate = UserEndPoints2.updateUser(this.userPayLoad.getUsername(), userPayLoad);
		responseAfterUpdate.then().log().all();
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		logger.info("*******************User is updated*****************");
	}
	
	@Test(priority=4)
	public void testGetUpdatedUserEmail() {
		logger.info("*******************Reading updated user info*****************");
		//Checking data after update
		Response responseAfterUpdate = UserEndPoints2.getUser(this.userPayLoad.getUsername());
		responseAfterUpdate.then().log().all();
		//System.out.println("Updated email is: " + response.jsonPath().get(".email").toString());
		JSONObject jo = new JSONObject(responseAfterUpdate.asString());
		System.out.println("*************Updated email is: " + jo.get("email").toString());
		Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);
		logger.info("*******************Updated user info is displayed*****************");
	}
	
	@Test(priority=5)
	public void testDeleteUserByName() {
		logger.info("*******************Deleting user*****************");
		Response response = UserEndPoints2.DeleteUser(this.userPayLoad.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
		response.then().log().all();
		logger.info("*******************User is deleted*****************");
	}
	
}
