package com.stanfield.karma.bdd.pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;


@DefaultUrl("http://localhost:9000/#/")
public class ChangePasswordPage extends PageObject {
	
	@FindBy(id="newPassword")
    WebElementFacade newPasswordInput;
	
	@FindBy(id="confirmPassword")
    WebElementFacade confirmPasswordInput;
	
	@FindBy(id="save" )
	WebElementFacade saveButton;
	
	String browserURL = null;
	
	public ChangePasswordPage(WebDriver driver) {
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
	
	public boolean confirmPasswordInputIsVisible() {
        return confirmPasswordInput.isVisible();
    }
	
	public boolean newPasswordInputIsVisible() {
        return newPasswordInput.isVisible();
    }
	
	public void enterNewPassword(String newPassword) {
    	if(newPasswordInputIsVisible()){
    		newPasswordInput.click();
    		newPasswordInput.typeAndEnter(newPassword);
    	}		
    }
	
	public void enterConfirmPassword(String confirmPassword) {
    	if(confirmPasswordInputIsVisible()){
    		confirmPasswordInput.click();
    		confirmPasswordInput.typeAndEnter(confirmPassword);
    	}		
    }
	
	public boolean saveButtonIsVisible() {
        return saveButton.isVisible();
    }
	
	public void clickSaveButton() {
		if (saveButtonIsVisible()) {
			saveButton.click();
		}
	}

}
