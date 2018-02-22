package com.stanfield.karma.bdd.steps;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class LoginDefinitions {

	@Steps
	LoginSteps loginSteps;
	
	@Given("^I am on the login page$")
	public void iAmOnTheLoginPage() {
		loginSteps.openViewWelcomePage();
	}
	
	@When("^I click on the Sign In button$")
	public void iClickOnTheSignInButton() {
		loginSteps.clickTheSignInButton();
	}
	
	@When("^I click on the Account button$")
	public void iClickOnTheAccountButton() {
		loginSteps.clickTheAccountButton();
	}
	
	@When("^I enter \"([^\"]*)\" in the Username field$")
	public void iEnterInTheUsernameField(String username) {
		loginSteps.enterUsername(username);
	}
	
	@When("^I enter \"([^\"]*)\" in the Password field$")
	public void iEnterInThePasswordField(String password) {
		loginSteps.enterPassword(password);
	}
	
	@When("^I click the Submit button$")
	public void iClickTheSubmitButton() {
		loginSteps.clickSumbit();
	}
	
	@Then("^I should see an error message with the text \"([^\"]*)\"$")
	public void iShouldSeeAnErrorMessageWithTheText(String errorMessage) {
		loginSteps.verifyErrorMessage(errorMessage);
	}

}
