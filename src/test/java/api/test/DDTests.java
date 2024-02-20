package api.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;


public class DDTests {
	
	User userPayLoad;
	
	@Test(priority=1, dataProvider="Data", dataProviderClass=DataProviders.class)
	public void testPostUser(String userID, String userName, String firstName, String lastName, String email, String password, String phone) {
		userPayLoad = new User();
		userPayLoad.setId(Integer.parseInt(userID));
		userPayLoad.setUsername(userName);
		userPayLoad.setFirstname(firstName);
		userPayLoad.setLastname(lastName);
		userPayLoad.setEmail(email);
		userPayLoad.setPassword(password);
		userPayLoad.setPhone(phone);
		
		Response response = UserEndPoints.createUser(userPayLoad);
		Assert.assertEquals(response.getStatusCode(), 200);	
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testGetUserByName(String userName) {
		
		Response response = UserEndPoints.getUser(userName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority=3, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUser(String userName) {
		Response response = UserEndPoints.DeleteUser(userName);
		Assert.assertEquals(response.getStatusCode(), 200);
	}
}
