package com.stanfield.karma.bdd.steps;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import net.thucydides.core.annotations.Steps;

public class ViewKnowledgeAreasListStepDefinitions {
    @Steps
    KnowledgeAreaSteps knowledgeAreaSteps;

    @When("^I view all knowledge areas$")
    public void iViewAllKnowledgeAreas() { 
    	knowledgeAreaSteps.viewAllKnowledgeAreas();
    }
    
    @Then("^I should see at least one knowledge area in the list of areas$")
    public void iShouldSeeAtLeastOneKnowledgeAreaInTheListOfAreas() { 
    	knowledgeAreaSteps.shouldSeeAtLeastOneKnowledgeArea();
    }
    
}
