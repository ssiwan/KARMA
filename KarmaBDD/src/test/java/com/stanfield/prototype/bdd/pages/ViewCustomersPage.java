package com.stanfield.prototype.bdd.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;

@DefaultUrl("http://localhost:4200/stanfieldprototype/customer")
//@DefaultUrl("https://acceptance.bigbluesign.com/stanfieldprototype/customer")
public class ViewCustomersPage extends PageObject{

	@FindBy(id="addButton")
    WebElementFacade addButton;

	@FindBy(id="customersButton")
    WebElementFacade customersButton;
	
	@FindBy(id="listOfLinks")
    List<WebElementFacade> customerLinks;
	
	String browserURL = null;
	
	public ViewCustomersPage(WebDriver driver) {
		super(driver);
		browserURL = System.getProperty("browser.url");
		if (browserURL != null) {
			this.getDriver().get(browserURL);
			System.out.println("Retrieved browser url from system properties");
		}
	}
	
    public boolean addButtonIsVisible() {
        return addButton.isVisible();
    }
    
    public boolean haveCustomers() {
    	withTimeoutOf(30, TimeUnit.SECONDS).waitFor(customerLinks);
    	if(customerLinks.size() > 0) {
    		System.out.println("Number of customers = " + customerLinks.size());
    		return true;
    	}
        return false;
    }
    
    public boolean customersButtonIsVisible() {
        return customersButton.isVisible();
    }

	public String getTitle(String message) {
		setImplicitTimeout(10, TimeUnit.SECONDS);
		System.out.println("Waiting for title: " + message);
		waitForTextToAppear(message);
		return getTitle().toString();
	}    
	
    public void clickAddButton() {
    	if(addButtonIsVisible()){
    		addButton.click();
    	}		
    }

    public void clickCustomersButton() {
    	//setImplicitTimeout(20, TimeUnit.SECONDS);
    	customersButton.withTimeoutOf(20, TimeUnit.SECONDS).waitUntilClickable().click();
    	if(customersButtonIsVisible()){
    		customersButton.click();
    	}		
    }
    
    public boolean verifyCustomerExists() {
    	String customer = "Ted";
		for (WebElementFacade customerLink: customerLinks) {
			if(customerLink.containsText(customer)) {
				System.out.println(customer + " exists in customer list");
				return true;
			}
		}
        return false;
    }
    
    public void clickItemInList() {
    	System.out.println("Clicking the designated customer");
		//setImplicitTimeout(10, TimeUnit.SECONDS);
		withTimeoutOf(20, TimeUnit.SECONDS).waitForPresenceOf(By.partialLinkText("Ted"));
    	getDriver().findElement(By.partialLinkText("Ted")).click();
    	System.out.println("Customer has been clicked");
    }
    
}
