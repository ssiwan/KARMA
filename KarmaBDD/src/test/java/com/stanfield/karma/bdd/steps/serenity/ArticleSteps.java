package com.stanfield.karma.bdd.steps.serenity;

import com.stanfield.karma.bdd.pages.ArticleListPage;
import com.stanfield.karma.bdd.pages.ArticlePage;
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

public class ArticleSteps extends ScenarioSteps{

	private static final long serialVersionUID = 1L;

	DashboardPage dashboardPage;
	ArticleListPage articleListPage;
	ArticlePage articlePage;
    

    @Step("I view all articles")
    public void viewAllArticles() {
    	dashboardPage.clickAllArticles();;
    }
    
    @Step("I should see at least one article") 
    public void iShouldSeeAtLeastOneArticle() {
    	Assert.assertTrue(articleListPage.hasArticles());
    }
    
    @Step("I click on first Article in list")
    public void clickOnFirstArticle() {
    	articleListPage.clickOnFirst();
    }
    
    @Step("Article has content")
    public void containtContent(String contentText) {
    	Assert.assertTrue(articlePage.containsContent(contentText));
    }
}

