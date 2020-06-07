package com.capgemini.lmscucumber.stepdefinitions;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.capgemini.lmscucumber.testrunner.AdminTestRunner;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AdminSteps extends AdminTestRunner {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions action;

	static {
		System.setProperty("webdriver.chrome.driver",
				"C://Users//Aravind//Desktop//Final Parallel Project LMS//lmscucmber//driver//chromedriver.exe");
	}

	@Given("^Admin is on Login page$")
	public void admin_is_on_Login_page() throws Throwable {
		driver = new ChromeDriver();
		Thread.sleep(5000);
		driver.get("http://localhost:4200/login");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		String actual = driver.getTitle();
		String excepted = "Library";
		assertTrue("The webpage is not a login page", actual.equals(excepted));
	}

	@When("^Admin enters \"([^\"]*)\",\"([^\"]*)\"$")
	public void admin_enters(String emailId, String password) throws Throwable {
		driver.findElement(By.id("email")).sendKeys(emailId);
		driver.findElement(By.id("password")).sendKeys(password);
	}

	@And("^Admin click on login button$")
	public void admin_click_on_login_button() throws Throwable {
		driver.findElement(By.name("loginbtn")).click();

	}

	@Then("^Admin should be logged in$")
	public void admin_should_be_logged_in() throws Throwable {
		Thread.sleep(4000);
		String alertMsg = driver.switchTo().alert().getText();
		String expectedAlertMsg = "User Loggedin";
		assertTrue(alertMsg.equals(expectedAlertMsg));
		driver.switchTo().alert().accept();
		String expectedUrl = "http://localhost:4200/home";
		String actualUrl = driver.getCurrentUrl();
		System.out.println(actualUrl);
		assertTrue("Admin is on logged in page", expectedUrl.equals(actualUrl));
	}

	@Given("^Admin navigate to Add Book Page$")
	public void admin_navigate_to_add_book_page() throws Throwable {
		driver.findElement(By.partialLinkText("Add Boo")).click();
		String actual = driver.getCurrentUrl();
		String excepted = "http://localhost:4200/addBook";
		System.out.println(actual);
		assertTrue("Admin logged in successfully", excepted.equals(actual));

	}

	@When("^Admin enters \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",(\\d+),\"([^\"]*)\"$")
	public void admin_enters(String bName, String authorName, String publisherName, int copies, String category)
			throws Throwable {
		driver.findElement(By.id("bookName")).sendKeys(bName);
		driver.findElement(By.id("authorName")).sendKeys(authorName);
		driver.findElement(By.id("bookCategory")).sendKeys(category);
		driver.findElement(By.id("copies")).sendKeys(String.valueOf(copies));
		driver.findElement(By.id("publisherName")).sendKeys(publisherName);
		driver.findElement(By.name("addBookButton")).click();

	}

	@Then("^Book Added status$")
	public void book_added_status() throws Throwable {
		String actualMessage = driver.findElement(By.xpath("//div[@name='sucesssmsg']")).getText();
		String expectedMessage = "Book added successfully";
		assertTrue(actualMessage.equals(expectedMessage));
		driver.close();
	}

	@Given("^Admin  navigates to Requested books page$")
	public void admin_Navigates_to_Requested_books_page() throws Throwable {
		driver.findElement(By.linkText("Requested Books")).click();
		String headingName = driver.findElement(By.xpath("//h6[@id='name']")).getText();
		String expectedHeadingName = "Book Request Details";
		assertTrue(headingName.equals(expectedHeadingName));
	}

	@When("^Admin click on Issue button$")
	public void admin_click_on_Issue_Button() throws Throwable {
		driver.findElement(By.xpath("//tr[1]//td[6]//button[1]")).click();
	}

	@Then("^Admin can Issue the Book to the Student$")
	public void admin_can_Issue_the_Book_to_the_Student() throws Throwable {
		Thread.sleep(5000);
		String alretMessage = driver.switchTo().alert().getText();
		String alertExpected = "Book Issued to the User";
		assertTrue(alertExpected.equals(alretMessage));
		driver.switchTo().alert().accept();
		driver.close();
	}

	@Given("^Admin navigates to Issued Books$")
	public void admin_navigates_to_Issued_Books() throws Throwable {
		driver.findElement(By.linkText("Issued Books")).click();

	}

	@Then("^Admin can view Issued Books$")
	public void admin_can_view_Issued_Books() throws Throwable {
		String actualHeading = driver.findElement(By.tagName("h6")).getText();
		String expectedHeading = "Issued Books";
		assertTrue(actualHeading.equalsIgnoreCase(expectedHeading));
		driver.close();
	}

	@Given("^Admin is on Search Books Page$")
	public void admin_is_on_Search_Books_Page() throws Throwable {
		driver.findElement(By.linkText("Search Books")).click();
		String actualUrl = driver.getCurrentUrl();
		String expectedUrl = "http://localhost:4200/bookDetails";
		assertTrue(actualUrl.equals(expectedUrl));
	}

	@When("^Admin click on Update button$")
	public void admin_click_on_Update_button() throws Throwable {
		driver.findElement(By.id("updateBtn")).click();
	}

	@Then("^Admin navigates to Update Book Page$")
	public void admin_navigates_to_Update_Book_Page() throws Throwable {
		String actualTitle = driver.findElement(By.tagName("h3")).getText();
		String expectedTitle = "Update Book!!";
		assertTrue(actualTitle.equals(expectedTitle));

	}

	@And("^Admin  wants to Update \"([^\"]*)\",(\\d+)$")
	public void admin_wants_to_Update(String bookName, int copies) throws Throwable {

		driver.findElement(By.id("bookName")).clear();
		driver.findElement(By.id("bookName")).sendKeys(bookName);
		driver.findElement(By.id("copies")).clear();
		driver.findElement(By.id("copies")).sendKeys(String.valueOf(copies));
		driver.findElement(By.id("updateBtn")).click();

	}

	@Then("^Admin click on Update Book Button$")
	public void admin_click_on_Update_Book_Button() throws Throwable {
		Thread.sleep(5000);
		String alertMsg = driver.switchTo().alert().getText();
		String expectedMsg = "Book Sucessfully Updated";
		assertTrue(alertMsg.equals(expectedMsg));
		driver.switchTo().alert().accept();
		String actualmsg = driver.findElement(By.id("successmsg")).getText();
		String expectedmsg = "Book Updated SuccessFully";
		assertTrue(actualmsg.equals(expectedmsg));
		driver.close();

	}

	@Given("^Admin is on Search Book Page$")
	public void admin_is_on_Search_Book_Page() throws Throwable {
		driver.findElement(By.linkText("Search Books")).click();
		String actualUrl = driver.getCurrentUrl();
		String expectedUrl = "http://localhost:4200/bookDetails";
		assertTrue(actualUrl.equals(expectedUrl));
	}

	@When("^Admin click on Delete button$")
	public void admin_click_on_delete_button() throws Throwable {
		driver.findElement(By.id("deleteBtn")).click();
	}

	@Then("^Book Deleted successfully$")
	public void admin_Deleted_Book_successfully() throws Throwable {
		Thread.sleep(5000);
		String alertMsg = driver.switchTo().alert().getText();
		String expectedMsg = "Book Deleted Successfully";
		assertTrue(alertMsg.equalsIgnoreCase(expectedMsg));
		driver.switchTo().alert().accept();
		driver.close();

	}

	@Given("^Admin is on viewing book page based on names$")
	public void student_is_on_viewing_book_page_based_on_name() throws Throwable {
		driver.findElement(By.linkText("Search Books")).click();
		String exceptedUrl = "http://localhost:4200/bookDetails";
		String actualUrl = driver.getCurrentUrl();
		assertTrue("Admin is search book page", exceptedUrl.equals(actualUrl));
	}

	@When("^Admin enters book Name as \"([^\"]*)\"$")
	public void admin_enters_book_Name_as(String bookTitle) throws Throwable {
		driver.findElement(By.xpath("//input[@placeholder='Search here for Book']")).sendKeys(bookTitle);
	}

	@Then("^show book based on book name$")
	public void show_book_based_on_name() throws Throwable {
		String actualBookName = driver.findElement(By.xpath("//tr[1]//td[3]")).getText().trim();
		String expectedBookName = "Half Girl";
		System.out.println(actualBookName);
		assertTrue("Student Searched A Book By Book Name", actualBookName.equalsIgnoreCase(expectedBookName));
		driver.close();
	}

	@Given("^Admin is on viewing book page based on author Name$")
	public void admin_is_on_viewing_book_page_based_on_author_Name() throws Throwable {
		driver.findElement(By.linkText("Search Books")).click();
		String exceptedUrl = "http://localhost:4200/bookDetails";
		String actualUrl = driver.getCurrentUrl();
		assertTrue("Admin is ", exceptedUrl.equals(actualUrl));

	}

	@When("^Admin enters book author Name as \"([^\"]*)\"$")
	public void admin_enters_book_author_Name_as(String authorName) throws Throwable {
		driver.findElement(By.xpath("//input[@placeholder='Search here for Book']")).sendKeys(authorName);

	}

	@Then("^Show books based on book author name$")
	public void show_books_based_on_author_name() throws Throwable {
		String actualBookAuthorName = driver.findElement(By.xpath("//tr[1]//td[4]")).getText().trim();
		String expectedBookAuthorName = "James";
		System.out.println(actualBookAuthorName);
		assertTrue("Student Searched A Book By Author Name",
				actualBookAuthorName.equalsIgnoreCase(expectedBookAuthorName));
		driver.close();

	}

	@Given("^Admin is on home page$")
	public void student_is_on_home_page() throws Throwable {
		Thread.sleep(2000);
		String actualTitle = driver.getCurrentUrl().trim();
		String exceptedUrl = "http://localhost:4200/home";
		assertTrue("Student Is On Home Page", actualTitle.equals(exceptedUrl));
	}

	@When("^Admin click on logout$")
	public void student_click_on_logout() throws Throwable {
		driver.findElement(By.name("logoutbutton")).click();
	}

	@Then("^Admin loggedout$")
	public void student_loggedout() throws Throwable {
		String titleName = driver.findElement(By.tagName("h2")).getText();
		String expectedName = "Welcome To Library Management System";
		assertTrue(titleName.equals(expectedName));
		driver.close();
		driver.quit();
	}

}
