package com.stanfield.karma.bdd.pages;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;

public class AddCustomerPage extends PageObject {
	
	@FindBy(id="firstname")
    WebElementFacade firstNameTextBox;
	
	@FindBy(id="lastname")
    WebElementFacade lastNameTextBox;
	
    @FindBy(id="age")
    WebElementFacade ageTextBox;
	
    @FindBy(id="buttonForSubmit")
    WebElementFacade submitButton;
    
    @FindBy(id="anotherButtonForGoBack")
    WebElementFacade anotherButtonForGoBack;

	public AddCustomerPage() {
	}
	
	public AddCustomerPage(WebDriver driver) {
		super(driver);
	}
	
	public void addACustomer() {
		firstNameTextBox.clear();
		firstNameTextBox.sendKeys("Ted");
		lastNameTextBox.clear();
		lastNameTextBox.sendKeys("Nugent");
		ageTextBox.clear();
		ageTextBox.sendKeys("29");
		submit();
	}
	
	public void submit() {
		//setImplicitTimeout(30, TimeUnit.SECONDS);
		submitButton.withTimeoutOf(10, TimeUnit.SECONDS).waitUntilClickable().click();
		if(submitButton.isVisible()){
    		submitButton.click();
    	}		
	}
	
	public void navigateBack() {
		//setImplicitTimeout(30, TimeUnit.SECONDS);
		anotherButtonForGoBack.withTimeoutOf(10, TimeUnit.SECONDS).waitUntilClickable().click();
    	if(anotherButtonForGoBack.isVisible()){
    		anotherButtonForGoBack.click();
    	}
	}
	
	public void quitDriver() {
		getDriver().quit();
	}
	
}