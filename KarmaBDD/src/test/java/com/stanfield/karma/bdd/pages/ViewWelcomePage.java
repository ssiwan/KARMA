package com.stanfield.karma.bdd.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;


@DefaultUrl("http://localhost:9000/#/")
public class ViewWelcomePage extends PageObject {
	
	@FindBy(id="login")
    WebElementFacade signInButton;
	
	@FindBy(id="account-menu" )
	WebElementFacade accountButton;

	@FindBy(id="username")
    WebElementFacade usernameField;
	
	@FindBy(id="password")
	WebElementFacade passwordField;
	
	@FindBy(id="submit")
	WebElementFacade submitButton;
	
	String browserURL = null;
	
	public ViewWelcomePage(WebDriver driver) {
		super(driver);
		browserURL = System.getProperty("browser.url");
		if (browserURL != null) {
			this.getDriver().get(browserURL);
			System.out.println("Retrieved browser url from system properties");
		}
	}
	
	public String getErrorMessages() {
		setImplicitTimeout(5, TimeUnit.SECONDS);

		  String returnedMessage = this.getDriver().findElement(By.className("alert-danger")).getText();
		  return returnedMessage;
	}
	
	public boolean accountButtonIsVisible() {
        return accountButton.isVisible();
    }

	
	public void clickAccountButton() {
    	if(accountButtonIsVisible()){
    		accountButton.click();
    	}		
    }
	
	public boolean signInButtonIsVisible() {
        return signInButton.isVisible();
    }
	
	public void clickSignInButton() {
		if (signInButtonIsVisible()) {
			signInButton.click();
		}
	}
	
	public void enterUsername(String username) {
		usernameField.clear();
		usernameField.sendKeys(username);
	}
	
	public void enterPassword(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);
	}
	
	public void clickSubmit() {
		submitButton.click();
	}
	

}
