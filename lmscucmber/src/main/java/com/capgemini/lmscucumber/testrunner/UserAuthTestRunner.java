package com.capgemini.lmscucumber.testrunner;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/java/com/capgemini/lmscucumber/features/admin.feature",
	glue = { "com.capgemini.lmscucumber.stepdefinitions" },
	dryRun = false, plugin = { "pretty", "html:target/cucumber" }, monochrome = true)

public class UserAuthTestRunner {

	public static WebDriver driver;
	public static WebDriverWait wait;
	public static Actions action;

	static {
		System.setProperty("webdriver.chrome.driver",
				"C://Users//Aravind//Desktop//Final Parallel Project LMS//lmscucmber//driver//chromedriver.exe");
	}

}