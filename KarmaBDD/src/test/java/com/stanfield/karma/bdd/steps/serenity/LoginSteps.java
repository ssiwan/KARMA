package com.stanfield.karma.bdd.steps.serenity;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import net.thucydides.core.steps.ScenarioSteps;

import org.testng.Assert;

import com.stanfield.karma.bdd.pages.ViewWelcomePage;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class LoginSteps extends ScenarioSteps {
	
	private static final long serialVersionUID = 1L;
	
	ViewWelcomePage viewWelcomePage;
	
	@Step("I navigate to the login page")                                                       
    public void openViewWelcomePage() {
		viewWelcomePage.open();
    	    	
    }
	
	@Step("Click the Sign In Button")
	public void clickTheSignInButton() {
		viewWelcomePage.clickSignInButton();
	}
	
	@Step("Click the Account Button")
	public void clickTheAccountButton() {
		viewWelcomePage.clickAccountButton();
	}
	
	@Step("Enter \"([^\"]*)\" in the Username field") 
	public void enterUsername(String username){
		viewWelcomePage.enterUsername(username);
	}
	
	@Step("Enter \"([^\"]*)\" in the Password field")
	public void enterPassword(String password) {
		viewWelcomePage.enterPassword(password);
	}
	
	@Step("Click Submit")
	public void clickSumbit() {
		viewWelcomePage.clickSubmit();
	}
	
	@Step("Retrieve Error Message \"([^\"]*)\"")
	public void verifyErrorMessage(String message) {
		String returnedMessage = viewWelcomePage.getErrorMessages();
		Assert.assertTrue(returnedMessage.contains(message));
	}

}
