package com.stanfield.karma.bdd.steps.serenity;

import com.stanfield.karma.bdd.pages.ArticleListPage;
import com.stanfield.karma.bdd.pages.DashboardPage;
import com.stanfield.karma.bdd.pages.WelcomePage; 
import com.stanfield.karma.bdd.helpers.EntityFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import net.thucydides.core.steps.ScenarioSteps;

import org.testng.Assert;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class SearchSteps extends ScenarioSteps{

	private static final long serialVersionUID = 1L;

	DashboardPage dashboardPage;
	ArticleListPage articleListPage;
    
    @Step("I open the Dashboard page")                                                       
    public void openDashboardPage() {
    	dashboardPage.open();
    }

    @Step("I search the keyword")
    public void searchKeywork(String searchString) {
    	dashboardPage.searchKeyword(searchString);
    }
    
    @Step("I should see at least one article") 
    public void iShouldSeeAtLeastOneArticle() {
    	Assert.assertTrue(articleListPage.hasArticles());
    }
}

