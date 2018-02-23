package com.stanfield.karma.bdd.steps.serenity;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import net.thucydides.core.steps.ScenarioSteps;

import org.testng.Assert;

import com.stanfield.karma.bdd.pages.ViewWelcomePage;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class PermissionSteps extends ScenarioSteps {
	
	private static final long serialVersionUID = 1L;
	
	ViewWelcomePage viewWelcomePage;
	
	@Step("I look for the Administration Tab")                                                       
    public void verifyAdministrationVisibility(boolean shouldBeSeen) {
		boolean isVisible = viewWelcomePage.AdministrationButtonVisible();
		Assert.assertTrue(isVisible == shouldBeSeen);  	
    }
	

}
