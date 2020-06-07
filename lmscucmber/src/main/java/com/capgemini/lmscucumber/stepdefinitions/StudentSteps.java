package com.capgemini.lmscucumber.stepdefinitions;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StudentSteps {

	public static WebDriver driver;
	public static WebDriverWait wait;

	static {
		System.setProperty("webdriver.chrome.driver",
				"C://Users//Aravind//Desktop//Final Parallel Project LMS//lmscucmber//driver//chromedriver.exe");

	}

	@Given("^Student is on Login page$")
	public void student_is_on_Login_page() throws Throwable {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://localhost:4200/login");
		Thread.sleep(5000);
		String actual = driver.getTitle();
		String excepted = "Library";
		assertTrue("The webpage is not a login page", actual.equals(excepted));

	}

	@When("^Student enters \"([^\"]*)\",\"([^\"]*)\"$")
	public void student_enters(String emailId, String password) throws Throwable {
		driver.findElement(By.id("email")).sendKeys(emailId);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.name("loginbtn")).click();

	}

	@Then("^Student should be logged in$")
	public void student_should_be_logged_in() throws Throwable {
		Thread.sleep(5000);
		String alertMsg = driver.switchTo().alert().getText();
		String expectedAlertMsg = "User Loggedin";
		assertTrue(alertMsg.equals(expectedAlertMsg));
		driver.switchTo().alert().accept();
		String expectedUrl = "http://localhost:4200/home";
		String actualUrl = driver.getCurrentUrl();
		System.out.println(actualUrl);
		assertTrue("Admin is on logged in page", expectedUrl.equals(actualUrl));

	}

	@Given("^Student is on request book page$")
	public void student_is_on_request_book_page() throws Throwable {
		// driver.get("http://localhost:4200/home");
		driver.findElement(By.linkText("Request A Book")).click();
		String actualUrl = driver.getCurrentUrl();
		String excepted = "http://localhost:4200/requestBook";
		assertTrue("Student is In ReQuestBook Page", actualUrl.equals(excepted));
	}

	@When("^Student clicks on Request Button$")
	public void student_clicks_on_Request_Button() throws Throwable {
		driver.findElement(By.xpath("//tr[1]//td[8]//button[1]")).click();
		wait = new WebDriverWait(driver, 10);

	}

	@Then("^Book Requested Successfully$")
	public void book_Requested_Successfully() throws Throwable {
		Thread.sleep(3000);
		String autualMessage = driver.switchTo().alert().getText();
		Thread.sleep(3000);
		String expectedMessage = "Request placed successfully";
		driver.switchTo().alert().accept();
		assertTrue("Book Requested Sucessfully", autualMessage.equalsIgnoreCase(expectedMessage));
		driver.close();
	}

	@Given("^Student is on returning book page$")
	public void student_is_on_returning_book_page() throws Throwable {
		driver.findElement(By.linkText("Borrowed Books")).click();
		String actualUrl = driver.getCurrentUrl();
		String exceptedUrl = "http://localhost:4200/returnBook";
		assertTrue("Student Is On Borrowed Books Page", actualUrl.equals(exceptedUrl));
	}

	@When("^Student clicks on return button$")
	public void student_clicks_on_return_button() throws Throwable {
		String titleName = driver.findElement(By.tagName("h6")).getText();
		assertTrue("Student is On Borrowrd Book", titleName.equals("Borrowed Books"));
		driver.findElement(By.xpath("//tr[1]//td[6]//button[1]")).click();
	}

	@Then("^Book Returned Successfully$")
	public void book_Returned_Successfully() throws Throwable {
		Thread.sleep(3000);
		String actualMessage = driver.findElement(By.xpath("//body/app-root/app-return-books/div/div[1]")).getText();
		String exceptedMessage = "Book returned to the Library";
		assertTrue("Book Reurned Sucessfully", actualMessage.equals(exceptedMessage));
		driver.close();
	}

	@Given("^Student is on  viewing books page based on names$")
	public void student_is_on_viewing_books_page_based_on_names() throws Throwable {
		driver.findElement(By.linkText("Search Books")).click();
		String exceptedUrl = "http://localhost:4200/allBookDetails";
		String actualUrl = driver.getCurrentUrl();
		assertTrue(exceptedUrl.equals(actualUrl));
	}

	@When("^Student enters \"([^\"]*)\"$")
	public void student_enters(String bookTitle) throws Throwable {
		driver.findElement(By.id("search")).sendKeys(bookTitle);
	}

	@Then("^show books based on name$")
	public void show_books_based_on_name() throws Throwable {
		String actualBookName = driver.findElement(By.xpath("//tr[1]//td[3]")).getText().trim();
		System.out.println(actualBookName);
		String expectedBookName = "Signals and Systems";
		assertTrue("Student Searched A Book By Book Name", actualBookName.equalsIgnoreCase(expectedBookName));
		driver.close();
	}

	@Given("^Student is on  viewing books page based on author Name$")
	public void user_is_on_viewing_books_page_based_on_author_Name() throws Throwable {
		driver.findElement(By.linkText("Search Books")).click();
		String exceptedUrl = "http://localhost:4200/allBookDetails";
		String actualUrl = driver.getCurrentUrl();
		assertTrue(exceptedUrl.equals(actualUrl));

	}

	@When("^Student enters author name \"([^\"]*)\"$")
	public void student_enters_author_name(String authorName) throws Throwable {
		driver.findElement(By.id("search")).sendKeys(authorName);

	}

	@Then("^show books based on  author name$")
	public void show_books_based_on_author_name() throws Throwable {
		String actualBookName = driver.findElement(By.xpath("//tr[1]//td[4]")).getText().trim();
		String expectedBookName = "James";
		assertTrue("Student Searched A Book By Author Name", actualBookName.equals(expectedBookName));
		driver.close();
	}

}
