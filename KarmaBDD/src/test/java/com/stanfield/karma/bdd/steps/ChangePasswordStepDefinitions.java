package com.stanfield.karma.bdd.steps;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class ChangePasswordStepDefinitions {

	@Steps
	ChangePasswordSteps changePasswordSteps;
	

	@When("^I select the password link$")
	public void iSelectThePasswordLink() {
		changePasswordSteps.selectThePasswordLink();
	}
	
	@When("^I enter a new password$")
	public void iEnterANewPassword() {
		changePasswordSteps.enterNewPassword();
	}
	
	@When("^I enter a confirm password$")
	public void iEnterAConfirmPassword() {
		changePasswordSteps.enterConfirmPassword();
	}
	
	@When("^I save the password$")
	public void iSaveThePassword() {
		changePasswordSteps.saveThePassword();
	}
	
	@Then("^I should see the success message \"([^\"]*)\"$")
    public void iShouldSeeTheSuccessMessageSomething(String successMessage){
		changePasswordSteps.verifyPasswordChangeSuccessMessage(successMessage);
    }
	
	 
}
