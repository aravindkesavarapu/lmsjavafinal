package com.capgemini.lmscucumber.stepdefinitions;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class UserAuth {

	public static WebDriver driver;

	static {
		System.setProperty("webdriver.chrome.driver",
				"C://Users//Aravind//Desktop//Final Parallel Project LMS//lmscucmber//driver//chromedriver.exe");
	}
	@Given("^User is On Register Page$")
	public void user_is_On_Register_Page() throws Throwable {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/home");
		driver.findElement(By.name("register")).click();
	}

	@When("^User enters \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",(\\d+),\"([^\"]*)\",\"([^\"]*)\"$")
	public void user_enters(String firstName, String lastName, String emailId, long mobileNo, String password,
			String role) throws Throwable {

		driver.findElement(By.id("firstName")).sendKeys(firstName);
		driver.findElement(By.id("lastName")).sendKeys(lastName);
		driver.findElement(By.id("email")).sendKeys(emailId);
		driver.findElement(By.id("mobileNo")).sendKeys(String.valueOf(mobileNo));
		driver.findElement(By.id("password")).sendKeys(password);
		WebElement options = driver.findElement(By.xpath(
				"/html[1]/body[1]/app-root[1]/app-register[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/form[1]/div[6]/div[1]/select[1]"));
		Select select = new Select(options);
		Thread.sleep(4000);
		select.selectByIndex(0);
		driver.findElement(By.id("register")).click();
	}

	@Then("^User should be registered$")
	public void user_should_be_true() throws Throwable {
		Thread.sleep(5000);
		String alertMsg = driver.switchTo().alert().getText();
		String actualMsg = "Registered Successfully";
		assertTrue(alertMsg.equals(actualMsg));
		driver.switchTo().alert().accept();
		String currentUrl = driver.getCurrentUrl();
		String actualUrl = "http://localhost:4200/login";
		assertTrue(currentUrl.equals(actualUrl));
	}

	@Given("^User is on Login page$")
	public void user_is_on_Login_page() throws Throwable {
		Thread.sleep(5000);
		driver.findElement(By.id("login")).click();
		String actual = driver.getTitle();
		String excepted = "Library";
		System.out.println(actual);
		assertTrue("The webpage is not a login page", actual.equals(excepted));
	}

	@When("^User enters \"([^\"]*)\",\"([^\"]*)\"$")
	public void user_enters(String emailId, String password) throws Throwable {
		driver.findElement(By.id("email")).sendKeys(emailId);;
		driver.findElement(By.id("password")).sendKeys(password);;
		driver.findElement(By.xpath("//button[@class='btn btn-primary btn-user btn-block logincolor']")).click();

	}

	@Then("^User should be <status>$")
	public void user_should_be_status() throws Throwable {
		Thread.sleep(4000);
		String alertMsg = driver.switchTo().alert().getText();
		String expectedAlertMsg = "User Loggedin";
		assertTrue(alertMsg.equals(expectedAlertMsg));
		driver.switchTo().alert().accept();
		String expectedUrl = "http://localhost:4200/home";
		String actualUrl = driver.getCurrentUrl();
		System.out.println(actualUrl);
		assertTrue("Admin is on logged in page", expectedUrl.equals(actualUrl));
		driver.close();
	}

}
