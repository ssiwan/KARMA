package com.stanfield.karma.bdd.steps.serenity;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import net.thucydides.core.steps.ScenarioSteps;

import org.testng.Assert;

import com.stanfield.karma.bdd.pages.ViewWelcomePage;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class LogoutSteps extends ScenarioSteps {
	
	private static final long serialVersionUID = 1L;
	
	ViewWelcomePage viewWelcomePage;
	
	@Step("Click the Sign Out Button")
	public void clickTheSignOutButton() {
		viewWelcomePage.clickSignOutButton();
	}
	
	@Step("Retrieve Warning Message \"([^\"]*)\"")
	public void verifyWarningMessage(String message) {
		String returnedMessage = viewWelcomePage.getWarningMessages();
		Assert.assertTrue(returnedMessage.contains(message));
	}
	
	@Step("Retrieve Settings Link Visibility \"([^\"]*)\"")
	public void verifySettingsLinkVisible(boolean visiblity) {
		boolean linkExists = viewWelcomePage.settingsLinkIsVisible();
		Assert.assertEquals(linkExists, visiblity);
	}

}
