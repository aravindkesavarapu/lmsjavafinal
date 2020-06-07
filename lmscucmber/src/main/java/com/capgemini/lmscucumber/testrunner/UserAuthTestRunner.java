package com.capgemini.lmscucumber.testrunner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/java/com/capgemini/lmscucumber/features/userAuth.feature", glue = {
		"com.capgemini.lmscucumber.stepdefinitions" }, dryRun = false, plugin = { "pretty",
				"html:target/cucumber" }, monochrome = true)

public class UserAuthTestRunner {

}