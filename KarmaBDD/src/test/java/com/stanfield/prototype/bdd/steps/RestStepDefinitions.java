package com.stanfield.prototype.bdd.steps;

import java.util.Map;

import com.stanfield.prototype.bdd.helpers.EntityFactory;
import com.stanfield.prototype.bdd.steps.serenity.RestSteps;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class RestStepDefinitions {
	@Steps
	RestSteps restSteps;

	@Given("^I have a link \"([^\"]*)\"$")
	public void iHaveALink(String link) {
		restSteps.setTheLink(link);
	}

	@Given("^I also have an entity for a \"([^\"]*)\"$")
	public void iAlsoHaveAnEntityForA(String entityName, DataTable table) {
		restSteps.setEntityName(entityName);
		restSteps.setEntity(EntityFactory.getInstance().getEntityFromTable(table, entityName));
	}

	@Given("^The created \"([^\"]*)\" also does not exist$")
	public void theCreatedUserAlsoDoesNotExist(String entity) {
		if (entity.equals("User")) {
			System.out.println("Storing userId in session for a user that does not exist.");
			restSteps.storeFakeVariableInSession("UserId");
		}
	}

	@Given("^I have parameters$")
	public void iHaveParameters(DataTable table) {
		Map<String, String> parameters = table.asMap(String.class, String.class);
		restSteps.setParameters(parameters);
	}

	@When("^I perform the action \"([^\"]*)\"$")
	public void iPerformTheAction(String action) {
		restSteps.performAction(action);
	}

	@Then("^I receive message \"([^\"]*)\"$")
	public void iReceiveMessage(String message) {
		restSteps.verifyMessage(message);
	}

	@Then("^The edited entity exists$")
	public void theEditedEntityExists() {
		restSteps.fetchEntityForJsonComparison();
	}

	@Then("^The fetched entity \"([^\"]*)\" also matches$")
	public void theFetchedEntityAlsoMatches(String entityName, DataTable table) {
		restSteps.setEntity(EntityFactory.getInstance().getEntityFromTable(table, entityName));
		restSteps.setEntityName(entityName);
		/**Note: You can also use restSteps.checkTheJsonListInTheResponse(); */
		restSteps.checkTheListForTheEntityInTheResponse();
	}

	@Then("^The response contains the entity \"([^\"]*)\"$")
	public void theResponseContainsTheEntity(String entityName,  DataTable table) {
		restSteps.setEntityName(entityName);
		restSteps.setEntity(EntityFactory.getInstance().getEntityFromTable(table, entityName));
		/**Note: You can also use restSteps.checkTheJsonMapInTheResponse(); */
		restSteps.checkTheEntityInTheResponse();
	}

}
