package api.test;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import api.endpoints.UserEndPoints;
import api.payload.User;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDtest {
	
	@Test(dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPostUser(String userId,String username,String fname,String lname,String useremail,String pwd,String phone)
	{
		User userpayload = new User();
		userpayload.setId(Integer.parseInt(userId));
		userpayload.setUsername(username);
		userpayload.setFirstName(fname);
		userpayload.setLastName(lname);
		userpayload.setEmail(useremail);
		userpayload.setPassword(pwd);
		userpayload.setPhone(phone);
		Response response = UserEndPoints.createUser(userpayload);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(dataProvider="UserName",dataProviderClass=DataProviders.class,dependsOnMethods = "testPostUser",alwaysRun = true)
	public void testDeleteUser(String userName)
	{
		Response response = UserEndPoints.deleteUser(userName);
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
