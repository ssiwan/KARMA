package com.stanfield.karma.bdd.steps;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import net.thucydides.core.annotations.Steps;

public class ViewWelcomeStepDefinitions {
    @Steps
    WelcomeSteps welcomeSteps;

    @Given("^I am at the welcome page$")
    public void iAmAtTheWelcomePage() {
    	welcomeSteps.openWelcomePage();
    }

    @When("^I view the page content$")
    public void iViewThePageContent() { 
    }
    
    @Then("^The title \"([^\"]*)\" should be present$")
    public void theTitleShouldBePresent(String title) { 
    	welcomeSteps.verifyTitle(title);
    }
}
