package com.stanfield.karma.bdd.steps.serenity;

import net.thucydides.core.steps.ScenarioSteps;

import org.testng.Assert;

import com.stanfield.karma.bdd.pages.ChangePasswordPage;
import com.stanfield.karma.bdd.pages.ViewWelcomePage;

import net.thucydides.core.annotations.Step;

public class ChangePasswordSteps extends ScenarioSteps {
	
	private static final long serialVersionUID = 1L;
	
	ChangePasswordPage changePasswordPage;
	ViewWelcomePage viewWelcomePage;
	String newPassword = "User2**";
	String oldPassword = "user";
	
	@Step("Select the Password link")
	public void selectThePasswordLink() {
		viewWelcomePage.clickPasswordLinkButton();
	}
	
	@Step("Enter New Password")
	public void enterNewPassword() {
		changePasswordPage.enterNewPassword(newPassword);
	}
	
	@Step("Enter Confirm Password")
	public void enterConfirmPassword() {
		changePasswordPage.enterConfirmPassword(newPassword);
	}
	
	@Step("Save the Password")
	public void saveThePassword() {
		changePasswordPage.clickSaveButton();
	}
	
	@Step("Retrieve Password change Success Message \"([^\"]*)\"")
	public void verifyPasswordChangeSuccessMessage(String message) {
		String returnedMessage = changePasswordPage.getSuccessMessages();
		Assert.assertTrue(returnedMessage.contains(message));
		RevertPassword();
	}
	
	private void RevertPassword() {
		changePasswordPage.enterNewPassword(oldPassword);
		changePasswordPage.enterConfirmPassword(oldPassword);
		changePasswordPage.clickSaveButton();
	}

}
