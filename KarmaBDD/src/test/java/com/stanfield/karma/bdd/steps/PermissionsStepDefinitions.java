package com.stanfield.karma.bdd.steps;

import org.junit.Assert;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import net.thucydides.core.annotations.Steps;

public class PermissionsStepDefinitions {
	@Steps
	LoginSteps loginSteps;
	
	@Steps
	PermissionSteps permissionSteps;

	@Given("^I am logged in as Admin$")
	public void iAmLoggedInAdAdmin() {
		loginSteps.openViewWelcomePage();
		loginSteps.clickTheAccountButton();
		loginSteps.clickTheSignInButton();
		loginSteps.enterUsername("admin");
		loginSteps.enterPassword("admin");
		loginSteps.clickSumbit();
		loginSteps.verifySuccessMessage("You are logged in as user \"admin\".");
	}
	
	
    @When("^I look for the Administration Tab$")
    public void iLookForTheAdministrationTab() { 
    }
    
    @Then("^whether I should see the Tab is \"([^\"]*)\"$")
    public void theTitleShouldBePresent(boolean isSeen) { 
    	permissionSteps.verifyAdministrationVisibility(isSeen);
    }
}
