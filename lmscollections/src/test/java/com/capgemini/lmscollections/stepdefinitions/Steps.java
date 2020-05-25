package com.capgemini.lmscollections.stepdefinitions;

import org.junit.jupiter.api.Assertions;

import com.capgemini.lmscollections.dao.UserDAO;
import com.capgemini.lmscollections.dto.UserPrimaryInfo;
import com.capgemini.lmscollections.factory.LibraryFactory;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {

	UserPrimaryInfo userInfo;
	UserDAO dao = LibraryFactory.getUserDAO();
	boolean status = false;

	@Given("^User is On Registration Page$")
	public void user_is_On_Registration_Page() throws Throwable {
		userInfo = new UserPrimaryInfo();
	}

	@When("^User enters (\\d+),\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
	public void user_enters(int arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
		userInfo.setUserId(arg1);
		userInfo.setUserName(arg2);
		userInfo.setUserEmailId(arg3);
		userInfo.setUserPassword(arg4);
		status = dao.register(userInfo, arg5);
	}

//	@Then("^User should be \"([^\"]*)\"$")
//	public void user_should_be(boolean arg1) throws Throwable {
//		Assertions.assertTrue(arg1);
//
//	}

	@Then("^User is Registered true$")
	public void user_is_Registered_true() throws Throwable {
		Assertions.assertTrue(status);
	}

	@Given("^User is On Login Page$")
	public void user_is_On_Login_Page() throws Throwable {
		dao = LibraryFactory.getUserDAO();
	}

	@When("^User enters \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
	public void user_enters(String arg1, String arg2, String arg3) throws Throwable {
		status = false;
		status = dao.userAuthentication(arg1, arg2, arg3);
	}

	@Then("^User true$")
	public void user_true() throws Throwable {
		Assertions.assertTrue(status);
	}

}
