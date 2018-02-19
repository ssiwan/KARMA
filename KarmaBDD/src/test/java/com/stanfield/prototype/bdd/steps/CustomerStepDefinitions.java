package com.stanfield.prototype.bdd.steps;

import com.stanfield.prototype.bdd.steps.serenity.CustomerSteps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class CustomerStepDefinitions {
    @Steps
    CustomerSteps customerSteps;

    @When("^I also navigate back to the View Customers page from Add Customer Page$")
    public void iAlsoNavigateBackToTheViewCustomersPageFromAddCustomerPage() {
    	customerSteps.navigateFromAddToToViewCustomersPage();
    }

    @When("^I also navigate back to the View Customers page from Edit Customer Page$")
    public void iAlsoNavigateBackToTheViewCustomersPageFromEditCustomerPage() {
    	customerSteps.navigateFromEditToToViewCustomersPage();
    }
    
    @When("^I add a customer$")
    public void iAddACustomer() {
    	customerSteps.addACustomer();
    }
    
    @When("^I edit the customer$")
    public void iEditTheCustomer() {
    	customerSteps.editACustomer();
    }
    
    @When("^I delete the customer$")
    public void iDeleteTheCustomer() {
    	customerSteps.deleteACustomer();
    }
    
    
}
