package com.stanfield.karma.bdd.steps;

import org.junit.Assert;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import net.thucydides.core.annotations.Steps;

public class SearchArticlesStepDefinitions {
    @Steps
    SearchSteps searchSteps;

    @When("^I search for the keyword \"([^\"]*)\"$")
    public void iSearchForTheKeyword(String searchString) { 
    	searchSteps.searchKeywork(searchString);
    }
    
    @Then("^I should see at least one article in the list of articles$")
    public void iShouldSeeAtLeastOneArticleInTheListOfArticles() { 
    	
    }
}
