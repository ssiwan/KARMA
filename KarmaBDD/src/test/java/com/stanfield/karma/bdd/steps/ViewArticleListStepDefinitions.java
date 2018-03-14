package com.stanfield.karma.bdd.steps;

import org.junit.Assert;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import net.thucydides.core.annotations.Steps;

public class ViewArticleListStepDefinitions {
    @Steps
    ArticleSteps articleSteps;

    @When("^I view all knowledge articles$")
    public void iViewAllKnowledgeArticles() { 
    	articleSteps.viewAllArticles();
    }
    
}
