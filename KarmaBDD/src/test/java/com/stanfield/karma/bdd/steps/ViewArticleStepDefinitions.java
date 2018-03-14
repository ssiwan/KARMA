package com.stanfield.karma.bdd.steps;

import org.junit.Assert;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import net.thucydides.core.annotations.Steps;

public class ViewArticleStepDefinitions {
    @Steps
    ArticleSteps articleSteps;

    @When("^I click on the first Article$")
    public void iClickOnTheFirstArticle() { 
    	articleSteps.clickOnFirstArticle();
    }
    
    @Then("^I should see the Article Content \"([^\"]*)\"$")
    public void iShouldSeeTheArticleContent(String contentText) {
    	articleSteps.containtContent(contentText);
    }
 
}
