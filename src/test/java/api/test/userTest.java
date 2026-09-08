package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class userTest {
	
	Faker faker;
	User userpayload;
	public Logger logger;
	@BeforeClass
	public void setData()
	{
		faker = new Faker();
		userpayload = new User();
		
		userpayload.setId(faker.idNumber().hashCode());
		userpayload.setUsername(faker.name().username());
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		userpayload.setPassword(faker.internet().password(5, 10));
		userpayload.setPhone(faker.phoneNumber().cellPhone());
		logger = LogManager.getLogger(this.getClass());
		logger.debug("debug");
	}
	
	@Test(priority=1)
	public void testPostUser()
	{
		logger.info("******* user creating ********");
		Response response = UserEndPoints.createUser(userpayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******* user created ********");
	}
	@Test(priority=2)
	public void testGetUser()
	{
		logger.info("******* user retriving ********");
		Response response = UserEndPoints.readUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******* user retrieved ********");
	}
	@Test(priority=3)
	public void testUpdateUser()
	{
		logger.info("******* user updating ********");
		userpayload.setFirstName(faker.name().firstName());
		userpayload.setLastName(faker.name().lastName());
		userpayload.setEmail(faker.internet().safeEmailAddress());
		Response response = UserEndPoints.updateUser(userpayload,this.userpayload.getUsername());
		response.then().log().body();
		Assert.assertEquals(response.getStatusCode(), 200);
		Response updateresponse = UserEndPoints.readUser(this.userpayload.getUsername());
		updateresponse.then().log().all();
		Assert.assertEquals(updateresponse.getStatusCode(), 200);
		logger.info("******* user updated ********");
	}
	@Test(priority=4)
	public void testDeleteUser()
	{
		logger.info("******* user deleting ********");
		Response response = UserEndPoints.deleteUser(this.userpayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
		logger.info("******* user deleted ********");
	}

}
