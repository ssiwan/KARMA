package com.stanfield.prototype.bdd.pages;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class EditCustomerPage extends PageObject {
	
	@FindBy(id="firstname")
    WebElementFacade firstNameTextBox;
	
	@FindBy(id="lastname")
    WebElementFacade lastNameTextBox;
	
    @FindBy(id="age")
    WebElementFacade ageTextBox;
	
    @FindBy(id="updateButton")
    WebElementFacade updateButton;
    
    @FindBy(id="shownGoBackButton")
    WebElementFacade shownGoBackButton;
    
    @FindBy(id="deleteButton")
    WebElementFacade deleteButton;

	public EditCustomerPage() {
	}
	
	public EditCustomerPage(WebDriver driver) {
		super(driver);
	}
	
	public void editACustomer() {
		// change the age
		ageTextBox.clear();
		ageTextBox.sendKeys("42");
		setImplicitTimeout(5, TimeUnit.SECONDS);
		updateCustomer();
	}
	
	public void updateCustomer() {
		setImplicitTimeout(30, TimeUnit.SECONDS);
		//updateButton.withTimeoutOf(10, TimeUnit.SECONDS).waitUntilClickable().click();
    	if(updateButton.isVisible()){
    		updateButton.click();
    	}

	}
	
	public void deleteACustomer() {
		System.out.println("Deleting the customer");
		setImplicitTimeout(30, TimeUnit.SECONDS);
    	//deleteButton.withTimeoutOf(30, TimeUnit.SECONDS).waitUntilClickable().click();
    	System.out.println("Delete button is visible = " + deleteButton.isVisible());
    	if(deleteButton.isVisible()){
    		deleteButton.click();
    		System.out.println("Delete button clicked");
    	}
	}
	
	public void navigateBack() {
		//shownGoBackButton.withTimeoutOf(10, TimeUnit.SECONDS).waitUntilClickable().click();
		setImplicitTimeout(30, TimeUnit.SECONDS);
		if(shownGoBackButton.isVisible()){
    		System.out.println("Navigating back to Customers page");
    		shownGoBackButton.click();
    	}
	}
	
	public void quitDriver() {
		getDriver().quit();
	}
	
}