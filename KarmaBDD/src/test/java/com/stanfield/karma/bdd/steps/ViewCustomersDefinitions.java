package com.stanfield.karma.bdd.steps;

import com.stanfield.karma.bdd.steps.serenity.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import net.thucydides.core.annotations.Steps;

public class ViewCustomersDefinitions {
    @Steps
    CustomerSteps customersSteps;

    @Given("^I am at the view customers page$")
    public void iAmAtTheViewCustomersPage() {
    	customersSteps.openViewCustomersPage();
    }

    @Given("^I also navigate to the add customer page$")
    public void iAlsoNavigateToTheAddCustomerPage() { 
    	customersSteps.clickAddButton();
    }
    
    @Given("^I also select a customer to edit or delete$")
    public void iAlsoSelectACustomerToEditOrDelete() { 
    	customersSteps.selectACustomer();
    }
    
//    @When("^I view the page content$")
//    public void iViewThePageContent() { 
//    }
//    
    @When("^I also click the customers button$")
    public void iAlsoClickTheCustomersButton() { 
    	customersSteps.clickCustomersButton();
    }
    
    // the add button should also be visible 
    @Then("^The add button should also be visible$")
    public void theAddButtonShouldAlsoBeVisible() { 
    	customersSteps.verifyAddButtonIsVisible();
    }

    // the customers button should also be visible 
    @Then("^The customers button should also be visible$")
    public void theCustomersButtonShouldAlsoBeVisible() { 
    	customersSteps.verifyCustomersButtonIsVisible();
    }
    
//    @Then("^The title \"([^\"]*)\" should be present$")
//    public void theTitleShouldBePresent(String title) { 
//    	customersSteps.verifyTitle(title);
//    }
//    
    @Then("^The customer exists$")
    public void theCustomerExists() {
    	customersSteps.verifyCustomerExists(true);
    }
    
    @Then("^The customer does not exist$")
    public void theCustomerDoesNotExist() {
    	customersSteps.verifyCustomerExists(false);
    }
}
