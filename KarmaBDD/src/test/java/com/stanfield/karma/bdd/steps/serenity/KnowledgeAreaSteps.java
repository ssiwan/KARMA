package com.stanfield.karma.bdd.steps.serenity;

import com.stanfield.karma.bdd.pages.DashboardPage;
import com.stanfield.karma.bdd.pages.KnowledgeAreaListPage;
import com.stanfield.karma.bdd.pages.WelcomePage; 
import com.stanfield.karma.bdd.helpers.EntityFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import net.thucydides.core.steps.ScenarioSteps;

import org.testng.Assert;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class KnowledgeAreaSteps extends ScenarioSteps{

	private static final long serialVersionUID = 1L;

	DashboardPage dashboardPage;
	KnowledgeAreaListPage knowledgeAreaListPage;
    

    @Step("I view all knowledge areas")
    public void viewAllKnowledgeAreas() {
    	dashboardPage.clickAllKnowledgeArea();
    }
    
    @Step("I should see at least one knowledge area") 
    public void shouldSeeAtLeastOneKnowledgeArea() {
    	Assert.assertTrue(knowledgeAreaListPage.hasKnowledgeAreas());
    }
}

