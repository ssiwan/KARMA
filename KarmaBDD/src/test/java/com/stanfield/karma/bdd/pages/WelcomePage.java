package com.stanfield.karma.bdd.pages;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;

@DefaultUrl("http://localhost:9000/")
//@DefaultUrl("https://acceptance.bigbluesign.com/stanfieldprototype/customer")
public class WelcomePage extends PageObject{
	
	String browserURL = null;
	
	public WelcomePage(WebDriver driver) {
		super(driver);
		browserURL = System.getProperty("browser.url");
		if (browserURL != null) {
			this.getDriver().get(browserURL);
			System.out.println("Retrieved browser url from system properties");
		}
	}
	
	public String getTitle(String message) {
		setImplicitTimeout(10, TimeUnit.SECONDS);
		System.out.println("Waiting for title: " + message);
		waitForTextToAppear(message);
		return getTitle().toString();
	}    
}
