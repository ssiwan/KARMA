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
	
	@FindBy(id="logout")
    WebElementFacade signOutButton;
	
	@FindBy(id="account-menu" )
	WebElementFacade accountButton;

	@FindBy(id="username")
    WebElementFacade usernameField;
	
	@FindBy(id="password")
	WebElementFacade passwordField;
	
	@FindBy(id="submit")
	WebElementFacade submitButton;
	
	@FindBy(id="settings")
	WebElementFacade settingsLink;
	
	@FindBy(id="password")
	WebElementFacade passwordLink;
	
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
	
	public String getSuccessMessages() {
		setImplicitTimeout(5, TimeUnit.SECONDS);

		  String returnedMessage = this.getDriver().findElement(By.className("alert-success")).getText();
		  return returnedMessage;
	}
	
	public String getWarningMessages() {
		setImplicitTimeout(5, TimeUnit.SECONDS);

		  String returnedMessage = this.getDriver().findElement(By.className("alert-warning")).getText();
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
	
	public boolean signOutButtonIsVisible() {
        return signOutButton.isVisible();
    }
	
	public boolean settingsLinkIsVisible() {
        return settingsLink.isVisible();
    }
	
	public boolean passwordLinkIsVisible() {
        return passwordLink.isVisible();
    }
	
	public void clickSignInButton() {
		if (signInButtonIsVisible()) {
			signInButton.click();
		}
	}
	
	public void clickPasswordLinkButton() {
		if (passwordLinkIsVisible()) {
			passwordLink.click();
		}
	}
	
	public void clickSignOutButton() {
		if (signOutButtonIsVisible()) {
			signOutButton.click();
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
