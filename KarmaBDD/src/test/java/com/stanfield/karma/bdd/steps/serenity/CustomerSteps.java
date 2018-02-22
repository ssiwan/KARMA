package com.stanfield.karma.bdd.steps.serenity;

import com.stanfield.karma.bdd.helpers.EntityFactory;
import com.stanfield.karma.bdd.pages.AddCustomerPage;
import com.stanfield.karma.bdd.pages.EditCustomerPage;
import com.stanfield.karma.bdd.pages.ViewCustomersPage;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import net.thucydides.core.steps.ScenarioSteps;

import org.testng.Assert;

import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;

public class CustomerSteps extends ScenarioSteps{

	private static final long serialVersionUID = 1L;

	ViewCustomersPage viewCustomersPage;
	AddCustomerPage addCustomerPage;
	EditCustomerPage editCustomerPage;
    
    @Step("I add a customer") 
    public void addACustomer() {
    	addCustomerPage.addACustomer();
    }
    
    @Step("I edit a customer") 
    public void editACustomer() {
    	editCustomerPage.editACustomer();
    }

    @Step("I delete a customer") 
    public void deleteACustomer() {
    	editCustomerPage.deleteACustomer();
    }
    
    @Step("I navigate from add to view customers page")
    public void navigateFromAddToToViewCustomersPage() {
    	addCustomerPage.navigateBack();
    }
    
    @Step("I navigate from edit to view customers page")
    public void navigateFromEditToToViewCustomersPage() {
    	editCustomerPage.navigateBack();
    }

    
    @Step("I open the view Customers page")                                                       
    public void openViewCustomersPage() {
    	viewCustomersPage.open();
    	viewCustomersPage.clickCustomersButton();
    	boolean customerVisible = viewCustomersPage.haveCustomers();
    	System.out.println("List of customers is visible = " + customerVisible);
    	
    }
    
    @Step("I verify the add button is visible")
    	public void verifyAddButtonIsVisible() {	
    	boolean visible = viewCustomersPage.addButtonIsVisible();
    	Assert.assertTrue(visible);
    }

    @Step("I verify the customers button is visible")
	public void verifyCustomersButtonIsVisible() {	
	boolean visible = viewCustomersPage.customersButtonIsVisible();
	Assert.assertTrue(visible);
}
    
    @Step("I click the add button")
    public void clickAddButton() {
    	viewCustomersPage.clickAddButton();
    }
    
    @Step("I also select a customer")
    public void selectACustomer() {
        viewCustomersPage.clickItemInList();
    }
    
    @Step("I click the customers button")
    public void clickCustomersButton() {
    	viewCustomersPage.clickCustomersButton();
    }

//    @Step("The title is present")
//    public void verifyTitle(String title) {
//    	viewCustomersPage.getTitle(title);
//		Assert.assertEquals("SSISA OS - Angular Prototype", viewCustomersPage.getTitle(title));
//    }
    
    @Step("I verify customer exists")
    public void verifyCustomerExists(boolean shouldMatch) {
    	boolean exists = viewCustomersPage.verifyCustomerExists();
    	if (shouldMatch) {
    		Assert.assertTrue(exists);
    	} else {
    		Assert.assertFalse(exists);
    	}
    }
    
	@After("@After_DeleteCustomerUsingAPI")
	public void afterScenario2(Scenario scenario) {
		// delete using REST API
		String s = Serenity.sessionVariableCalled("id");
		System.out.println("SessionVariable = " + s);
		com.stanfield.karma.bdd.steps.serenity.RestSteps steps = new com.stanfield.karma.bdd.steps.serenity.RestSteps();
		System.out.println("Deleting customer with Id = " + s);
		steps.setTheLink("customer");
		steps.getRequest().pathParams("id", s);
		steps.getRequest().given().when().delete("{id}");
		steps.clear();
	}
    
	@After("@After_DeleteWebCustomer")
	public void afterScenario(Scenario scenario) {
		openViewCustomersPage();
		selectACustomer();
		try {
			deleteACustomer();
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			System.out.println("StaleELementReferenceException");
		}
		System.out.println("Customer deleted in AfterScenario.");
	}
	
	@Before("@Before_InsertCustomerUsingAPI")
	public void insertScenario(Scenario scenario) {
		System.out.println("------------------------------");
		System.out.println(scenario.getName());
		System.out.println("------------------------------");
		
		// insert using REST API
		com.stanfield.karma.bdd.steps.serenity.RestSteps steps = new com.stanfield.karma.bdd.steps.serenity.RestSteps();
		steps.setTheLink("customer");
		Object newEntity = EntityFactory.getInstance().createACustomer("Ted", "Nugent", 68);
		steps.setEntity(newEntity);
		io.restassured.response.Response response = steps.getRequest().when().post();
		steps.storeVariableFromResponseInSession(response, "id", "id");
		int id = steps.getSessionVariable("id");
		Serenity.setSessionVariable("id").to(String.valueOf(id));
		System.out.println("id = " + id);
		System.out.println("Inserted customer during Before Scenario");
	}
	
	public void quitDriver(){
		addCustomerPage.quitDriver();
	}
}
