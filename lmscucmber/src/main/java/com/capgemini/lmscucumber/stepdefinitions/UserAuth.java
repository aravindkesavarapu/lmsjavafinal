//package com.capgemini.lmscucumber.stepdefinitions;
//
//import static org.junit.Assert.assertTrue;
//
//import java.util.concurrent.TimeUnit;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//import com.capgemini.lmscucumber.testrunner.UserAuthTestRunner;
//
//import cucumber.api.java.Before;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//
//public class UserAuth extends UserAuthTestRunner {
//
//	@Before
//	public void openBrowser() throws Exception {
//		driver = new ChromeDriver();
//		driver.get("http://localhost:4200/login");
////		wait = new WebDriverWait(driver, 10);
////		action = new Actions(driver);
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//	}
//
//	@Given("^User is on Login page$")
//	public void user_is_on_Login_page() throws Throwable {
//		Thread.sleep(5000);
//		String actual = driver.getTitle();
//		String excepted = "Library";
//		System.out.println(actual);
//		assertTrue("The webpage is not a login page", actual.equals(excepted));
//	}
//
//	@When("^User enters \"([^\"]*)\",\"([^\"]*)\"$")
//	public void user_enters(String arg1, String arg2) throws Throwable {
//		WebElement email = driver.findElement(By.id("email"));
//		WebElement password = driver.findElement(By.id("password"));
//		WebElement login = driver
//				.findElement(By.xpath("//button[@class='btn btn-primary btn-user btn-block logincolor']"));
//		email.sendKeys(arg1);
//		email.sendKeys(arg1);
//		password.sendKeys(arg2);
//		login.click();
//
//	}
//
//	@Then("^User should be <status>$")
//	public void user_should_be_status() throws Throwable {
//		String actual = driver.getCurrentUrl();
//		String excepted = "http://localhost:4200/home";
//		System.out.println(actual);
//		assertTrue("Admin logged in successfully", actual.equals(excepted));
//
//	}
//}
