package com.stanfield.karma.bdd.steps.serenity;

import com.stanfield.karma.bdd.pages.WelcomePage; 
import com.stanfield.karma.bdd.helpers.EntityFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import net.thucydides.core.steps.ScenarioSteps;

import org.testng.Assert;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class WelcomeSteps extends ScenarioSteps{

	private static final long serialVersionUID = 1L;

	WelcomePage welcomePage;
    
    @Step("I open the Welcome page")                                                       
    public void openWelcomePage() {
    	welcomePage.open();
    }

    @Step("The title is present")
    public void verifyTitle(String title) {
    	welcomePage.getTitle(title);
		Assert.assertEquals("Welcome to KARMA", welcomePage.getTitle(title));
    }
}

