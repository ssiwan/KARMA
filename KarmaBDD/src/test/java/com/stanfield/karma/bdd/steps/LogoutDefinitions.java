package com.stanfield.karma.bdd.steps;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class LogoutDefinitions {

	@Steps
	LogoutSteps logoutSteps;
	@Steps
	LoginSteps loginSteps;
	
	@Given("^I am logged in$")
	public void iAmLoggedIn() {
		loginSteps.openViewWelcomePage();
		loginSteps.clickTheAccountButton();
		loginSteps.clickTheSignInButton();
		loginSteps.enterUsername("user");
		loginSteps.enterPassword("user");
		loginSteps.clickSumbit();
		loginSteps.verifySuccessMessage("You are logged in as user \"user\".");
	}
	

	@When("^I click on the Sign out button$")
	public void iClickOnTheSignOutButton() {
		logoutSteps.clickTheSignOutButton();
	}
	
	@Then("^I should see the warning message \"([^\"]*)\"$")
    public void i_should_see_the_warning_message_something(String warningMessage){
		logoutSteps.verifyWarningMessage(warningMessage);
    }
	
	 @Then("^I should \"([^\"]*)\" the Settings link$")
	 public void i_should_something_the_settings_link(String status){
	       if(status.equals("see"))
	    	   logoutSteps.verifySettingsLinkVisible(true);
	       else if(status.equals("not see"))
	    	   logoutSteps.verifySettingsLinkVisible(false);
	 }
}
